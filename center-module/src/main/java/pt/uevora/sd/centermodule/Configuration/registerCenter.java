package pt.uevora.sd.centermodule.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Configuration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Configuration
public class registerCenter {

    private boolean isRegistered(String nome) throws ClientProtocolException, IOException{
        HttpGet request = new HttpGet("http://localhost:8000/api/v1/getCentro/" + URLEncoder.encode(nome, StandardCharsets.UTF_8).replace("+", "%20"));
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                if(result.equals(""))
                    return false;
                return true;
            }
        }
        
        return true;
    }

    private void register(String nome, String localidade, Long vacinadospordia, String url) throws IOException{
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

        client.execute(post);
        client.close();
    }

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

            if (! isRegistered(nome))
                register(nome, localidade, vacinadospordia, url);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
