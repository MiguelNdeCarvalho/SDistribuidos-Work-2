package pt.uevora.sd.centermodule;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agendamento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
    private String nome, email;
    private int idade;
    private LocalDateTime data;
    private boolean confirmacao;

    protected Agendamento() {}

    public Agendamento(String nome, int idade, String email, LocalDateTime data, boolean confirmacao) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.data = data;
        this.confirmacao = confirmacao;
    }

    @Override
	public String toString() {
		return String.format(
				"Product[id=%d, nome='%s', idade=%d, email='%s', data='%s', confirmacao='%s']",
				id, nome, idade, email, data, confirmacao);
	}
    
    public Long getId() {
		return id;
    }
    
    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }
    
    public String getEmail() {
        return email;
    }

    public LocalDateTime getData() {
        return data;
    }

    public boolean getConfirmacao() {
        return confirmacao;
    }
}