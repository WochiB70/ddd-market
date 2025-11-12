package xyz.wochib70.web;

import org.springframework.stereotype.Component;
import xyz.wochib70.infrastructure.event.EventUserIdAware;

@Component
public class EventUserIdAwareImpl implements EventUserIdAware {
    @Override
    public Long getUserId() {
        return AuthorizedThreadLocal.getUserId().id();
    }
}
