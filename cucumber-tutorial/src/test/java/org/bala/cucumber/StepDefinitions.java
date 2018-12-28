package org.bala.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.bala.closure.VolumeWeightedAveragePrice;
import org.bala.md.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefinitions {
    private static final Logger log = LoggerFactory.getLogger(StepDefinitions.class);
    private VolumeWeightedAveragePrice vwap;
    private double averagePrice;
    
    @Given("the list of Trades")
    public void theListOfTrades(final List<Trade> trades) {
//        log.info("dataTable:" + dataTable.toString());

 //       List<Trade> trades = dataTable.asList(Trade.class);
 //       log.info("Input list of Trades size {}", trades.size());
 //       vwap = new VolumeWeightedAveragePrice(trades);
 //       vwap.printAllTrades();
 //       printTimestamp();
    }

    @When("volume weighted average price is calculated")
    public void checkTheCount() {
//        averagePrice = vwap.calculate();
    }
    
    @Then("it should be {double}")
//    @Then("^it should be (\\d+\\.\\d+)$")
    public void averagePriceShouldBe(final double expectedAveragePrice) {
//        assertThat(Math.abs(averagePrice - expectedAveragePrice), is(lessThan(1.0e-4)) );
    }

    private void printTimestamp() {
        log.info("time: {}",System.currentTimeMillis());
        LocalDateTime time = LocalDateTime.now();
        log.info("time: {}",time);
        log.info("time: {}",time.toEpochSecond(ZoneOffset.UTC));
    }
}