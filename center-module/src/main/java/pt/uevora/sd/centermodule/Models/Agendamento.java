package pt.uevora.sd.centermodule.Models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agendamento {

	@Id
	private Long cc;

	@Column(nullable = false)
    private String nome, email;
    private int idade;
    private LocalDateTime data;
    private boolean confirmacao;

    protected Agendamento() {}

    public Agendamento(Long cc,String nome, int idade, String email, LocalDateTime data, boolean confirmacao) {
        this.cc = cc;
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.data = data;
        this.confirmacao = confirmacao;
    }

    @Override
	public String toString() {
		return String.format(
				"Product[cc=%d, nome='%s', idade=%d, email='%s', data='%s', confirmacao='%s']",
				cc, nome, idade, email, data, confirmacao);
	}
    
    public Long getCc() {
		return cc;
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