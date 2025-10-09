package xyz.wochib70.domain.currency;

import java.util.Objects;

public record CurrencyInfo(
        String name,
        String description
) {


    public CurrencyInfo {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("Currency name不能为null或是空字符串");
        }
        if (name.length() > 20) {
            throw new IllegalArgumentException("Currency name长度不能超过20个字符");
        }
        description = Objects.requireNonNullElse(description, "");
        if (description.length() > 50) {
            throw new IllegalArgumentException("Currency description长度不能超过50个字符");
        }
    }

}
