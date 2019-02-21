package me.locadoraws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import me.locadoraws.model.Filme;

/**
 * @author Alex Junior Rambo 
 */

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {    

	Filme findById(int id);
	
	//Query para retornar filmes com base no titulo
	List<Filme> findByTituloContaining(String titulo);
	
	//Custom query para buscar todos filmes disponíveis
	@Query(value = "SELECT * FROM filme WHERE quantidade_disponivel > 0", nativeQuery = true)
    List<Filme> buscarTodosFilmesDisponiveis();	

	//Custom query para subtrair quantidade_disponivel ao filme
	@Modifying
    @Query(value = "UPDATE filme SET quantidade_disponivel = (quantidade_disponivel - 1) WHERE id = :idFilme", nativeQuery = true)
	int locarFilmePorId(int idFilme);	

	//Custom query para adicionar quantidade_disponivel ao filme
	@Modifying
    @Query(value = "UPDATE filme SET quantidade_disponivel = (quantidade_disponivel + 1) WHERE id = :idFilme", nativeQuery = true)
	int devolverFilmePorId(int idFilme);
}