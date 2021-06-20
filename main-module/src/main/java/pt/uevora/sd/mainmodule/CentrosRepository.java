package pt.uevora.sd.mainmodule;

import org.springframework.data.repository.CrudRepository;

public interface CentrosRepository extends CrudRepository<Centros, Long> {
    Centros findByid(Long ID);
}
