package org.bala.async;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageListener implements Listener {
    private static final Logger LOG = LoggerFactory.getLogger(MessageListener.class);

    private ExecutorService executorService;
    private List<Message> cache = new CopyOnWriteArrayList<>(); 

    public MessageListener(final ExecutorService executorService) {
        this.executorService = requireNonNull(executorService);
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public void onMessage(Message message) {
        executorService.execute(() -> handleMessage(message));
    }

    private void handleMessage(Message message) {
        cache.add(message);
        LOG.info("Handled message: {}", message);
    }
    
    public boolean isCacheEmpty() {
        return cache.isEmpty();
    }
}