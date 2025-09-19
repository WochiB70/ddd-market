package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.DomainException;

public class ActivityStatusException extends DomainException {
    public ActivityStatusException(String message) {
        super(message);
    }
}
