package xyz.wochib70.domain.usertask;

import xyz.wochib70.domain.DomainException;

public class NoSuchUserTaskException extends DomainException {
    public NoSuchUserTaskException(String message) {
        super(message);
    }
}
