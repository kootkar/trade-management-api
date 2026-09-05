package com.trade.trade.management.service;

import java.util.List;
import com.trade.trade.management.dto.TradeRequest;
import com.trade.trade.management.exception.TradeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trade.trade.management.model.TradeStatus;
import com.trade.trade.management.model.TradeSide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import com.trade.trade.management.model.Trade;
import com.trade.trade.management.repository.TradeRepository;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    public Trade createTrade(TradeRequest request){
        Trade trade = new Trade();
        trade.setSymbol(request.getSymbol());
        trade.setQuantity(request.getQuantity());
        trade.setPrice(request.getPrice());
        trade.setSide(request.getSide());
        trade.setStatus(request.getStatus());
        trade.setTimestamp(request.getTimestamp());
        return tradeRepository.save(trade);
    }

    public List<Trade> getALLTrades(){
        return tradeRepository.findAll();
    }

    public Page<Trade> getTrades(String symbol, TradeStatus status, TradeSide side, int page, int size, String sortBy){
       Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy));
        if (symbol != null && status!= null){
            return tradeRepository.findBySymbolAndStatus(symbol,status,pageable);
        } else if(symbol != null) {
            return tradeRepository.findBySymbol(symbol, pageable);
        } else if (status != null) {
            return tradeRepository.findByStatus(status, pageable);
        } else if (side != null)  {
            return tradeRepository.findBySide(side, pageable);
        }

        return tradeRepository.findAll(pageable);
    }

    public Trade getTradeById (Long id){
        return tradeRepository.findById(id)
                .orElseThrow(() -> new TradeNotFoundException(id));
    }
    public Trade updateTrade(Long id,TradeRequest request)  {
        Trade trade = getTradeById(id);
        trade.setSymbol(request.getSymbol());
        trade.setQuantity(request.getQuantity());
        trade.setPrice(request.getPrice());
        trade.setSide(request.getSide());
        trade.setStatus(request.getStatus());
        trade.setTimestamp(request.getTimestamp());
        return tradeRepository.save(trade);
    }

    public void deleteTrade(Long id) {
        tradeRepository.deleteById(id);

    }
}
