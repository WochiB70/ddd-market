package xyz.wochib70.domain.currency;

import xyz.wochib70.domain.DomainException;

public class NoSuchCurrencyException extends DomainException {
    public NoSuchCurrencyException(String message) {
        super(message);
    }
}
