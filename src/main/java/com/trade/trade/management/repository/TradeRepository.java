package com.trade.trade.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trade.trade.management.model.TradeSide;
import com.trade.trade.management.model.TradeStatus;
import com.trade.trade.management.model.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    Page<Trade> findBySymbol(String symbol, Pageable pageable);

    Page<Trade> findByStatus(TradeStatus status, Pageable pageable);

    Page<Trade> findBySide(TradeSide side, Pageable pageable);

    Page<Trade> findBySymbolAndStatus(String symbol, TradeStatus status, Pageable pageable);
}
