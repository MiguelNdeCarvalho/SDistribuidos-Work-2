package pt.uevora.sd.centermodule.Repositories;

import org.springframework.data.repository.CrudRepository;
import pt.uevora.sd.centermodule.Models.Stock;

public interface StockRepository extends CrudRepository<Stock, Long> {
    Stock findByid(Long ID);
}
