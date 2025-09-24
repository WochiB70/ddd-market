package xyz.wochib70.domain.redeem;

import xyz.wochib70.domain.DomainException;

public class InsufficientRedeemItemInventoryException extends DomainException {
    public InsufficientRedeemItemInventoryException(String message) {
        super(message);
    }
}
