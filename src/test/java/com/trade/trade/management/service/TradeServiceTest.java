package com.trade.trade.management.service;

import com.trade.trade.management.dto.TradeRequest;
import com.trade.trade.management.model.Trade;
import com.trade.trade.management.model.TradeSide;
import com.trade.trade.management.model.TradeStatus;
import com.trade.trade.management.repository.TradeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test 
    void createTrade_shouldSaveandReturnTrade(){
        TradeRequest request = new TradeRequest();
        request.setSymbol("AAPL");
        request.setQuantity(10);
        request.setPrice(BigDecimal.valueOf(150.50));
        request.setSide(TradeSide.BUY);
        request.setStatus(TradeStatus.OPEN);
        request.setTimestamp(LocalDateTime.now());

        Trade savedTrade = new Trade();
        savedTrade.setId(1L);
        savedTrade.setSymbol("AAPL");

        when(tradeRepository.save(any(Trade.class))).thenReturn(savedTrade);

        Trade result =tradeService.createTrade(request);

        assertNotNull(result);
        assertEquals("AAPL", result.getSymbol());
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test 
    void getTradeById_shouldThrowException_whenTradeNotFound(){
        when(tradeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(com.trade.trade.management.exception.TradeNotFoundException.class,
                () -> tradeService.getTradeById(999L));
    }
    
}
