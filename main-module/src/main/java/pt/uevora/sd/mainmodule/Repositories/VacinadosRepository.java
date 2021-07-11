package pt.uevora.sd.mainmodule.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import pt.uevora.sd.mainmodule.Models.Centros;
import pt.uevora.sd.mainmodule.Models.Vacinados;

public interface VacinadosRepository extends CrudRepository<Vacinados, Long> {
    Centros findByid(Long ID);
    List<Vacinados> findByData(LocalDate data);
    List<Vacinados> findAll();
}
