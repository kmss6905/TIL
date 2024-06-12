package database.distributelock.service;

import database.distributelock.domain.Stock;
import database.distributelock.repository.StockRepository;
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
    Stock stock = stockRepository.findByWithPessimisticLock(id);
    stock.decrease(quantity);
    stockRepository.save(stock);
  }
}
