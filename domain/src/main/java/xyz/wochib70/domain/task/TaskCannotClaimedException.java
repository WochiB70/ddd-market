package xyz.wochib70.domain.task;

import xyz.wochib70.domain.DomainException;

public class TaskCannotClaimedException extends DomainException {
    public TaskCannotClaimedException(String message) {
        super(message);
    }
}
