package org.example.ws.util;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.concurrent.*;

/**
 * Created by z063407 on 6/25/17.
 */
public class AsyncResponse<V> implements Future<V> {

    private V value;
    private Exception executionExeption;
    private boolean isCompletedExceptionally = false;
    private boolean isCancelled = false;
    private boolean isDone = false;
    private long checkCompletedInterval = 100;

    public AsyncResponse() {
    }

    public AsyncResponse(V val) {
        this.value = val;
        this.isDone = true;
    }

    public AsyncResponse(Throwable ex) {
        this.executionExeption = new ExecutionException(ex);
        this.isCompletedExceptionally = true;
        this.isDone = true;
    }

    public boolean cancel (boolean mayInterruptIfRunning){
        this.isCancelled = true;
        this.isDone = true;
        return false;
    }

    public boolean isCompletedExceptionally() {
        return isCompletedExceptionally;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public boolean isDone() {
        return isDone;
    }

    public V get() throws InterruptedException, ExecutionException {
        block(0);

        if(isCancelled()){
            throw new CancellationException();
        }

        if(isCompletedExceptionally()) {
            throw new ExecutionException(this.executionExeption);
        }

        if(isDone()){
            return this.value;
        }

        throw new InterruptedException();
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {

        long timeoutInMills = unit.toMillis(timeout);
        block(timeoutInMills);

        if(isCancelled()){
            throw new CancellationException();
        }

        if(isCompletedExceptionally()) {
            throw new ExecutionException(this.executionExeption);
        }

        if(isDone()){
            return this.value;
        }

        throw new InterruptedException();
    }

    public boolean complete (V val) {
        this.value = val;
        this.isDone = true;
        return true;
    }

    public boolean completeExceptionally(Throwable ex) {
        this.value = null;
        this.executionExeption = new ExecutionException(ex);
        this.isCompletedExceptionally = true;
        this.isDone = true;
        return true;
    }

    public void setCheckCompletedInterval(long millis) {
        this.checkCompletedInterval = millis;
    }

    private void block(long timeout) throws InterruptedException {
        long start = System.currentTimeMillis();

        while (isDone() && !isCancelled()){
            if(timeout > 0) {
                long now = System.currentTimeMillis();
                if(now > start + timeout) {
                    break;
                }
            }
            Thread.sleep(checkCompletedInterval);
        }
    }
}
