package me.locadoraws.controller;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.locadoraws.error.BadRequestErrorException;
import me.locadoraws.error.ResourceNotFoundErrorException;
import me.locadoraws.model.Usuario;
import me.locadoraws.repository.UsuarioRepository;
import me.locadoraws.utils.Utils;

/**
 * @author Alex Junior Rambo 
 */

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	//Essa annotation faz com que possamos usar usuarioRespository em qualquer lugar do controller sem precisar instanciar a todo momento
	@Autowired
    UsuarioRepository usuarioRepository;
	
	@PostMapping
    public ResponseEntity<?> crirarUsuario(@RequestBody Usuario usuario) {
		//Verifica se todos os dados foram informados
		validaDadosUsuario(usuario);
		
        return new ResponseEntity<>(usuarioRepository.save(usuario), HttpStatus.CREATED);
    }
	
	@PostMapping("/logon")
    public ResponseEntity<?> logonUsuario(@RequestBody Map<String, String> body){
        //Busca dados do corpo da requisição
		String email = body.get("email");
        String senha = body.get("senha");
        
		//Validação para obrigar e-mail e senha
		validaNullOuVazio("email", email);
		validaNullOuVazio("senha", senha);
		
		//Realiza busca de usuário por e-mail e senha informados
        Usuario usuario = usuarioRepository.buscarPorEmail(email, senha);       
        
        //Caso o usuário tenha informado as credenciais corretas
        if (usuario != null) {
        	usuario.setAcessToken(geraAcessTokeUsuario());
        } else {
        	throw new ResourceNotFoundErrorException("Não foi encontrado usuário para o login e senha informados");        	
        }
        
        return new ResponseEntity<>(usuarioRepository.save(usuario), HttpStatus.OK);
    }
		
	@DeleteMapping("/logoff")
    public ResponseEntity<?> logoffUsuario(@RequestHeader (value="acessToken") String acessToken){		
		//Validação do token de acesso        
		Usuario usuario = validaTokenUsuario(acessToken); 
                
        //Caso o usuário tenha informado as credenciais corretas
        if (usuario != null) {
        	usuario.setAcessToken("");
        	usuarioRepository.save(usuario);
        } else {
        	throw new ResourceNotFoundErrorException("Token de acesso invávlido");
        }
        
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

	//Gera token para usuário que está logando
	private String geraAcessTokeUsuario() {		
		//Criando csprng para utilizar como token
		SecureRandom csprng = new SecureRandom();		
	    byte randomBytes[] = new byte[32];
	    csprng.nextBytes(randomBytes);
	    
	    //Transformando em uma string
	    Encoder encoder = Base64.getUrlEncoder();
	    String token = encoder.encodeToString(randomBytes);
	    return token;
	}
	
	//Busca token informado no header da requisição, verifica se é válido e retorna o usuário
	private Usuario validaTokenUsuario(String token){
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
	
	//Verifica se campos obrigatórios foram preenchidos, caso não foram, devolve uma excpetion com um uma mensagem amigável
	private void validaDadosUsuario(Usuario usuario) {
		validaNullOuVazio("email", usuario.getEmail());
		validaNullOuVazio("senha", usuario.getSenha());
		validaNullOuVazio("nome", usuario.getNome());
	}
	
	private void validaNullOuVazio(String campo, String valor) {
		if (Utils.isNullOrEmpty(valor)) {
			throw new BadRequestErrorException("Campo " + campo + " deve ser informado");   
		}
	}
}
