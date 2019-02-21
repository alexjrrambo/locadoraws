package me.locadoraws.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.locadoraws.error.BadRequestErrorException;
import me.locadoraws.error.ResourceNotFoundErrorException;
import me.locadoraws.model.Filme;
import me.locadoraws.model.Locacao;
import me.locadoraws.model.Usuario;
import me.locadoraws.repository.FilmeRepository;
import me.locadoraws.repository.LocacaoRepository;
import me.locadoraws.repository.UsuarioRepository;
import me.locadoraws.utils.Utils;

/**
 * @author Alex Junior Rambo 
 */

@RestController
@RequestMapping("/filmes")
public class FilmeController {

	//Essa annotation faz com que possamos usar usuarioRespository em qualquer lugar do controller sem precisar instanciar
	@Autowired
	FilmeRepository filmeRepository;

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	LocacaoRepository locacaoRepository;
	
	@GetMapping
	public ResponseEntity<?> listarFilmes(@RequestParam(value="titulo", required=false) String titulo, @RequestHeader (value="acessToken") String acessToken){
		validaTokenUsuario(acessToken);
				
		if (!Utils.isNullOrEmpty(titulo)) {
			return new ResponseEntity<>(filmeRepository.findByTituloContaining(titulo), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(filmeRepository.buscarTodosFilmesDisponiveis(), HttpStatus.OK);
		}
	}
	
	//Abrindo transação para efetuar update no banco de dados
	@Transactional	
	@PostMapping("/locacao")
	public ResponseEntity<?> locarFilme(@RequestBody Map<String, String> body, @RequestHeader (value="acessToken") String acessToken){
		//Realiza validações do body e header
		Usuario usuario = validaTokenUsuario(acessToken);
		int idFilme = validaRetornaIdFilme(body.get("idFilme"));
		
		//Busca dados do filme pelo id
		Filme filme = filmeRepository.findById(idFilme);
		
		if (filme == null) {
			throw new ResourceNotFoundErrorException("Não foi encontrado filme para o id informado");
		}
		
		//Caso filme possuir quantidade disponível realiza locação
		if (filme.getQuantidadeDisponivel() > 0) {
			locacaoRepository.save(new Locacao(idFilme, usuario.getId(), "L"));
			filmeRepository.locarFilmePorId(idFilme);
		} else {
			throw new BadRequestErrorException("Filme não está disponível");        
		}
				
		return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
	}
	
	//Abrindo transação para efetuar update no banco de dados
	@Transactional	
	@PostMapping("/devolucao")
	public ResponseEntity<?> devolverFilme(@RequestBody Map<String, String> body, @RequestHeader (value="acessToken") String acessToken){
		//Realiza validações do body e header
		Usuario usuario = validaTokenUsuario(acessToken);
		int idFilme = validaRetornaIdFilme(body.get("idFilme"));
		
		//Valida se filme exsite e se realmente está com o usuário
		int filmeLocadoParaUsuario = locacaoRepository.existeFilmeLocadoUsuario(idFilme, usuario.getId());
		
		if (filmeLocadoParaUsuario == 0) {
			throw new ResourceNotFoundErrorException("Não foi encontrado o filme informado para o id informado ou o usuário não está com este filme locado");
		}
		
		//Realiza devolução do filme
		locacaoRepository.devolverFilmePorId(idFilme);
		filmeRepository.devolverFilmePorId(idFilme);
		
		return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
	}
	
	//Verifica se o token de acesso do usuário é válido e retorna o usuário
	private Usuario validaTokenUsuario(String token){
		//Verifica se token foi informado
		if (Utils.isNullOrEmpty(token)) {
			throw new ResourceNotFoundErrorException("Token de acesso invávlido");
		}
		
		//Tenta encontrar usuário com o token informado
		Usuario usuario = usuarioRepository.buscarPorAcessToken(token);
		if (usuario == null) {
        	throw new ResourceNotFoundErrorException("Token de acesso invávlido");
        }
		
		return usuario;
	}
	
	//Verifica se o Id do filme é um númerico e retorna o mesmo como um inteiro
	private int validaRetornaIdFilme(String idFilme) {		
		validaInteiro("idFilme",idFilme);
		
		return Integer.parseInt(idFilme);	
	}
	
	private void validaInteiro(String campo, String valor) {
		validaNullOuVazio(campo, valor);
		
		if (!Utils.isInteger(valor)) {
			throw new BadRequestErrorException("Campo " + campo + " deve ser um número inteiro");   
		}
	}
	
	private void validaNullOuVazio(String campo, String valor) {
		if (Utils.isNullOrEmpty(valor)) {
			throw new BadRequestErrorException("Campo " + campo + " deve ser informado");   
		}
	}
}
