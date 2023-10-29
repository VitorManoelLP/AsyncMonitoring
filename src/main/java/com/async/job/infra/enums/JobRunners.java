package com.async.job.infra.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobRunners {

    MOCK_RUNNER("Mock Runner For Tests");

    private final String identifier;

}
