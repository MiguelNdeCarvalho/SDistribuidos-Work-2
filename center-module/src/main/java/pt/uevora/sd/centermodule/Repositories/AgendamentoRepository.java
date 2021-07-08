package pt.uevora.sd.centermodule.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import pt.uevora.sd.centermodule.Models.Agendamento;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {
    Agendamento findOneByCc(Long cc);
    Agendamento findOneByNome(String nome);
    List<Agendamento> findAllByOrderByIdadeDesc();
}
