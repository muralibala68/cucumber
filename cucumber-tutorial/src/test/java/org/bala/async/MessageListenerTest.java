package org.bala.async;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageListenerTest {
    private MessageListener messageListener;
    
    @Mock private ExecutorService mockExecutorService;
    @Mock private ScheduledExecutorService mockScheduledExecutorService;
    @Mock private Message mockMessage;
    
    @Captor private ArgumentCaptor<Runnable> runnableCaptor;

    @Rule public ExpectedException expectedException = ExpectedException.none(); 

    @Test
    public void whenExecutorServiceIsNullConstructorToThrow() {
        expectedException.expect(NullPointerException.class);
        messageListener = new MessageListener(null, mockScheduledExecutorService);
    }
    
    @Test
    public void whenScheduledExecutorServiceIsNullConstructorToThrow() {
        expectedException.expect(NullPointerException.class);
        messageListener = new MessageListener(mockExecutorService, null);
    }
    
    @Test
    public void whenAllArgumentsAreValidConstructorNotToThrow() {
        messageListener = new MessageListener(mockExecutorService, mockScheduledExecutorService);
    }

    @Test
    public void whenOnMessageIsCalled() {
        messageListener = new MessageListener(mockExecutorService, mockScheduledExecutorService);
        messageListener.onMessage(mockMessage);
        
        verify(mockExecutorService).execute(runnableCaptor.capture());

        assertThat(messageListener.isCacheEmpty(), is(true));
        runnableCaptor.getValue().run();
        assertThat(messageListener.isCacheEmpty(), is(false));
    }

    @Test
    public void whenInitIsCalledScheduledExecutorServiceIsScheduled() {
        messageListener = new MessageListener(mockExecutorService, mockScheduledExecutorService);
        messageListener.init();
        
        verify(mockScheduledExecutorService).scheduleWithFixedDelay(runnableCaptor.capture(), anyLong(), anyLong(), eq(TimeUnit.MILLISECONDS));
        runnableCaptor.getValue().run();
    }

    @Test
    public void whenShutdownIsCalledAllExecutorServicesAreShutdown() {
        messageListener = new MessageListener(mockExecutorService, mockScheduledExecutorService);
        messageListener.shutdown();
        
        verify(mockExecutorService).shutdown();
        verify(mockScheduledExecutorService).shutdown();
    }
}