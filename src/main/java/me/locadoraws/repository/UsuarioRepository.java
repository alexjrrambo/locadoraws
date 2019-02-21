package me.locadoraws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import me.locadoraws.model.Usuario;

/**
 * @author Alex Junior Rambo 
 */

//Interface extende para o JpaRepository, assim ganhamos funcionalidades de buscar todos registros, update, insert, delete e etc...
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	//Custom query para validar login do usuário
	@Query(value = "SELECT * FROM usuario u WHERE email = :email AND senha = :senha", nativeQuery = true)
	Usuario buscarPorEmail(String email, String senha);
	
	//Custom query para buscar usuário pelo token de acesso
	@Query(value = "SELECT * FROM usuario u WHERE acess_token = :acessToken", nativeQuery = true)
	Usuario buscarPorAcessToken(String acessToken);
	    
}
