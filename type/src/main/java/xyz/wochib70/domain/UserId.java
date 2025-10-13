package xyz.wochib70.domain;

import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

public record UserId(
        Long id
) implements IdentifierId<Long> {

    public UserId {
        ParameterUtil.requireNonNull(id, "用户Id不能为null");
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserId userId = (UserId) o;
        return Objects.equals(id, userId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
