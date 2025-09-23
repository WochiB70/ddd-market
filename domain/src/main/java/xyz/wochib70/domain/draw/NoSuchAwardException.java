package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.DomainException;

public class NoSuchAwardException extends DomainException {
    public NoSuchAwardException(String message) {
        super(message);
    }
}
