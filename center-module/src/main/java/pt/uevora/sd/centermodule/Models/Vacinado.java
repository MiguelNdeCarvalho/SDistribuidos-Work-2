package pt.uevora.sd.centermodule.Models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vacinado {

	@Id
	private Long cc;

	@Column(nullable = false)
    String codigo, tipoVacina;
    LocalDate data;

    protected Vacinado() {}

    public Vacinado(Long cc, String codigo, LocalDate data, String tipoVacina) {
        this.cc = cc;
        this.codigo = codigo;
        this.data = data;
        this.tipoVacina = tipoVacina;
    }

    @Override
	public String toString() {
		return String.format(
				"Product[cc=%d, codigo='%s', data='%s', tipoVacina='%s']",
				cc, codigo, data, tipoVacina);
	}
    
    public Long getCc() {
		return cc;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public String getTipoVacina() {
        return tipoVacina;
    }
}