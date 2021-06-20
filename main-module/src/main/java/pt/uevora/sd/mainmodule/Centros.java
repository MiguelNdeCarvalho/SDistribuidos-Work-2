package pt.uevora.sd.mainmodule;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Centros {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
    private String nome, localidade, url;
    private Long vacinadosPorDia;

    protected Centros() {}

    public Centros(String nome, String localidade, Long vacinadosPorDia, String url) {
        this.nome = nome;
        this.localidade = localidade;
        this.vacinadosPorDia = vacinadosPorDia;
        this.url = url;
    }

    @Override
	public String toString() {
		return String.format(
				"Product[id=%d, nome='%s', localidade='%s', vacinadosPorDia=%d, url='%s']",
				id, nome, localidade, vacinadosPorDia, url);
	}
    
    public Long getId() {
		return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getLocalidade() {
        return localidade;
    }

    public Long getVacinadosPorDia() {
        return vacinadosPorDia;
    }

    public String getUrl() {
        return url;
    }
}