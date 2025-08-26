package dev.ivan.searchlab.web.runtime;

import jakarta.inject.Singleton;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class IndexState {
    private final AtomicBoolean ready = new AtomicBoolean(false);

    public boolean isReady() { return ready.get(); }
    public void markReady()  { ready.set(true); }
    public void markNotReady() { ready.set(false); }
}
