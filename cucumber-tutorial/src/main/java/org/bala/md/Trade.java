package org.bala.md;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Trade {
    private final String productId;
    private final double size;
    private final double price;
    private final long timestamp;
    
    public Trade(final String productId,
                 final double size,
                 final double price,
                 final long timestamp) {
        this.productId = productId;
        this.size = size;
        this.price = price;
        this.timestamp = System.currentTimeMillis();
        System.out.println("<*** Cucumber does not call the Constructor while reading off the data table? ***>");
    }

    public String getProductId() {
        return productId;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }
    
    public long getTimestamp() {
        return timestamp;
    }

    public String getTimestampString(final ZoneOffset offset) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, offset).toString();
    }
    
    public double getValue() {
        return size * price;
    }
    
    public boolean isValid() {
        return size > 0.0d && price > 0.0d;
    }
    
    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
    
    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }
    
    @Override
    public boolean equals(final Object rhs) {
        return Pojomatic.equals(this, rhs);
    }
}