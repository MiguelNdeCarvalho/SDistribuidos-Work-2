package pt.uevora.sd.centermodule.Configuration;

import java.io.FileReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Configuration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Configuration
public class registerCenter {

    @PostConstruct
    public void init(){

        String nome, localidade, url;
        Long vacinadospordia;

        try{
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader("/home/mike/Documents/University/SDistribuidos-Work-2/center-module/src/main/resources/properties.json", StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) obj;
            
            
            nome = (String) jsonObject.get("nome");
            localidade = (String) jsonObject.get("localidade");
            vacinadospordia = (Long) jsonObject.get("vacinadospordia");
            url = (String) jsonObject.get("url");

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost("http://localhost:8000/api/v1/newCentro");
            
            JSONObject send = new JSONObject();

            send.put("nome", nome);
            send.put("localidade", localidade);
            send.put("vacinadosPorDia", vacinadospordia);
            send.put("url", url);

            StringWriter out = new StringWriter();
            send.writeJSONString(out);
            
            String sendObj = out.toString();

            StringEntity entity = new StringEntity(sendObj, "UTF-8");

            post.setEntity(entity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(post);
            System.out.println(response);
            client.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
