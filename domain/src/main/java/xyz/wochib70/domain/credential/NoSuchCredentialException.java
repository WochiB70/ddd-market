package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.DomainException;

public class NoSuchCredentialException extends DomainException {
    public NoSuchCredentialException(String message) {
        super(message);
    }
}
