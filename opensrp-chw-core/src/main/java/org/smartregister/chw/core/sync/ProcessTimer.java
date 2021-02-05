package org.smartregister.chw.core.sync;

public class ProcessTimer {
    private String event;
    private long start;
    private long end;
    private static ProcessTimer processTimer;

    private ProcessTimer() {
    }

    public static void startTiming(String event) {
        processTimer = new ProcessTimer();
        processTimer.event = event;
        processTimer.start = System.currentTimeMillis();
    }

    public static void endTiming() {
        processTimer.end = System.currentTimeMillis();
        long elapsedTime = processTimer.end - processTimer.start;
        System.out.println(processTimer.event + " was processed in : " + elapsedTime);
    }
}
