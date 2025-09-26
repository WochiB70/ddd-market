package xyz.wochib70.domain.account.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.account.Account;
import xyz.wochib70.domain.account.AccountRepository;
import xyz.wochib70.domain.task.Task;
import xyz.wochib70.domain.task.TaskAward;
import xyz.wochib70.domain.task.TaskAwardType;
import xyz.wochib70.domain.task.TaskRepository;
import xyz.wochib70.domain.usertask.UserTask;
import xyz.wochib70.domain.usertask.UserTaskRepository;
import xyz.wochib70.domain.usertask.events.UserTaskCompletedEvent;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserTaskCompletedPolicy {


    private final TaskRepository taskRepository;

    private final AccountRepository accountRepository;

    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handle(UserTaskCompletedEvent event) {
        Task task = taskRepository.queryTaskByIdOrThrow(event.getTaskId());
        TaskAward award = task.getTaskAward();
        if (!Objects.equals(award.type(), TaskAwardType.CURRENCY_POINTS)) {
            return;
        }
        log.info("用户任务完成，任务ID：{}, 建立类型为CURRENCY_POINTS,为用户{}发送货币{}， 数量{}", event.getTaskId(), event.getUserId(), award.awardId(), award.count());
        Account account = accountRepository.queryAccountByCurrencyIdAndUserId(award.awardId(), event.getUserId());
        account.increateBalance(award.count());
        accountRepository.update(account);
        account.getEvents().forEach(eventPublisher::publishEvent);
        log.info("用户任务完成，任务ID：{}, 建立类型为CURRENCY_POINTS,为用户{}发送货币{}， 数量{}成功", event.getTaskId(), event.getUserId(), award.awardId(), award.count());
    }

}
