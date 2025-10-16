package xyz.wochib70.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.account.AccountIdGenerator;
import xyz.wochib70.domain.activity.ActivityIdGenerator;
import xyz.wochib70.domain.credential.CredentialIdGenerator;
import xyz.wochib70.domain.currency.CurrencyIdGenerator;
import xyz.wochib70.domain.draw.DrawItemIdGenerator;
import xyz.wochib70.domain.draw.DrawPoolIdGenerator;
import xyz.wochib70.domain.redeem.RedeemItemIdGenerator;
import xyz.wochib70.domain.redeem.RedeemPoolIdGenerator;
import xyz.wochib70.domain.task.TaskIdGenerator;
import xyz.wochib70.domain.usertask.UserTaskIdGenerator;

import java.util.UUID;

@AllArgsConstructor
@Component
public class DefaultIdGeneratorByDataBaseImpl implements AccountIdGenerator, ActivityIdGenerator,
        CredentialIdGenerator, CurrencyIdGenerator,
        DrawPoolIdGenerator, RedeemPoolIdGenerator,
        TaskIdGenerator, UserTaskIdGenerator,
        DrawItemIdGenerator, RedeemItemIdGenerator {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String generateUsageCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public IdentifierId<Long> nextAggregateId(String aggregateName) {
        return new DefaultIdentifierId<>(jdbcTemplate.queryForObject("SELECT NEXTVAL('" + aggregateName + "_seq')", Long.class));
    }

    @Override
    public IdentifierId<Long> nextAwardId() {
        return nextAggregateId("award_item");
    }

    @Override
    public IdentifierId<Long> nextRedeemItemId() {
        return nextAggregateId("redeem_item");
    }
}
