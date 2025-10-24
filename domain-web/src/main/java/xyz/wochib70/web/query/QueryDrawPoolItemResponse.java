package xyz.wochib70.web.query;

import lombok.Data;
import xyz.wochib70.domain.draw.DrawInventoryType;
import xyz.wochib70.domain.draw.DrawItemType;

@Data
public class QueryDrawPoolItemResponse {

    private Long id;

    private Long drawPoolId;

    private String name;

    private String description;

    private DrawItemType type;

    private Integer weight;

    private DrawInventory inventory;

    public record DrawInventory(
            DrawInventoryType type,
            Integer surplus
    ) {
    }
}
