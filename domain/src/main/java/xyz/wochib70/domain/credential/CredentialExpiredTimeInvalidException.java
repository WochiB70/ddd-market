package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.DomainException;

public class CredentialExpiredTimeInvalidException extends DomainException {
    public CredentialExpiredTimeInvalidException(String message) {
        super(message);
    }
}
