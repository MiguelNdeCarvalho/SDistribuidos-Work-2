package pt.uevora.sd.centermodule.Repositories;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

import pt.uevora.sd.centermodule.Models.Vacinado;

public interface VacinadoRepository extends CrudRepository<Vacinado, Long> {
    Vacinado findByCc(Long cc);
    List<Vacinado> findByData(LocalDate data);
}
