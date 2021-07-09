package pt.uevora.sd.centermodule.Components;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:properties.json", encoding = "UTF-8")
@ConfigurationProperties
public class ReadJSONProperties {

    private String nome;

    private String localidade;

    private String maxvacinas;

    private String url;

    private String mailgunkey;

    private String mailgunurl;

    public String getNome() {
        return nome.replace("\"", "").replace(",", "");
    }

    public String getLocalidade() {
        return localidade.replace("\"", "").replace(",", "");
    }

    public Long getMaxvacinas() {
        return Long.valueOf(maxvacinas.replace("\"", "").replace(",", ""));
    }

    public String getUrl() {
        return url.replace("\"", "").replace(",", "");
    }

    public String getMailgunkey() {
        return mailgunkey.replace("\"", "").replace(",", "");
    }

    public String getMailgunurl() {
        return mailgunurl.replace("\"", "").replace(",", "");
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public void setMaxvacinas(String maxvacinas) {
        this.maxvacinas = maxvacinas;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMailgunkey(String mailgunkey) {
        this.mailgunkey = mailgunkey;
    }

    public void setMailgunurl(String mailgunurl) {
        this.mailgunurl = mailgunurl;
    }
}
