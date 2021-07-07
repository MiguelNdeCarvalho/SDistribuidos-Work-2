package pt.uevora.sd.centermodule.Controllers;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;
import java.net.*;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.uevora.sd.centermodule.Models.*;
import pt.uevora.sd.centermodule.Repositories.*;

@RestController
@RequestMapping(path = "/api/v1")
public class CenterController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @PostMapping(
        path = "/autoAgendamento",
		consumes = "application/json",
		produces = "application/json")
    String newAgendamento(@RequestBody Agendamento newAgendamento) throws ClientProtocolException, IOException{

        Agendamento same = getAgendamentoByCC(newAgendamento.getCc());
        
        if (same == null){
            /*
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



            */

            agendamentoRepository.save(newAgendamento);
        }
        else
        {
            return "ERROR: cc já em uso";
        }
		System.out.println(newAgendamento.toString());
		agendamentoRepository.save(newAgendamento);
        return "save";
	}

    @GetMapping(
        path = "/getAgendamentos",
        produces = "application/json")
    List<Agendamento> getAgendamentos(){
        return (List<Agendamento>) agendamentoRepository.findAll();
    }
/*
    @GetMapping(
        path = "/getAgendamentos",
        produces = "application/json")
    List<Agendamento> getAgendamentosByData(@RequestBody LocalDateTime data){
        return (List<Agendamento>) agendamentoRepository.findAll();
    }
*/
    @GetMapping(
        path = "/getAgendamento")
    Agendamento getAgendamentoByCC(@RequestParam Long cc){
        return agendamentoRepository.findOneByCc(cc);
    }


    /*
    @GetMapping(
    path = "/confirmacao",
    consumes = "application/json")
    Agendamento getAgendamentoByNome(@RequestParam String nome){
        return agendamentoRepository.findOneByNome(nome);
    }
    
    @GetMapping(
    path = "/realizacao",
    consumes = "application/json")
    Agendamento getAgendamentoByNome(@RequestParam String nome){
        return agendamentoRepository.findOneByNome(nome);
    }

    @GetMapping(
    path = "/vacinasRealizadas",
    produces = "application/json")
    Agendamento getAgendamentoByNome(@RequestParam String data){
        return agendamentoRepository.findOneByNome(nome);
    } */
}