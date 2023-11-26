package com.async.job;

import com.async.job.application.JobEventSubjectInteractorTest;
import com.async.job.infra.usecase.RunJobTest;
import com.async.job.infra.usecase.StopJobTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(value = {
        JobEventSubjectInteractorTest.class,
        RunJobTest.class,
        StopJobTest.class
})
public class TestSuite {
}
