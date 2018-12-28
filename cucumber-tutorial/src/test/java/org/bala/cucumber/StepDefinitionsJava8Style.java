package org.bala.cucumber;

import cucumber.api.java8.En;
import org.bala.closure.VolumeWeightedAveragePrice;
import org.bala.md.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

/**
 * Java 8 style Lambda based step definition does not work for some reason ;)
 * Please refer to: https://github.com/cucumber/cucumber-jvm/issues/937
 *
 * @author murali
 *
 */
public class StepDefinitionsJava8Style implements En
{
    private static final Logger log = LoggerFactory.getLogger(StepDefinitionsJava8Style.class);

    private VolumeWeightedAveragePrice vwap;
    private double averagePrice;

    public void theListOfTrades(List<Trade> trades) {
        vwap = new VolumeWeightedAveragePrice(trades);
    }

    public void calculateVWAP() {
        averagePrice = vwap.calculate();
    }

    public void averagePriceShouldBe(double expectedAveragePrice) {
        assertThat(Math.abs(averagePrice - expectedAveragePrice), is(lessThan(1.0e-4)) );
    }

    public void givenStep() {
        Given("^the list of Trades Java eight style$", this::theListOfTrades);
        log.info("Is it working ok?");
    }

    public void whenStep() {
        When("^volume weighted average price is calculated Java eight style$", this::calculateVWAP);
    }

    public void thenStep() {
        Then("^it should be (\\d+\\.\\d+) Java eight style$", this::averagePriceShouldBe);
    }
}