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
import br.com.ifsp.Pessoas.Model.Telefone;

@RestController
@RequestMapping("/pessoas")
/*
 * Endpoints (URIs)
 * 	/pessoas						--> Obtém todas as pessoas.
 * 	/pessoas/5						--> Obtém a pessoa de código 5.
 * 	/pessoas/nome/Marcelo        	--> Obtém as pessoas que contém no nome Marcelo.
 * 	/pessoas/sobrenome/Alves     	--> Obtém as pessoas que contém no sobrenome Alves.
 * 	/pessoa/pessoa               	--> Cadastra uma nova pessoa.
 * 	/pessoa/10      	(PUT)      	--> Altera a pessoa de código 10.     
 * 	/pessoa/9      		(DELETE)  	--> Elimina a pessoa de código 9.
 * 
 */
public class PessoaController {

	private List<Pessoa> pessoas = new ArrayList<>();
		
	public PessoaController() {
		this.pessoas.addAll(List.of(
				new Pessoa(1,"Ana Paula","Silva",28,"enfermeira", List.of(new Telefone("Celular", "11", "98554-5545"))), 
				new Pessoa(2,"Mariana","Silva",32,"enfermeira", List.of(new Telefone("Celular", "11", "98765-4321"))), 
	    		new Pessoa(3,"Pedro","Garcia",42,"engenheiro civil", List.of(new Telefone("Celular","11","98765-4321"))),
	    		new Pessoa(4,"Ana","Ribeiro",35,"advogada", List.of(new Telefone("Residencial","21","1234-5678"))),
	    		new Pessoa(5,"Lucas","Santos",29,"professor de matemática", List.of(new Telefone("Residencial","51","5678-1234"),
	    																			new Telefone("Celular","51","91234-5678"))),
	    		new Pessoa(6,"Carla","Ferreira",25,"dsigner gráfico", List.of(new Telefone("Comercial","91","2468-1357"))),
	    		new Pessoa(7,"João","Oliveira",48,"empresário", List.of(new Telefone("Celular","12","98765-4321"))),
	    		new Pessoa(8,"Bruna","Souza", 31,"médica", List.of(new Telefone("Residencial","22","1234-5678"))),
	    		new Pessoa(9,"Mariano","Castro",37,"contador", List.of(new Telefone("Residencial","23","1234-5678"),
	    																new Telefone("Celular","23","94321-8765"))),
	    		new Pessoa(10,"Isabela","Mendes",23,"estudante de direito", List.of(new Telefone("Comercial","62","1357-2468"))),
	    		new Pessoa(11,"Rodrigo","Costa", 41,"chef de cozinha", List.of(new Telefone("Comercial","92","2468-1357"))),
	    		new Pessoa(12,"Juliana","Lima",33,"jornalista", List.of(new Telefone("Comercial","61","1357-2468"))),
	    		new Pessoa(13,"Fernando","Pereira",30,"arquiteto", List.of(new Telefone("Celular","32","92468-1357"),
	    																   new Telefone("Comercial","32","2468-1357"))),
	    		new Pessoa(14,"Diego","Almeida", 26,"programador de software", List.of(new Telefone("Celular","41","8765-4321"))),
	    		new Pessoa(15,"Camila","Rodrigues",27,"dentista", List.of(new Telefone("Celular","71","4321-8765"))),
	    		new Pessoa(16,"Rafael", "Martins",39,"consultor financeiro", List.of(new Telefone("Celular","42","8765-4321"))),
	    		new Pessoa(17,"João", "Santos",39,"engenheiro civíl", List.of(new Telefone("Residencial","81","1234-5678"),
	    																	  new Telefone("Comercial","81","2468-1357"))),
	     		new Pessoa(18,"José", "Simão",54,"analista de BD", List.of(new Telefone("Residencial","52","5678-1234"),
	     																			new Telefone("Celular","13","98765-4321"))),
	     		new Pessoa(19,"Rodrigo", "Santos",54,"sociólogo", null),
	    		new Pessoa(20,"Pamela","Oliveira",27,"web designer", List.of(new Telefone("Celular","20","94322-8788")))
				));
		
	}

	// Gets...
	@GetMapping
	public Iterable<Pessoa> getPessoas() {
		if (this.pessoas.isEmpty()) {
			throw new NotFoundException("Cadastro de pessoas está vazio!",null);
		}
		return this.pessoas;
	}

	@GetMapping("/{cod}")
	public Optional<Pessoa> getPessoaByCod(@PathVariable int cod) {
		for(Pessoa pessoa: this.pessoas) {
			if(pessoa.getCod() == cod) {
				return Optional.of(pessoa);
			}
		}
		throw new NotFoundException("Não localizada a pessoa pelo código!","Código: " + cod);
	}
	
	@GetMapping("/nome/{nome}")
	public Iterable<Pessoa> getPessoasByNome(@PathVariable String nome) {
		List<Pessoa> listaPessoas = new ArrayList<>();
		for(Pessoa pessoa: this.pessoas) {
			if(pessoa.getNome().contains(nome)) {
				listaPessoas.add(pessoa);
			}
		}
		if (listaPessoas.size() > 0) {
			return listaPessoas;
		}
		throw new NotFoundException("Não localizadas pessoas pelo nome!","Nome: " + nome);
	}
	
	@GetMapping("/sobrenome/{sobrenome}")
	public Iterable<Pessoa> getPessoasBySobrenome(@PathVariable String sobrenome) {
		List<Pessoa> listaPessoas = new ArrayList<>();
		for(Pessoa pessoa: this.pessoas) {
			if(pessoa.getSobrenome().contains(sobrenome)) {
				listaPessoas.add(pessoa);
			}
		}
		if (listaPessoas.size() > 0) {
			return listaPessoas;
		}
		throw new NotFoundException("Não localizadas pessoas pelo sobrenome!","Sobrenome: " + sobrenome);
	}
				
	// Post...
	@PostMapping("/pessoa")
	public Pessoa postPessoa(@RequestBody Pessoa pessoa) {
		for (Pessoa p: this.pessoas) {
			if (p.getCod() == pessoa.getCod()) {
				throw new FoundException("Código da pessoa já existe!","Código: " + pessoa.getCod());
			}
		}
		if (!validarPessoaExistente(pessoa)) {
			this.pessoas.add(pessoa);
			return pessoa;
		}
		throw new FoundException("A pessoa com o nome e sobrenome já está cadastrada!",
									"Sobrenome, nome: " + pessoa.getSobrenome() + ", " + pessoa.getNome());
	}
	
	private boolean validarPessoaExistente(Pessoa pessoa) {
		for (Pessoa p: this.pessoas) {
			if (p.getNome().equals(pessoa.getNome()) &&
					p.getSobrenome().equals(pessoa.getSobrenome())) {
					// Pessoa com nome e sobrenome existentes!
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
			throw new NotFoundException("Código da pessoa inexistente; dados não alterados!",
											"Código: " + cod);
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
		throw new NotFoundException("Código da pessoa inexistente, dados não removidos!",
										"Código: " + cod);
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
