package xyz.wochib70.web.query;

import lombok.Data;
import xyz.wochib70.domain.currency.CurrencyStatus;

@Data
public class QueryCurrencyResponse {

    private Long id;

    private String name;

    private CurrencyStatus status;
}
