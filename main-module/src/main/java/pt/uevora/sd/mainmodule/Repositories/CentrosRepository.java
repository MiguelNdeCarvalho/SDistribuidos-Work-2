package pt.uevora.sd.mainmodule.Repositories;

import org.springframework.data.repository.CrudRepository;
import pt.uevora.sd.mainmodule.Models.Centros;

public interface CentrosRepository extends CrudRepository<Centros, Long> {
    Centros findByCC(Long cc);
    Centros findOneByNome(String nome);
}
