package persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

	@Query(value="select f from Funcionario as f where f.nome like :pnome")
	List<Funcionario> buscarNome(@Param("pnome") String nome);
	
}
