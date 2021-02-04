package com.example.reddit.reddit.exceptions;

import org.springframework.mail.MailException;

import java.security.GeneralSecurityException;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage, MailException e) {
        super(exMessage);
    }

    public SpringRedditException(String invalid_username_not_found, GeneralSecurityException e) {

    }

    public SpringRedditException(String vote_not_found) {

    }
}
