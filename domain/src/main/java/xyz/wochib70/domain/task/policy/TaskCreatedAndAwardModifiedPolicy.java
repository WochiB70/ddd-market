package xyz.wochib70.domain.task.policy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.activity.NoSuchActivityException;
import xyz.wochib70.domain.currency.CurrencyRepository;
import xyz.wochib70.domain.currency.NoSuchCurrencyException;
import xyz.wochib70.domain.task.TaskAward;
import xyz.wochib70.domain.task.events.TaskAwardModifiedEvent;
import xyz.wochib70.domain.task.events.TaskCreatedEvent;

@AllArgsConstructor
@Slf4j
@Component
public class TaskCreatedAndAwardModifiedPolicy {


    private final CurrencyRepository currencyRepository;

    private final ActivityRepository activityRepository;

    @EventListener
    public void handle(TaskCreatedEvent event) {
        log.info("任务创建成功，校验任务奖励配置");
        TaskAward award = event.getTask().getTaskAward();
        switch (award.type()) {
            case CREDENTIALS -> checkActivity(award.awardId());
            case CURRENCY_POINTS -> checkCurrency(award.awardId());
        }
    }

    @EventListener
    public void handle(TaskAwardModifiedEvent event) {
        log.info("任务奖励修改成功，校验任务奖励配置");
        TaskAward award = event.getTaskAward();
        switch (award.type()) {
            case CREDENTIALS -> checkActivity(award.awardId());
            case CURRENCY_POINTS -> checkCurrency(award.awardId());
        }
    }


    private void checkCurrency(IdentifierId<Long> currencyId) {
        try {
            currencyRepository.findByIdOrThrow(currencyId);
        } catch (NoSuchCurrencyException e) {
            throw new IllegalArgumentException("任务奖励的货币不存在");
        }
    }

    private void checkActivity(IdentifierId<Long> activityId) {
        try {
            Activity activity = activityRepository.queryActivityByIdOrThrow(activityId);
            if (!activity.useCredentialLimit()) {
                throw new IllegalArgumentException("任务奖励的活动未开启凭证限制，不能配置其凭证作为奖励");
            }
        } catch (NoSuchActivityException e) {
            throw new IllegalArgumentException("任务奖励的活动不存在，不能配置其凭证作为奖励");
        }
    }
}
