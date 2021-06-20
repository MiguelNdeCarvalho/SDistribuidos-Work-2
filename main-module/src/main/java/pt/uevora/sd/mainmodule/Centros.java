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
    private String nome, localidade, URL;
    private Long vacinadosPorDia;

    protected Centros() {}

    public Centros(String nome, String localidade, Long vacinadosPorDia, String URL) {
        this.nome = nome;
        this.localidade = localidade;
        this.vacinadosPorDia = vacinadosPorDia;
        this.URL = URL;
    }

    @Override
	public String toString() {
		return String.format(
				"Product[id=%d, nome='%s', localidade='%s', vacinadoPorDia=%d, URL='%s']",
				id, nome, localidade, vacinadosPorDia, URL);
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

    public String getURL() {
        return URL;
    }
}