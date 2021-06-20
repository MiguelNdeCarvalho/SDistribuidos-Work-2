package pt.uevora.sd.centermodule;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stock {

	@Id
	private LocalDateTime data;

	@Column(nullable = false)
    private long nVacinas;
    private String tipoVacinas;

    protected Stock() {}

    public Stock(LocalDateTime data, long nVacinas, String tipoVacinas) {
        this.data = data;
        this.nVacinas = nVacinas;
        this.tipoVacinas = tipoVacinas;
    }

    @Override
	public String toString() {
		return String.format(
				"Product[data='%s', nVacinas=%d, tipoVacinas='%s']",
                data.toString(), nVacinas, tipoVacinas);
	}
    
    public LocalDateTime getData() {
        return data;
    }

    public long getnVacinas() {
        return nVacinas;
    }

    public String getTipoVacinas() {
        return tipoVacinas;
    }
}