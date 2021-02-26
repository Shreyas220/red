package com.example.reddit.reddit.service;


import com.example.reddit.reddit.dto.AuthenticationResponse;
import com.example.reddit.reddit.dto.LoginRequest;
import com.example.reddit.reddit.dto.RegisterRequest;
import com.example.reddit.reddit.exceptions.SpringRedditException;
import com.example.reddit.reddit.models.NotificationEmail;
import com.example.reddit.reddit.models.User;
import com.example.reddit.reddit.models.VerificationToken;
import com.example.reddit.reddit.repository.UserRepository;
import com.example.reddit.reddit.repository.VerificationTokenRepository;
import com.example.reddit.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {


        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final VerificationTokenRepository verificationTokenRepository;
        private final MailService mailService;
        private final AuthenticationManager authenticationManager;
        private final JwtProvider jwtProvider;

    //Creating a new user
        @Transactional
        public void signup(RegisterRequest registerRequest){

            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCreated(Instant.now());
            user.setEnabled(false);
            userRepository.save(user);
            //generating token
            String token = generateVerificationToken((user));
            mailService.sendMail((new NotificationEmail("Please Activate your Account",
                    user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                    "please click on the below url to activate your account : " +
                    "http://localhost:8080/api/auth/accountVerification/" + token)));

        }

    //generating token
        private String generateVerificationToken(User user){
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUser(user);

            verificationTokenRepository.save(verificationToken);
            return token;
        }
    //verifying after user clicks on verification link
    public void verifyAccount(String token, GeneralSecurityException e) {
          Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
          verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token", e));
          fetchUserAndEnable(verificationToken.get(), e);


    }
    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken, GeneralSecurityException e){
            String username = verificationToken.getUser().getUsername();
            User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("Invalid username not found", e));
            user.setEnabled(true);
            userRepository.save(user);

        }

        public void login(LoginRequest loginRequest){
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtProvider.generateToken(authenticate);
            return; new AuthenticationResponse(token, loginRequest.getUsername());
             }

    public User getCurrentUser() {
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}


