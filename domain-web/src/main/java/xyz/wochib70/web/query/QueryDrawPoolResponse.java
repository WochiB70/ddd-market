package xyz.wochib70.web.query;

import lombok.Data;
import xyz.wochib70.domain.draw.DrawStrategyType;

@Data
public class QueryDrawPoolResponse {

    private Long id;

    private Long activityId;

    private String name;

    private DrawStrategyType strategyType;

    private DrawPrice drawPrice;


    public record DrawPrice(
            Long currencyId,
            Integer price
    ) {
    }
}
