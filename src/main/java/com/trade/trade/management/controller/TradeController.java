package com.trade.trade.management.controller;

import com.trade.trade.management.model.Trade;
import com.trade.trade.management.dto.TradeRequest;
import com.trade.trade.management.model.TradeStatus;
import com.trade.trade.management.model.TradeSide;
import com.trade.trade.management.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/trades")


public class TradeController {
    @Autowired
    private TradeService tradeService;

    @PostMapping
    public Trade createTrade(@Valid @RequestBody TradeRequest request) {
        return tradeService.createTrade(request);
    }

    @GetMapping
    public Page<Trade> getALLTrades(
        @RequestParam(required =false) String symbol,
        @RequestParam(required = false) TradeStatus status,
        @RequestParam(required =false) TradeSide side,
        @RequestParam(defaultValue ="0") int page,
        @RequestParam(defaultValue ="10") int size,
        @RequestParam(defaultValue ="id") String sortBy ) {
        return tradeService.getTrades(symbol, status, side, page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Trade getTradeById (@PathVariable Long id){
        return tradeService.getTradeById(id);
    }

    @PutMapping("/{id}")
    public Trade updateTrade(@PathVariable Long id,@Valid @RequestBody TradeRequest request){
        return tradeService.updateTrade(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteTrade(@PathVariable Long id) {
        tradeService.deleteTrade(id);
    }

    
}
