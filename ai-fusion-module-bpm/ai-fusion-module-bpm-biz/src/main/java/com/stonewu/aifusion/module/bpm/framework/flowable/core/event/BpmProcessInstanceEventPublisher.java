package com.stonewu.aifusion.module.bpm.framework.flowable.core.event;

import com.stonewu.aifusion.module.bpm.event.BpmProcessInstanceStatusEvent;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;

/**
 * {@link BpmProcessInstanceStatusEvent} 的生产者
 *
 *
 */
@AllArgsConstructor
@Validated
public class BpmProcessInstanceEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void sendProcessInstanceResultEvent(@Valid BpmProcessInstanceStatusEvent event) {
        publisher.publishEvent(event);
    }

}
