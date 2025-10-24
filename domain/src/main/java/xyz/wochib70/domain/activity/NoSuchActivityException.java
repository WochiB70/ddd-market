package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.DomainException;

public class NoSuchActivityException extends DomainException {
    public NoSuchActivityException(String message) {
        super(message);
    }
}
