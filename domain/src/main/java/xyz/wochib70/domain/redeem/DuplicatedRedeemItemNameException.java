package xyz.wochib70.domain.redeem;

import xyz.wochib70.domain.DomainException;

public class DuplicatedRedeemItemNameException extends DomainException {
    public DuplicatedRedeemItemNameException(String message) {
        super(message);
    }
}
