package xyz.wochib70.domain.account;

import xyz.wochib70.domain.DomainException;

public class InsufficientBalanceException extends DomainException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
