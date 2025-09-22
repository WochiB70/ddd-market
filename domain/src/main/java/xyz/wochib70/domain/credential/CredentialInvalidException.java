package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.DomainException;

public class CredentialInvalidException extends DomainException {
    public CredentialInvalidException(String message) {
        super(message);
    }
}
