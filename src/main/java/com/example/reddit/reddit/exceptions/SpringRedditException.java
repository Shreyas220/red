package com.example.reddit.reddit.exceptions;

import org.springframework.mail.MailException;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage, MailException e) {
        super(exMessage);
    }
}
