package ar.edu.unlam.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.proyecto.entidades.Token;

@Repository
public interface RepositorioDeToken extends JpaRepository<Token, Long>{

    List<Token> findAllByEmailId(String idMail);

    @Query("select t from Token t where (t.authenticationToken LIKE ?1 and t.userID = ?2)")
	List<Token> findByUserAndToken(String token, int emailId);
	
}
