package org.bala.closure;

import java.util.List;
import static com.google.common.base.Preconditions.checkState;

import org.bala.md.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VolumeWeightedAveragePrice {
   private static final Logger log = LoggerFactory.getLogger(VolumeWeightedAveragePrice.class);
    
    private final List<Trade> trades;
    
    public VolumeWeightedAveragePrice(final List<Trade> trades) {
        this.trades = trades;
    }
    
    public void printAllTrades() {
        trades.stream().map(Trade::toString).forEach(log::info);
    }

    public long getAllTradesCount() {
        return trades.stream().count();
    }

    public void printValidTrades() {
        trades.stream().filter(this::isValid).map(Trade::toString).forEach(log::info);
    }

    public long getValidTradesCount() {
        return trades.stream().filter(this::isValid).count();
    }

    public void printAllTradeValues() {
        trades.stream().map(this::getValue).map(String::valueOf).forEach(log::info);
    }
    
    public void printValidTradeValues() {
        trades.stream().filter(this::isValid).map(this::getValue).map(String::valueOf).forEach(log::info);
    }

    private Double getSumOfAllTradeSizes() {
        return trades.stream().mapToDouble(Trade::getSize).sum();
    }

    private Double getSumOfAllTradeValues() {
        return trades.stream().mapToDouble(this::getValue).sum();
    }

    private Double getValue(final Trade trade) {
        return trade.getSize() * trade.getPrice();
    }

    private boolean isValid(final Trade trade) {
        return trade.getSize() > 0.0d && trade.getPrice() > 0.0d;
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