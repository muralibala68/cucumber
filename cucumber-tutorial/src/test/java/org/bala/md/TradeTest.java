package org.bala.md;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TradeTest {

    public void whenTradeIsCreated() {
        Trade t1 = new Trade("prod1", 1.2, 1.3, 1234);
        assertThat(t1.getProductId(), is("prod1"));
        assertThat(t1.getSize(), is(1.2));
        assertThat(t1.getPrice(), is(1.3));
        assertThat(t1.getTimestamp(), is(1234L));
    }

}