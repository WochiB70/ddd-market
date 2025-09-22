package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.DomainException;

public class IllegalCredentialException extends DomainException {
    public IllegalCredentialException(String message) {
        super(message);
    }
}
