package pt.uevora.sd.centermodule.Repositories;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import pt.uevora.sd.centermodule.Models.Stock;

public interface StockRepository extends CrudRepository<Stock, Long> {
    Stock findOneByData(LocalDate data);
}
