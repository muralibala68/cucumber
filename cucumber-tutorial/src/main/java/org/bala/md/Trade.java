package org.bala.md;

import lombok.Data;

@Data
public class Trade {
    private final String productId;
    private final double size;
    private final double price;
    private final long timestamp;
}