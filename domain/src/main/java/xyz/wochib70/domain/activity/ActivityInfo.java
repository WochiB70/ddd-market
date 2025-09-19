package xyz.wochib70.domain.activity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record ActivityInfo(
        String name,
        String description,
        List<String> images
) {

    public ActivityInfo {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("activity name不能为null或是空白");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("activity name长度不能超过50");
        }
        if (Objects.nonNull(description) && description.length() > 200) {
            throw new IllegalArgumentException("activity description长度不能超过200");
        }
        images = Objects.isNull(images) ? Collections.emptyList() : images;
        if (images.size() > 5) {
            throw new IllegalArgumentException("activity images数量不能超过5");
        }

    }
}
