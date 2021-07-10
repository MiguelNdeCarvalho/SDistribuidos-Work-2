package pt.uevora.sd.centermodule.Models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vacinado {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long codigo;

	@Column(nullable = false)
    private Long cc;
    private LocalDate data;
    private String tipoVacina;

    protected Vacinado() {}

    public Vacinado(Long cc, LocalDate data, String tipoVacina) {
        this.cc = cc;
        this.data = data;
        this.tipoVacina = tipoVacina;
    }

    @Override
	public String toString() {
		return String.format(
				"Product[cc=%d, codigo='%d', data='%s', tipoVacina='%s']",
				cc, codigo, data, tipoVacina);
	}
    
    public Long getCc() {
		return cc;
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public String getTipoVacina() {
        return tipoVacina;
    }
}