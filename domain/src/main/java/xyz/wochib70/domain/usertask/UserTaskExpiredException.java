package xyz.wochib70.domain.usertask;

import xyz.wochib70.domain.DomainException;

public class UserTaskExpiredException extends DomainException {
    public UserTaskExpiredException(String message) {
        super(message);
    }
}
