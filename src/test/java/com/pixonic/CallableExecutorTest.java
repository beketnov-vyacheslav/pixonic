package com.pixonic;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CallableExecutorTest {

    private CallableExecutor executor;

    @Before
    public void setUp() {
        executor = new CallableExecutor();
    }

    @Test
    public void scheduleNow() {
        LocalDateTime now = LocalDateTime.now();
        executor.schedule(now, new SomeCallable(now));
    }

    @Test
    public void scheduleNegativeDelay() {
        LocalDateTime dateTime = LocalDateTime.now().minus(1000L, ChronoUnit.SECONDS);
        executor.schedule(dateTime, new SomeCallable(dateTime));
    }

    @Test
    public void scheduleSeveral() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();

        executor.schedule(now, new SomeCallable(now));

        now = LocalDateTime.now();
        LocalDateTime dateTimeBefore = now.minus(1000L, ChronoUnit.SECONDS);
        executor.schedule(dateTimeBefore, new SomeCallable(dateTimeBefore));

        now = LocalDateTime.now();
        LocalDateTime dateTimeAfter = now.plus(5L, ChronoUnit.SECONDS);
        executor.schedule(dateTimeAfter, new SomeCallable(dateTimeAfter));
    }

    @Test
    public void scheduleSeveralEqualDateTime() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTimeAfter = now.plus(5L, ChronoUnit.SECONDS);
        for (int i = 0; i < 10; ++i) {
            executor.schedule(dateTimeAfter, new SomeCallable(dateTimeAfter));
        }
    }
}