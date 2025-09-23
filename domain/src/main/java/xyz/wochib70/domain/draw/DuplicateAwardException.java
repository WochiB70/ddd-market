package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.DomainException;

public class DuplicateAwardException extends DomainException {
    public DuplicateAwardException(String message) {
        super(message);
    }
}
