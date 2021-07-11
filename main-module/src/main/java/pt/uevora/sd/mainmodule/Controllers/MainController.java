package pt.uevora.sd.mainmodule.Controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.expr.NewArray;
import pt.uevora.sd.mainmodule.Models.*;
import pt.uevora.sd.mainmodule.Repositories.*;

@RestController
@RequestMapping(path = "/api/v1")
public class MainController {

    @Autowired
    private CentrosRepository centrosRepository;

    @Autowired
    private VacinadosRepository vacinadosRepository;
    
    @PostMapping(
		path = "/newCentro",
		consumes = "application/json",
		produces = "application/json")
	Centros newCentro(@RequestBody Centros newCentro){
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

    @PostMapping(
        path = "/getVacinasPorDia",
        consumes = "application/json")
    Long getVacinasPorDiaByNome(@RequestBody String nome) throws ParseException{
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(nome);
        return centrosRepository.findOneByNome(json.get("nome").toString()).getVacinadosPorDia();
    }

    @PostMapping(
        path = "/setVacinados",
        consumes = "application/json")
    String setVacinados(@RequestBody String nome) throws ParseException{
        JSONParser parser = new JSONParser();
        JSONArray json = (JSONArray) parser.parse(nome);
        
        JSONObject centerNome = (JSONObject) json.get(0);
        JSONObject data = (JSONObject) json.get(1);

        for (int i = 2; i < json.size(); i++) {
            
            JSONObject tipoVacina = (JSONObject) json.get(i);
            JSONObject count = (JSONObject) json.get(i);

            Long idCentro = centrosRepository.findOneByNome(centerNome.get("centerName").toString()).getId();
            Long numeroVacinados = Long.valueOf(count.get("count").toString());
            LocalDate dia = LocalDate.parse(data.get("data").toString());
            String vacina = tipoVacina.get("vacina").toString();


            vacinadosRepository.save(new Vacinados( idCentro,  numeroVacinados, dia,  vacina));
        }

        return "ok";
    }

    @GetMapping(
        path = "/listVacinados",
        produces = "application/json")
    JSONArray listVacinados() {
        
        List<Vacinados> vacinas = vacinadosRepository.findAll();
        List<LocalDate> datas = new ArrayList<>();

        for (Vacinados vaci : vacinas) {
            LocalDate date = vaci.getData();
            if ( !datas.contains(date)){
                datas.add(date);
            }
        }

        JSONArray global = new JSONArray();

        for (LocalDate n : datas) {
            List<Vacinados> vacinados = vacinadosRepository.findByData(n);
            Long count = (long) 0;
            for (Vacinados vaci : vacinados) {
                count += vaci.getNumeroVacinados();
            }
    
            JSONObject m = new JSONObject();
            m.put("vacinados", count);
            m.put("data", n.toString());
            global.add(m);
        }

        return global;
    }

    @GetMapping(
        path = "/nTotalVacinas",
        produces = "application/json")
    JSONArray getTotalVacinas() throws ClientProtocolException, IOException, ParseException{

        List<Centros> centros = (List<Centros>) centrosRepository.findAll();
        JSONArray global = new JSONArray();
        
        for (Centros centro : centros) {    
            //pedido getAgendamentosByData
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(centro.getUrl() + "getStocks");
    
            try (CloseableHttpResponse response = httpClient.execute(request)) {
    
                // Get HttpResponse Status
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);

                    JSONArray jsonArr = (JSONArray) new JSONParser().parse( result );

                    for (int i = 0; i < jsonArr.size(); i++) {
                        JSONObject n =  (JSONObject) jsonArr.get(i);
                        n.put("centroNome", centro.getNome());
                        global.add(n);
                    }
                    return jsonArr;
                }
            }
        }
        JSONArray jsonArr = (JSONArray) new JSONParser().parse("");
        return jsonArr;
    }

    @GetMapping(
        path = "/requestVacinados",
        produces = "application/json")
    void requestVacinados(@RequestParam String nome) throws ClientProtocolException, IOException, ParseException{
    
        List<Centros> centros = (List<Centros>) centrosRepository.findAll();
        JSONArray global = new JSONArray();
        
        for (Centros centro : centros) {    
            //pedido getAgendamentosByData
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(centro.getUrl() + "getCountVacinasAdmnistradas");
    
            try (CloseableHttpResponse response = httpClient.execute(request)) {
    
                // Get HttpResponse Status
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);

                    JSONArray jsonArr = (JSONArray) new JSONParser().parse( result );

                    for (int i = 0; i < jsonArr.size(); i++) {
                        JSONObject n =  (JSONObject) jsonArr.get(i);
                        n.put("centroNome", centro.getNome());
                        global.add(n);

                    }
                }
            }
        }
    }


    @GetMapping(
        path = "/fornecerVacinas",
        produces = "application/json")
    void getFornecerVacinas(@RequestParam Long n_vacinas, String data) throws ClientProtocolException, IOException, ParseException{
        
        List<Centros> centros = (List<Centros>) centrosRepository.findAll();
        JSONArray global = new JSONArray();
        
        for (Centros centro : centros) {    
            //pedido getAgendamentosByData
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(centro.getUrl() + "getAgendamentos");
    
            try (CloseableHttpResponse response = httpClient.execute(request)) {
    
                // Get HttpResponse Status
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);

                    JSONArray jsonArr = (JSONArray) new JSONParser().parse( result );

                    for (int i = 0; i < jsonArr.size(); i++) {
                        JSONObject n =  (JSONObject) jsonArr.get(i);
                        n.put("centroId", centro.getId());
                        global.add(n);

                    }
                }
            }
        }

        Collections.sort( global,new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "idade";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                Long valA = (long) 0;
                Long valB = (long) 0;

                try {
                    valA = (Long) a.get(KEY_NAME);
                    valB = (Long) b.get(KEY_NAME);
                } 
                catch (JSONException e) {
                    //do something
                }
                return -valA.compareTo(valB);

            }
        });
                
        int contagem[] = new int[centros.size()];
        Arrays.fill(contagem, 0);

        for(int i = 0; i < n_vacinas; i++)
        {
            JSONObject n =  (JSONObject) global.get(i);
            Long id = (Long) n.get("centroId");
            contagem[(int)(id-1)]++;
        }

        
        for(int i = 0; i < contagem.length; i++)
        {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(centros.get(i).getUrl() + "setStock");

            JSONObject send = new JSONObject();
            Long num = (long) contagem[centros.get(i).getId().intValue() - 1];
            send.put("data", data);
            send.put("nVacinas", num);
            send.put("tipoVacinas", "braps");

            StringWriter out = new StringWriter();
            send.writeJSONString(out);
            
            String sendObj = out.toString();

            StringEntity entity = new StringEntity(sendObj, "UTF-8");

            post.setEntity(entity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
    
            client.execute(post);
            client.close();
        }
    }
}