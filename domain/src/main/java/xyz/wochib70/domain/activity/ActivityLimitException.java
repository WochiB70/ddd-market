package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.DomainException;

public class ActivityLimitException extends DomainException {
    public ActivityLimitException(String message) {
        super(message);
    }
}
