package pt.uevora.sd.mainmodule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class CentroController {

    @Autowired
    private CentrosRepository centrosRepository;
    
    @PostMapping(
		path = "/newCentro",
		consumes = "application/json",
		produces = "application/json")
	Centros newCentro(@RequestBody Centros newCentro){

		System.out.println(newCentro.toString());

		return centrosRepository.save(newCentro);
	}

	@GetMapping(
        path = "/getCentros",
        produces = "application/json")
    List<Centros> getCentros(){
        return (List<Centros>) centrosRepository.findAll();
    }

	@GetMapping(
        path = "/getCentro/{nome}",
        produces = "application/json")
    Centros getCentroByNome(@PathVariable String nome){
        return centrosRepository.findOneByNome(nome);
    }

	@DeleteMapping("/deleteCentro/{id}")
	void deleteEmployee(@PathVariable Long id) {
		centrosRepository.deleteById(id);
	}
}