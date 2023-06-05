package br.com.ifsp.Pessoas.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.ifsp.Pessoas.ErrorResponse.ErrorResponse;
import br.com.ifsp.Pessoas.ErrorResponse.FoundException;
import br.com.ifsp.Pessoas.ErrorResponse.NotFoundException;
import br.com.ifsp.Pessoas.Model.Pessoa;

@RestController
@RequestMapping("/pessoas")
/*
 * Endpoints (URIs)
 * /pessoas						--> Obtém todas as pessoas.
 * /pessoas/5					--> Obtém a pessoa de código 5.
 * /pessoas/nome/Marcelo        --> Obtém as pessoas que contém no nome Marcelo.
 * /pessoas/sobrenome/Alves     --> Obtém as pessoas que contém no sobrenome Alves.
 * /pessoa/pessoa               --> Cadastra uma nova pessoa.
 * /pessoa/10      (PUT)        --> Altera a pessoa de código 10.     
 * /pessoa/9      (DELETE)      --> Elimina a pessoa de código 9.
 * 
 */
public class PessoaController {

	private List<Pessoa> pessoas = new ArrayList<>();
		
	public PessoaController() {
		this.pessoas.addAll(List.of(
				new Pessoa(1,"Ana Paula","Silva",28,"enfermeira"),
	    		new Pessoa(2,"Mariana","Silva",32,"enfermeira"),
	    		new Pessoa(3,"Pedro","Garcia",42,"engenheiro civil"),
	    		new Pessoa(4,"Ana","Ribeiro",35,"advogada"),
	    		new Pessoa(5,"Lucas","Santos",29,"professor de matemática"),
	    		new Pessoa(6,"Carla","Ferreira",25,"dsigner gráfico"),
	    		new Pessoa(7,"João","Oliveira",48,"empresário"),
	    		new Pessoa(8,"Bruna","Souza", 31,"médica"),
	    		new Pessoa(9,"Diego","Almeida", 26,"programador de software"),
	    		new Pessoa(10,"Mariano","Castro",37,"contador"),
	    		new Pessoa(11,"Isabela","Mendes",23,"estudante de direito"),
	    		new Pessoa(12,"Rodrigo","Costa", 41,"chef de cozinha"),
	    		new Pessoa(13,"Juliana","Lima",33,"jornalista"),
	    		new Pessoa(14,"Fernando","Pereira",30,"arquiteto"),
	    		new Pessoa(15,"Camila","Rodrigues",27,"dentista"),
	    		new Pessoa(16,"Rafael", "Martins",39,"consultor financeiro"),
	    		new Pessoa(17,"João", "Santos",39,"engenheiro civíl"),
	     		new Pessoa(18,"José", "Simão",54,"Analista de BD")
	    		));
	}

	// Gets...
	@GetMapping
	public Iterable<Pessoa> getPessoas() {
		if (!this.pessoas.isEmpty()) {
			return this.pessoas;
		}
		throw new NotFoundException("O cadastro de pessoas está vazio!",null);
	}

	@GetMapping("/{cod}")
	public Optional<Pessoa> getPessoaByCod(@PathVariable int cod) {
		for(Pessoa pessoa: this.pessoas) {
			if(pessoa.getCod() == cod) {
				return Optional.of(pessoa);
			}
		}
		throw new NotFoundException("Não localizado a pessoas pelo código!","Cód: " + cod);
	}
	
	@GetMapping("/nome/{nome}")
	public Iterable<Pessoa> getPessoaByNome(@PathVariable String nome) {
		List<Pessoa> containPessoas = new ArrayList<>();
		for(Pessoa pessoa: this.pessoas) {
			if(pessoa.getNome().contains(nome)) {
				containPessoas.add(pessoa);
			}
		}
		if (containPessoas.size() > 0) {
			return containPessoas;
		}
		throw new NotFoundException("Não localizado pessoas pelo nome!","Nome: " + nome);
	}
	
	@GetMapping("/sobrenome/{sobrenome}")
	public Iterable<Pessoa> getPessoaBySobrenome(@PathVariable String sobrenome) {
		List<Pessoa> containPessoas = new ArrayList<>();
		for(Pessoa pessoa: this.pessoas) {
			if(pessoa.getSobrenome().contains(sobrenome)) {
				containPessoas.add(pessoa);
			}
		}
		if (containPessoas.size() > 0) {
			return containPessoas;
		}
		throw new NotFoundException("Não localizado pessoas pelo sobrenome!","Sobrenome: " + sobrenome);
	}
				
	// Post...
	@PostMapping("/pessoa")
	public Pessoa postPessoa(@RequestBody Pessoa pessoa) {
		for (Pessoa p: this.pessoas) {
			if (p.getCod() == pessoa.getCod()) {
				throw new FoundException("Código da pessoa já existe!","Cód: " + pessoa.getCod());
			}
		}
		if (!validarPessoaExistente(pessoa)) {
			this.pessoas.add(pessoa);
			return pessoa;
		}
		throw new FoundException("O nome e sobrenome já estão cadastrados!",
				"Sobrenome, nome: " + pessoa.getSobrenome() + ", " + pessoa.getNome());
	}
	
	private boolean validarPessoaExistente(Pessoa pessoa) {
		for (Pessoa p: this.pessoas) {
			if (p.getNome().equals(pessoa.getNome()) &&
					p.getSobrenome().equals(pessoa.getSobrenome())) {
					// Nova pessoa com nome e sobrenome existentes!
					return true;
			}
		}
		return false;
	}
	
	// Put...
	@PutMapping("/{cod}")
	public ResponseEntity<Pessoa> putPessoa(@PathVariable int cod, @RequestBody Pessoa pessoa) {
		int pessoaIndice = -1;
		for(Pessoa p: this.pessoas) {
			if(p.getCod() == cod) {
				pessoaIndice = this.pessoas.indexOf(p);
				pessoa.setCod(cod);
				this.pessoas.set(pessoaIndice, pessoa);
			}
		}
		if (pessoaIndice == -1) {
			throw new NotFoundException("Dados da pessoa não alterados! Código inexistente!","Cód: " + cod);
		}
		return new ResponseEntity<>(pessoa,
									HttpStatus.ACCEPTED)
									;
	}
	
	// Delete...
	@DeleteMapping("/{cod}")
	public ResponseEntity<Pessoa> deletePessoa(@PathVariable int cod) {
		for (Pessoa pessoa : this.pessoas) {
			if (pessoa.getCod() == cod) {
				this.pessoas.removeIf(p -> p.getCod() == cod);
				return new ResponseEntity<>(pessoa,
											HttpStatus.OK
											);
			}
		}
		throw new NotFoundException("Dados da pessoa não removido. Código inexistente!","Cód: " + cod);
	}
		
	@ExceptionHandler(NotFoundException.class)
	private ResponseEntity<ErrorResponse> handlerNotFoundException(NotFoundException nfe) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
														HttpStatus.NOT_FOUND.toString(),
														nfe.getMessage(),
														nfe.getErroInfo(),
														this.getClass().toString()
														);
		
		return new ResponseEntity<>(errorResponse,
									HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FoundException.class)
	private ResponseEntity<ErrorResponse> handlerFoundException(FoundException fe) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FOUND.value(),
														HttpStatus.FOUND.toString(),
														fe.getMessage(),
														fe.getErroInfo(),
														this.getClass().toString()
														);
		
		return new ResponseEntity<>(errorResponse,
									HttpStatus.FOUND);
	}
	
}
