package pt.uevora.sd.mainmodule.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.util.JSONPObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
        path = "/getVacinasPorDia")
    Long getVacinasPorDiaByNome(@RequestParam String nome){
        return centrosRepository.findOneByNome(nome).getVacinadosPorDia();
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
    void getFornecerVacinas(@RequestParam String n_vacinas, String data) throws ClientProtocolException, IOException{
        
        List<Centros> centros = (List<Centros>) centrosRepository.findAll();
        HashMap<String,JSONPObject> association= new HashMap<>();  
        for (Centros centro : centros) {
            
            //pedido getAgendamentosByData
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://localhost:8000/api/v1/getVacinasPorDia");
    
            try (CloseableHttpResponse response = httpClient.execute(request)) {
    
                // Get HttpResponse Status
                System.out.println(response.getStatusLine().toString());
                HttpEntity entity = response.getEntity();

    
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                }
    
            }
        }
        
        //merge

        //contagem
        

    }
}