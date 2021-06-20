package pt.uevora.sd.centermodule;

import org.springframework.data.repository.CrudRepository;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {
    Agendamento findByid(Long ID);
}
