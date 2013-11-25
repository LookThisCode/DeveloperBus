package br.com.expenseme.core;

public class TimerPausedEvent {

    private boolean timerIsPaused;

    public TimerPausedEvent(boolean timerIsPaused) {
        this.timerIsPaused = timerIsPaused;
    }

    public boolean isTimerIsPaused() {
        return timerIsPaused;
    }
}
