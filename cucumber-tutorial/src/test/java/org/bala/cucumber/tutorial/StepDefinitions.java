package org.bala.cucumber.tutorial;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.bala.closure.VolumeWeightedAveragePrice;
import org.bala.md.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitions.class); 
    private VolumeWeightedAveragePrice vwap;
    private double averagePrice;
    
    @Given("^the list of Trades$")
    public void theListOfTrades(List<Trade> trades) {
        LOGGER.info("Input list of Trades size {}", trades.size());
        vwap = new VolumeWeightedAveragePrice(trades);
        vwap.printAllTrades();
        LOGGER.info("time: {}",System.currentTimeMillis());
        LocalDateTime time = LocalDateTime.now(); 
        LOGGER.info("time: {}",time);
        LOGGER.info("time: {}",time.toEpochSecond(ZoneOffset.UTC));
    }

    @When("^volume weighted average price is calculated$")
    public void checkTheCount() {
        averagePrice = vwap.calculate();
    }
    
    @Then("^it should be (\\d+\\.\\d+)$")
    public void averagePriceShouldBe(double expectedAveragePrice) {
        assertThat(Math.abs(averagePrice - expectedAveragePrice), is(lessThan(1.0e-4)) );
    }
}