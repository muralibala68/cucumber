package org.bala.generics;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class PairTest {
    @Test
    public void testPair() {
        final Pair<String,Integer> pair = new Pair<>("Murali", 54);
        assertThat(pair.getFirst(), is("Murali"));
        assertThat(pair.getSecond(), is(54));
    }
}
