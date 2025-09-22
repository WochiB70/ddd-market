package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.DomainException;

public class CredentialUnusedCountInvalidException extends DomainException {
    public CredentialUnusedCountInvalidException(String message) {
        super(message);
    }
}
