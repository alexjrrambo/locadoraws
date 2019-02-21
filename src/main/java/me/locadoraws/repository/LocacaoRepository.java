package me.locadoraws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import me.locadoraws.model.Locacao;

/**
 * @author Alex Junior Rambo 
 */

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Integer> {
				
	//Custom query para verificar se usuário está com filme locado
	@Query(value = "SELECT CASE WHEN count(*)>0 THEN true ELSE false END FROM locacao l WHERE l.id_filme = :idFilme and l.id_usuario = :idUsuario AND situacao = 'L'", nativeQuery = true)
    int existeFilmeLocadoUsuario(int idFilme, int idUsuario);
	
	//Custom query para devolver o filme
	@Modifying
    @Query(value = "UPDATE locacao SET situacao = 'D' WHERE id_filme = :idFilme AND situacao = 'L'", nativeQuery = true)
	int devolverFilmePorId(int idFilme);	
}
