package xyz.wochib70.domain.currency;

import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

public record CurrencyInfo(
        String name,
        String description
) {


    public CurrencyInfo {
        ParameterUtil.requireExpression(Objects.isNull(name) || name.isBlank(), "Currency name不能为null或是空字符串");
        ParameterUtil.requireExpression(name.length() > 20, "Currency name长度不能超过20个字符");
        description = Objects.requireNonNullElse(description, "");
        ParameterUtil.requireExpression(description.length() > 50, "Currency description长度不能超过50个字符");
    }

}
