package pt.uevora.sd.centermodule;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vacinado {

	@Id
	private Long id;

	@Column(nullable = false)
    String codigo, tipoVacina;
    LocalDateTime data;

    protected Vacinado() {}

    public Vacinado(Long id, String codigo, LocalDateTime data, String tipoVacina) {
        this.id = id;
        this.codigo = codigo;
        this.data = data;
        this.tipoVacina = tipoVacina;
    }

    @Override
	public String toString() {
		return String.format(
				"Product[id=%d, codigo='%s', data='%s', tipoVacina='%s']",
				id, codigo, data, tipoVacina);
	}
    
    public Long getId() {
		return id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getTipoVacina() {
        return tipoVacina;
    }
}