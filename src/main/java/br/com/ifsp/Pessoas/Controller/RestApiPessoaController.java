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

import br.com.ifsp.Pessoas.Model.Pessoa;

@RestController
@RequestMapping("/pessoas")
public class RestApiPessoaController {

	List<Pessoa> pessoas = new ArrayList<>();
		
	public RestApiPessoaController() {
		pessoas.addAll(List.of(
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
	    		new Pessoa(17,"João", "Santos",39,"engenheiro civíl")
				));
	}

	// Gets...
	@GetMapping
	Iterable<Pessoa> getPessoas() {
		return pessoas;
	}

	@GetMapping("/{cod}")
	Optional<Pessoa> getPessoaByCod(@PathVariable int cod) {
		for(Pessoa p: pessoas) {
			if(p.getCod() == cod) {
				return Optional.of(p);
			}
		}
		return Optional.empty();
	}
	
	@GetMapping("/nome/{nome}")
	Optional<Pessoa> getPessoaByNome(@PathVariable String nome) {
		for(Pessoa p: pessoas) {
			if(p.getNome().equals(nome)) {
				return Optional.of(p);
			}
		}
		return Optional.empty();
	}
	
	@GetMapping("/sobrenome/{sobrenome}")
	Optional<Pessoa> getPessoaBySobrenome(@PathVariable String sobrenome) {
		for(Pessoa p: pessoas) {
			if(p.getSobrenome().equals(sobrenome)) {
				return Optional.of(p);
			}
		}
		return Optional.empty();
	}
				
	// Post...
	@PostMapping("/pessoa")
	Pessoa postPessoa(@RequestBody Pessoa pessoa) {
		pessoas.add(pessoa);
		return pessoa;
	}
	
	// Put...
	@PutMapping("/{cod}")
	ResponseEntity<Pessoa> putPessoa(@PathVariable int cod, @RequestBody Pessoa pessoa) {
		int pessoaIndice = -1;
		for(Pessoa p: pessoas) {
			if(p.getCod() == cod) {
				pessoaIndice = pessoas.indexOf(p);
				pessoas.set(pessoaIndice, pessoa);
			}
		}	
		return (pessoaIndice >= 0) ?
				new ResponseEntity<>(pessoa,
						HttpStatus.ACCEPTED)
				: new ResponseEntity<>(postPessoa(pessoa),
						HttpStatus.CREATED)
				;
	}
	
	// Delete...
	@DeleteMapping("/{cod}")
	ResponseEntity<String> deletePessoa(@PathVariable int cod) {
		return (pessoas.removeIf(p -> p.getCod() == cod)) ?
				new ResponseEntity<String>("Removido",
						HttpStatus.OK)
				: new ResponseEntity<String>("Não localizado!",
						HttpStatus.NOT_FOUND)
				;
	}
	
}
