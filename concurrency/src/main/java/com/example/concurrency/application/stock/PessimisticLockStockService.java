package com.example.concurrency.application.stock;

import com.example.concurrency.domain.stock.Stock;
import com.example.concurrency.domain.stock.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessimisticLockStockService {

  private final StockRepository stockRepository;

  public PessimisticLockStockService(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  @Transactional
  public void decrease(Long id, Long quantity) {
    Stock stock = stockRepository.findByIdWithPessimisticLock(id);
    stock.decrease(quantity);
    stockRepository.saveAndFlush(stock);
  }
}
