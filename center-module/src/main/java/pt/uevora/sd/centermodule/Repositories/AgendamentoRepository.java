package pt.uevora.sd.centermodule.Repositories;

import org.springframework.data.repository.CrudRepository;
import pt.uevora.sd.centermodule.Models.Agendamento;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {
    Agendamento findByid(Long ID);
}
