package xyz.wochib70.domain.redeem;

import xyz.wochib70.domain.DomainException;

public class NoSuchRedeemItemException extends DomainException {
    public NoSuchRedeemItemException(String message) {
        super(message);
    }
}
