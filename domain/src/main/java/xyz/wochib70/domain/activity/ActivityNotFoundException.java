package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.DomainException;

public class ActivityNotFoundException extends DomainException {
    public ActivityNotFoundException(String message) {
        super(message);
    }
}
