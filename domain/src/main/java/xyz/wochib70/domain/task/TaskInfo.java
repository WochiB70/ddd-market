package xyz.wochib70.domain.task;

public record TaskInfo(
        String name,
        String description
) {

    public TaskInfo {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("任务名称不能为空");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("任务描述不能为空");
        }
    }

}
