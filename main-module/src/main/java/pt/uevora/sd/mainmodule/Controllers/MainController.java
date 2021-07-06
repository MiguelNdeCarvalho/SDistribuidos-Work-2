package pt.uevora.sd.mainmodule.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.uevora.sd.mainmodule.Models.*;
import pt.uevora.sd.mainmodule.Repositories.*;

@RestController
@RequestMapping(path = "/api/v1")
public class MainController {

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

	@DeleteMapping("/deleteCentro")
	void deleteCentro(@RequestParam Long id) {
		centrosRepository.deleteById(id);
	}

    @GetMapping(
        path = "/nTotalVacinas",
        produces = "application/json")
    Centros getTotalVacinas(@RequestParam String nome){
        return centrosRepository.findOneByNome(nome);
    }

    @GetMapping(
        path = "/fornecerVacinas",
        produces = "application/json")
    Centros getFornecerVacinas(@RequestParam String nome){
        return centrosRepository.findOneByNome(nome);
    }
}