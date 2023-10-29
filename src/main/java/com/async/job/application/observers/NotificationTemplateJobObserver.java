package com.async.job.application.observers;

import com.async.job.infra.domain.JobEvent;
import com.async.job.infra.gateway.JobEventObserverGateway;

public class NotificationTemplateJobObserver  implements JobEventObserverGateway {

    @Override
    public void next(JobEvent job) {
        // TODO NOTIFICAR VIA WEBSOCKET
        System.out.println(job);
    }

}
