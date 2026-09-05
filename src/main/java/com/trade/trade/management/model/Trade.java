package com.trade.trade.management.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name ="trades")
@Data


public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private Integer quantity;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private TradeSide side;

    @Enumerated(EnumType.STRING)
    private TradeStatus status;

    private LocalDateTime timestamp;
    
}
