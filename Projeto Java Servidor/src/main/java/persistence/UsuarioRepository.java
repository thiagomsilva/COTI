package persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Query(value="select u from Usuario as u where u.login = :plogin and u.senha = :psenha")
	Usuario buscarNome(@Param("plogin") String login, @Param("psenha") String senha);
	
	@Query(value="select u from Usuario as u where u.token = :ptoken")
	Usuario buscarToken(@Param("ptoken") String token);
	
}
