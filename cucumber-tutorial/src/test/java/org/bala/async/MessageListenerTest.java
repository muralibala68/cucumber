package org.bala.async;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutorService;

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
    @Mock private Message mockMessage;
    
    @Captor private ArgumentCaptor<Runnable> runnableCaptor;

    @Rule public ExpectedException expectedException = ExpectedException.none(); 

    @Test
    public void whenExecutorServiceIsNullConstructorToThrow() {
        expectedException.expect(NullPointerException.class);
        messageListener = new MessageListener(null);
    }
    
    @Test
    public void whenExecutorServiceIsNotNullConstructorNotToThrow() {
        messageListener = new MessageListener(mockExecutorService);
    }

    @Test
    public void whenOnMessageIsCalled() {
        messageListener = new MessageListener(mockExecutorService);
        messageListener.onMessage(mockMessage);
        
        verify(mockExecutorService).execute(runnableCaptor.capture());

        assertThat(messageListener.isCacheEmpty(), is(true));
        runnableCaptor.getValue().run();
        assertThat(messageListener.isCacheEmpty(), is(false));
    }
}
