package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.DomainException;

public class NoSuchDrawPoolException extends DomainException {
    public NoSuchDrawPoolException(String message) {
        super(message);
    }
}
