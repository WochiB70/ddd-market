package xyz.wochib70.domain.redeem;

import xyz.wochib70.domain.DomainException;

public class NoSuchRedeemExistsException extends DomainException {
    public NoSuchRedeemExistsException(String message) {
        super(message);
    }
}
