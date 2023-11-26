package com.async.job.infra.gateway;

import java.util.List;

public interface ThreadScannerGateway {

    List<Thread> findAllThreads();
    List<Thread> findAllThreadsByName(String name);

}
