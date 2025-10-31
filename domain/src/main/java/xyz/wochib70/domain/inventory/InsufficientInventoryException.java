package xyz.wochib70.domain.inventory;

import xyz.wochib70.domain.DomainException;

public class InsufficientInventoryException extends DomainException {
    public InsufficientInventoryException(String message) {
        super(message);
    }
}
