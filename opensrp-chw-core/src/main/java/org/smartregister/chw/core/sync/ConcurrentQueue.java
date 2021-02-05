package org.smartregister.chw.core.sync;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentQueue {

    private final ConcurrentLinkedQueue<Runnable> concurrentLinkedQueue = new ConcurrentLinkedQueue<Runnable>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public Runnable dequeueItem() {
        if (!concurrentLinkedQueue.isEmpty()) {
            System.out.println("Queue size: " + concurrentLinkedQueue.size());
            return concurrentLinkedQueue.remove();
        } else {
            return null;
        }
    }

    public void enqueueItem(Runnable item) {
        concurrentLinkedQueue.add(item);
    }

    public void run() {
        while (concurrentLinkedQueue.peek() != null) {
            Runnable runnable = dequeueItem();
            executorService.execute(runnable);
        }
    }
}
