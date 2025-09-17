package xyz.wochib70.domain;

import java.io.Serializable;

/**
 * @author WochiB70
 */
public interface IdentifierId<ID> extends Serializable {

    ID getId();
}
