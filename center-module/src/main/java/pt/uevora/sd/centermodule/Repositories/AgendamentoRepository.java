package pt.uevora.sd.centermodule.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import pt.uevora.sd.centermodule.Models.Agendamento;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {
    Agendamento findOneByCc(Long cc);
    Agendamento findOneByNome(String nome);
    List<Agendamento> findAllByData(LocalDate data);
    List<Agendamento> findAllByOrderByIdadeDesc();
    long deleteByCc(Long cc);
}
