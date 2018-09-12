package com.pixonic;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CallableExecutor {

    private static final int POOL_SIZE = 1;

    private ScheduledExecutorService executor;


    public CallableExecutor() {
        executor = Executors.newScheduledThreadPool(POOL_SIZE);
    }

    /**
     * Из условия задачи не ясно ("Для решения можно использовать любые встроенные средства."(с)), можно ли использовать БД.
     * Считается ли embedded database - встроенным средством...
     * Есть и плюсы, и минусы, требуется анализ рациональности её использования, исходя из природы и объема потока данных.
     * Плюсы:
     * 1. Задачи на обработку не хранятся в оперативной памяти
     * 2. Потенциально удобнее организовать сортировку (приоритеты задач), если сортировки сложные, а критериев (полей) сортировки много
     * Минусы:
     * 1. Издержки на сохранение задачи в БД, вычитывания, обновления (пометка, что задача отработала) или удаления отработанных задач
     * 2. Отслеживание освободившихся потоков в пуле, чтобы нагрузить их снова
     * <p/>
     * В текущей реализации (без БД), при нехватке обрабатывающих потоков, задачи на обработку будут добавляться в очередь согласно
     * времени отложенного запуска. Но до запуска задачи будут храниться в оперативной памяти, что не всегда позволительно.
     * <p/>
     * Согласно javadoc, задачи, запланированные для выполнения в одно и то же время, выполняются в порядке FIFO.
     */
    public void schedule(LocalDateTime localDateTime, Callable callable) {
        long delay = getDelayUntil(localDateTime);
        executor.schedule(callable, delay, TimeUnit.NANOSECONDS);
        System.out.println("Scheduled: " + localDateTime + " " + callable);
    }

    private static long getDelayUntil(LocalDateTime localDateTime) {
        return LocalDateTime.now().until(localDateTime, ChronoUnit.NANOS);
    }
}