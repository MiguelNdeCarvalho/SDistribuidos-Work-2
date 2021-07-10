package pt.uevora.sd.centermodule.Controllers;

import java.time.LocalDate;
import java.util.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;

import pt.uevora.sd.centermodule.Components.ReadJSONProperties;
import pt.uevora.sd.centermodule.Models.*;
import pt.uevora.sd.centermodule.Repositories.*;

@Transactional
@RestController
@RequestMapping(path = "/api/v1")
public class CenterController {

    @Autowired
    private ReadJSONProperties jsonProperties;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private VacinadoRepository vacinadoRepository ;

    @Autowired
    private StockRepository stockRepository;

    public void sendMail(String recipientMail, String subject, String text){
        String mail = jsonProperties.getMailgunmail();
        String url = jsonProperties.getMailgunurl();
        String api = jsonProperties.getMailgunkey();

        try {
            Unirest.post(url + "/messages")
            .basicAuth("api", api)
            .queryString("from", "No-Reply <" + mail + ">")
            .queryString("to", recipientMail)
            .queryString("subject", subject)
            .queryString("text", text).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

	}

    @PostMapping(
        path = "/autoAgendamento",
		consumes = "application/json")
    String newAgendamento(@RequestBody Agendamento newAgendamento) throws ClientProtocolException, IOException, ParseException{

        Agendamento same = getAgendamentoByCC(newAgendamento.getCc());
        

        if (same == null){
            
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost( "http://localhost:8000/api/v1/" + "getVacinasPorDia");

            JSONObject send = new JSONObject();
            send.put("nome", jsonProperties.getNome());


            StringWriter out = new StringWriter();
            send.writeJSONString(out);
            
            String sendObj = out.toString();

            StringEntity entity = new StringEntity(sendObj, "UTF-8");

            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(post)) {
    
                if (entity != null) {
                    // return it as a String
                    String capacidade = EntityUtils.toString(response.getEntity());
                    System.out.println(capacidade);
                    if (Integer.parseInt(capacidade) <= agendamentoRepository.findAllByData(LocalDate.parse(newAgendamento.getData().toString())).size())
                    {
                        return "O dia está cheio";
                    }
                }
            }

            agendamentoRepository.save(newAgendamento);
        }
        else
        {
            return "ERROR: cc já em uso";
        }
        return "saved";
	}

    @GetMapping(
        path = "/getAgendamentos",
        produces = "application/json")
    List<Agendamento> getAgendamentos(){
        return (List<Agendamento>) agendamentoRepository.findAll();
    }

    @PutMapping(
        path = "/updateAgendamento",
        produces = "application/json")
    String setDataAgendamento(@RequestParam Long cc, String data){
        
        Agendamento found = getAgendamentoByCC(cc);
        if (found == null)
        {
            return "Não existe";
        }
        else
        {
            found.setConfirmacao(null);
            found.setData(LocalDate.parse(data));
            agendamentoRepository.save(found);

        }
        return "Data alterada, espere pela confirmação";
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

    @GetMapping(
        path = "/getAgendamentoStatus")
    String getAgendamentoStatusByCC(@RequestParam Long cc){
        
        Agendamento agendamento = agendamentoRepository.findOneByCc(cc);
        if (agendamento == null){
            return "Not Found";
        }
        return agendamento.getConfirmacao().toString();
    }

    @PostMapping(
        path = "/setStock",
		consumes = "application/json")
    String setStock(@RequestBody Stock newStock){
        stockRepository.save(newStock);

        List<Agendamento> agendamentos = agendamentoRepository.findAllByOrderByIdadeDesc();
        int count = 0;
        for (Agendamento agend : agendamentos) {
            Agendamento agendamento = agendamentoRepository.findOneByCc(agend.getCc());
            String name = agend.getNome();
            String email = agend.getEmail();
            String subject = "Agendamento da vacinação";
            String date = agend.getData().toString();
            String bodyConfirmed = "Caro(a) %s,\nExmo. Senhor,\n\nServe o presente para informar que o agendamento para o dia %s se encontra confirmado.\n\nCom os melhores cumprimentos";
            String bodyNotConfirmed = "Caro(a) %s,\nExmo. Senhor,\n\nServe o presente para informar que o agendamento para o dia %s necessita ser reagendado. O reagendamento deverá ser feito através do portal.\n\nCom os melhores cumprimentos";

            if (count < newStock.getnVacinas())
            {
                sendMail(email, subject, String.format(bodyConfirmed, name, date));
                agendamento.setConfirmacao(true);
            }
            else
            {
                sendMail(email, subject, String.format(bodyNotConfirmed, name, date));
                agendamento.setConfirmacao(false);
            }
            agendamentoRepository.save(agendamento);
            count++;
        }

        return "saved";
    }

    @GetMapping(
        path = "/getStock/{data}")
    Stock getStock(@PathVariable String data){
        
        return stockRepository.findOneByData(LocalDate.parse(data));
    }

    @GetMapping(
        path = "/getStocks",
        produces = "application/json")
    List<Stock> getStocks(){
        return (List<Stock>) stockRepository.findAll();
    }

    @PostMapping(
        path = "/setVacinado/{cc}",
        produces = "application/json")
    String getStocks(@PathVariable Long cc, @RequestParam String tipoVacina){

        Agendamento agendamento = agendamentoRepository.findOneByCc(cc);
        if (agendamento == null){
            return "não existe";
        }
        agendamentoRepository.delete(agendamento);
        vacinadoRepository.save(new Vacinado(cc,agendamento.getData(),tipoVacina));


        return "done";
    }

    @GetMapping(
        path = "/sendVacinados",
        produces = "application/json")
    String sendVacinados(@RequestParam String data){
        
/*
        List<Vacinado> lista = vacinadoRepository.findByData(LocalDate.parse(data));
        lista.

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8000/api/v1/" + "receiveVacinados");

        JSONObject send = new JSONObject();
        Long num = (long) contagem[centros.get(i).getId().intValue() - 1];
        send.put("data", data);
        send.put("nVacinas", num);
        send.put("tipoVacinas", tipo);

        StringWriter out = new StringWriter();
        send.writeJSONString(out);
        
        String sendObj = out.toString();

        StringEntity entity = new StringEntity(sendObj, "UTF-8");

        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        client.execute(post);
        client.close();
        */

        return "ok";
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