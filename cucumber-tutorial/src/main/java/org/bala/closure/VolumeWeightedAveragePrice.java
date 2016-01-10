package org.bala.closure;

import java.util.List;
import static com.google.common.base.Preconditions.checkState;

import org.bala.md.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VolumeWeightedAveragePrice {
   private static final Logger LOGGER = LoggerFactory.getLogger(VolumeWeightedAveragePrice.class); 
    
    private final List<Trade> trades;
    
    public VolumeWeightedAveragePrice(List<Trade> trades) {
        this.trades = trades;
    }
    
    public void printAllTrades() {
        trades.stream().map(Trade::toString).forEach(LOGGER::info);
    }

    public long getAllTradesCount() {
        return trades.stream().count();
    }

    public void printValidTrades() {
        trades.stream().filter(Trade::isValid).map(Trade::toString).forEach(LOGGER::info);
    }

    public long getValidTradesCount() {
        return trades.stream().filter(Trade::isValid).count();
    }

    public void printAllTradeValues() {
        trades.stream().map(Trade::getValue).map(String::valueOf).forEach(LOGGER::info);
    }
    
    public void printValidTradeValues() {
        trades.stream().filter(Trade::isValid).map(Trade::getValue).map(String::valueOf).forEach(LOGGER::info);
    }

    private Double getSumOfAllTradeSizes() {
        return trades.stream().mapToDouble(Trade::getSize).sum();
    }

    private Double getSumOfAllTradeValues() {
        return trades.stream().mapToDouble(Trade::getValue).sum();
    }

    public Double calculate() {
        final Double sumOfAllTradeValues = getSumOfAllTradeValues();
        checkState(isValid(sumOfAllTradeValues), "SumOfAllTradeValues is Invalid");
        final Double sumOfAllTradeSizes = getSumOfAllTradeSizes();
        checkState(isValid(sumOfAllTradeSizes) && sumOfAllTradeSizes > 0.0d, "SumOfAllTradeSizes is Invalid");
        final Double vwap = sumOfAllTradeValues / sumOfAllTradeSizes;
        checkState(isValid(vwap) && vwap > 0.0d, "VWAP calculated is Invalid");
        return vwap;
    }
    
    private boolean isValid(Double value) {
        return !value.isNaN() && !value.isInfinite();
    }
}