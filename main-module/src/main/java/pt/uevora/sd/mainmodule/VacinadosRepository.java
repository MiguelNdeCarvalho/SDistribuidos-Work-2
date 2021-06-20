package pt.uevora.sd.mainmodule;

import org.springframework.data.repository.CrudRepository;

public interface VacinadosRepository extends CrudRepository<Centros, Long> {
    Centros findByid(Long ID);
}
