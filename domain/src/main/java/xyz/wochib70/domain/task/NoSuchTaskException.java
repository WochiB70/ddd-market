package xyz.wochib70.domain.task;

import xyz.wochib70.domain.DomainException;

public class NoSuchTaskException extends DomainException {
    public NoSuchTaskException(String message) {
        super(message);
    }
}
