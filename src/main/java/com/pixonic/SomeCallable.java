package com.pixonic;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * Project TestProject
 * Created by s1av0k on 12.09.2018.
 */
public class SomeCallable implements Callable {

    private LocalDateTime localDateTime;

    public SomeCallable(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public Object call() throws Exception {
        System.out.println(localDateTime + " Важная и ответственная работа");
        return null;
    }
}
