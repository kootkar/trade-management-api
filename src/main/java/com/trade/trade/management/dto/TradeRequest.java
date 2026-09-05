package com.trade.trade.management.dto;

import com.trade.trade.management.model.TradeSide;
import com.trade.trade.management.model.TradeStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeRequest {

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotNull(message ="Quantity is required")
    @Positive(message ="Quantity must be positive")
    private Integer quantity;

    @NotNull(message ="Price is required")
    @Positive(message ="Price must be positive")
    private BigDecimal price;

    @NotNull(message ="Side is required")
    private TradeSide side;

    @NotNull(message = "Status is required")
    private TradeStatus status;

    @NotNull(message ="Timestamp is required")
    private LocalDateTime timestamp;
    
}
