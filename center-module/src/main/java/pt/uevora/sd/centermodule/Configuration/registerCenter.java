package pt.uevora.sd.centermodule.Configuration;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pt.uevora.sd.centermodule.Components.ReadJSONProperties;

import org.json.simple.JSONObject;

@Configuration
public class registerCenter {

    @Autowired
    ReadJSONProperties jsonProperties;

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
    public void init() throws ClientProtocolException, IOException{
        
        String nome = jsonProperties.getNome();
        String localidade = jsonProperties.getLocalidade();
        Long vacinadospordia = jsonProperties.getMaxvacinas();
        String url = jsonProperties.getUrl();

        if (! isRegistered(nome))
                register(nome, localidade, vacinadospordia, url);

    }
}
