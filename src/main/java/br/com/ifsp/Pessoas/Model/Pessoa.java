package br.com.ifsp.Pessoas.Model;

import java.util.UUID;

public class Pessoa {
	private int cod;
	private String nome;
	private String sobrenome;
	private int idade;
	private String profissao;
	private final String uuid;
	
	// Constructor
	public Pessoa(int cod, String nome, String sobrenome, int idade, String profissao) {
		super();
		this.cod = cod;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.idade = idade;
		this.profissao = profissao;
		this.uuid = UUID.randomUUID().toString();
	}

	// Getters and setters
	public int getCod() {
		return this.cod;
	}
		
	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getUuid() {
		return this.uuid;
	}

}
