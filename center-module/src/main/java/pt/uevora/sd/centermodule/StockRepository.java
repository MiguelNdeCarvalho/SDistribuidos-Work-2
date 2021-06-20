package pt.uevora.sd.centermodule;

import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> {
    Stock findByid(Long ID);
}
