package xyz.wochib70.domain.task;

import xyz.wochib70.domain.DomainException;

public class TaskCountLimitException extends DomainException {
    public TaskCountLimitException(String message) {
        super(message);
    }
}
