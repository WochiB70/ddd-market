package xyz.wochib70.domain;


import java.util.Objects;

public record DefaultIdentifierId<ID>(
        ID id
) implements IdentifierId<ID> {

    public DefaultIdentifierId {
        Objects.requireNonNull(id, "id不能为null");
    }

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultIdentifierId<?> that = (DefaultIdentifierId<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
