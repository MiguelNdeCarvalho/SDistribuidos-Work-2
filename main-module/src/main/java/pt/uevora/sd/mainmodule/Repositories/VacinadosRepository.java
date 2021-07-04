package pt.uevora.sd.mainmodule.Repositories;

import org.springframework.data.repository.CrudRepository;
import pt.uevora.sd.mainmodule.Models.Centros;

public interface VacinadosRepository extends CrudRepository<Centros, Long> {
    Centros findByid(Long ID);
}
