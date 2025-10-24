package xyz.wochib70.web.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.infrastructure.currency.QCurrencyEntity;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "货币查询接口", description = "提供货币信息的查询接口")
public class QueryCrrencysController {

    private final JPAQueryFactory queryFactory;

    @GetMapping("/query/currencys")
    @Operation(summary = "查询货币列表", description = "获取所有货币的基本信息")
    @ApiResponse(responseCode = "200", description = "成功返回货币列表", 
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = QueryCurrencysResponse.class)))
    public List<QueryCurrencysResponse> queryCurrencys() {
        QCurrencyEntity currency = QCurrencyEntity.currencyEntity;
        return queryFactory.select(
                        currency.id,
                        currency.name,
                        currency.status
                )
                .from(currency)
                .fetch()
                .stream()
                .map(it -> {
                    QueryCurrencysResponse response = new QueryCurrencysResponse();
                    response.setId(it.get(currency.id));
                    response.setName(it.get(currency.name));
                    response.setStatus(it.get(currency.status));
                    return response;
                })
                .toList();
    }
}