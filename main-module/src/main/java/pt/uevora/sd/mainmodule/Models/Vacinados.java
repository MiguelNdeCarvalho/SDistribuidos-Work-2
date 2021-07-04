package pt.uevora.sd.mainmodule.Models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vacinados {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
    private String tipoVacina;
    private Long idCentro, numeroVacinados;
    private LocalDateTime data;

    protected Vacinados() {}

    public Vacinados(Long idCentro, Long numeroVacinados, LocalDateTime data, String tipoVacina) {
        this.idCentro = idCentro;
        this.numeroVacinados = numeroVacinados;
        this.data = data;
        this.tipoVacina = tipoVacina;
    }

    @Override
	public String toString() {
		return String.format(
				"Product[id=%d, idCentro=%d, numeroVacinado=%d, data='%s', tipoVacina='%s']",
				id, idCentro, numeroVacinados, data.toString(), tipoVacina);
	}
    
    public Long getId() {
		return id;
    }
    
    public Long getIdCentro() {
        return idCentro;
    }

    public Long getNumeroVacinados() {
        return numeroVacinados;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getTipoVacina() {
        return tipoVacina;
    }
}