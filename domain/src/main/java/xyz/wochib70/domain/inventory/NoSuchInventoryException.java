package xyz.wochib70.domain.inventory;

import xyz.wochib70.domain.DomainException;

public class NoSuchInventoryException extends DomainException {
    public NoSuchInventoryException(String message) {
        super(message);
    }
}
