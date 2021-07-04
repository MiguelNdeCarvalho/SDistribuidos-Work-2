package pt.uevora.sd.centermodule.Repositories;

import org.springframework.data.repository.CrudRepository;
import pt.uevora.sd.centermodule.Models.Agendamento;
import pt.uevora.sd.centermodule.Models.Vacinado;

public interface VacinadoRepository extends CrudRepository<Vacinado, Long> {
    Agendamento findByid(Long ID);
}
