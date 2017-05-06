package org.bala.async;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageListener implements Listener {
    private static final Logger LOG = LoggerFactory.getLogger(MessageListener.class);

    private final ExecutorService executorService;
    private final ScheduledExecutorService scheduledExecutorService;
    private final List<Message> cache = new CopyOnWriteArrayList<>(); 

    public MessageListener(final ExecutorService executorService, final ScheduledExecutorService scheduledExecutorService) {
        this.executorService = requireNonNull(executorService);
        this.scheduledExecutorService = requireNonNull(scheduledExecutorService);
    }
    
    @PostConstruct
    public void init() {
        scheduledExecutorService.scheduleWithFixedDelay(() -> checkCacheSize(), 100, 200, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }

    @Override
    public void onMessage(Message message) {
        executorService.execute(() -> handleMessage(message));
    }

    private void handleMessage(Message message) {
        cache.add(message);
        LOG.info("Handled message: {}", message);
    }
    
    private void checkCacheSize() {
        LOG.info("Cache size is {}", cache.size());
    }
    
    public boolean isCacheEmpty() {
        return cache.isEmpty();
    }
}