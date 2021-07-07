package pt.uevora.sd.centermodule.Controllers;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.net.*;
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
    String newAgendamento(@RequestBody Agendamento newAgendamento){

        Agendamento same = getAgendamentoByCC(newAgendamento.getCc());
        if (same == null){

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/api/v1/getVacinasPorDia"))
                .addParameter("nome", "ler ficheiro")
                .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.body());

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

    @GetMapping(
        path = "/getAgendamentos",
        produces = "application/json")
    List<Agendamento> getAgendamentosByData(@RequestBody LocalDateTime data){
        return (List<Agendamento>) agendamentoRepository.findAll();
    }

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