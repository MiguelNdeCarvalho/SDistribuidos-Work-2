package pt.uevora.sd.centermodule;

import org.springframework.data.repository.CrudRepository;

public interface VacinadoRepository extends CrudRepository<Vacinado, Long> {
    Agendamento findByid(Long ID);
}
