package pt.uevora.sd.centermodule.Models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Agendamento {

	@Id
	private Long cc;

    private Boolean confirmacao;

	@Column(nullable = false)
    private String nome, email;
    private int idade;
    private LocalDateTime data;

    protected Agendamento() {}

    public Agendamento(Long cc,String nome, int idade, String email, LocalDateTime data, Boolean confirmacao) {
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

    public Boolean getConfirmacao() {
        return confirmacao;
    }

    //--------------------------
    public void setCc(Long cc) {
        this.cc = cc;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setConfirmacao(Boolean confirmacao) {
        this.confirmacao = confirmacao;
    }
}