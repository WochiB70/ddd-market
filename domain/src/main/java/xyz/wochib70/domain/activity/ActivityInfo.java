package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record ActivityInfo(
        String name,
        String description,
        List<String> images
) {

    public ActivityInfo {
        ParameterUtil.requireNonBlank(name, "activity name不能为null");
        ParameterUtil.requireExpression(name.length() > 50, "activity name长度不能超过50");
        ParameterUtil.requireExpression(Objects.nonNull(description) && description.length() > 200,
                "activity description长度不能超过200"
        );
        images = Objects.isNull(images) ? Collections.emptyList() : images;
        ParameterUtil.requireExpression(images.size() > 5, "activity images数量不能超过5");
    }
}
