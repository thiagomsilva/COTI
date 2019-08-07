package controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dto.FuncionarioDTO;
import model.Endereco;
import model.Funcionario;
import model.Usuario;
import persistence.FuncionarioRepository;
import persistence.UsuarioRepository;

@RestController
@RequestMapping("funcionario/")
public class FuncionarioController {

	@Autowired
	FuncionarioRepository fDao;
	
	@Autowired
	UsuarioRepository uDao;
	
	public Boolean isAccess(String token) {
		Usuario user = uDao.buscarToken(token);
		if(user == null) {
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value="cadastrar", method=RequestMethod.POST, 
			consumes= { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<String> cadastrar(@RequestBody Funcionario f){
		try {
			/*
			 * { "nome" : "Jose", "salario" : 3500, "endereco" : { "cidade" : "RIo de Janeiro", "cep" : "22750-050"} }
			 */
			if(f.getEndereco() != null) {
				f.getEndereco().setFuncionario(f);
			}
			fDao.save(f);
			fDao.flush();
			
			return new ResponseEntity<String>("Dados enviados " + f.getNome(), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Erro ao gravar os dados", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="buscar/{pnome}", method=RequestMethod.GET, 
			consumes= { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<String> buscar(@PathVariable("pnome") String nome, @RequestHeader HttpHeaders headers){
		try {
			
			String token = headers.getFirst("Authorization");
			if(!isAccess(token)) {
				//Status 401
				return new ResponseEntity<String>("TOKEN INVALIDO", HttpStatus.UNAUTHORIZED);
			}
			//System.out.println("Token: " + token);
			
			List<FuncionarioDTO> lista = new ArrayList<>();
			for(Funcionario f : fDao.buscarNome(nome + "%")) {
				FuncionarioDTO fDto = new FuncionarioDTO();
				fDto.nome = f.getNome();
				fDto.salario = NumberFormat.getInstance().format( f.getSalario() );
				lista.add(fDto);
			}
			
			JsonObject json = new JsonObject();
			json.addProperty("lista", new Gson().toJson(lista));
			return new ResponseEntity<String>(new Gson().toJson(json), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Erro ao gravar os dados", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="delete/{id}", method=RequestMethod.GET, 
			consumes= { MediaType.APPLICATION_JSON_VALUE })	
	public @ResponseBody ResponseEntity<String> deletar(@PathVariable("id") String id){
		try {
			Funcionario f = fDao.findOne(new Integer(id));
			if(f == null) throw new Exception("Funcionario nao encontrado");

			fDao.delete(f);
			fDao.flush();
			
			JsonObject json = new JsonObject();
			json.addProperty("result", "Funcionario excluído com sucesso!");
			return new ResponseEntity<String>(new Gson().toJson(json), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Erro ao excluir gravar os dados", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="findone/{id}", method=RequestMethod.GET, 
			consumes= { MediaType.APPLICATION_JSON_VALUE })	
	public @ResponseBody ResponseEntity<String> buscarUm(@PathVariable("id") String id){
		try {
			Funcionario f = fDao.findOne(new Integer(id));
			if(f == null) throw new Exception("Funcionario nao encontrado");
				
			FuncionarioDTO fDto = new FuncionarioDTO();
			fDto.id = f.getId();
			fDto.nome = f.getNome();
			fDto.salario = NumberFormat.getInstance().format( f.getSalario() );
			fDto.idEndereco = f.getEndereco().getId();
			fDto.cep = f.getEndereco().getCep();
			fDto.cidade = f.getEndereco().getCidade();
			
			JsonObject json = new JsonObject();
			json.addProperty("f", new Gson().toJson(fDto));
			return new ResponseEntity<String>(new Gson().toJson(json), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Erro ao excluir gravar os dados", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="atualizar/{id}", method=RequestMethod.PUT, 
			consumes= { MediaType.APPLICATION_JSON_VALUE })	
	public @ResponseBody ResponseEntity<String> atualizar(@PathVariable("id") String id, @RequestBody Funcionario func){
		try {
			
			Funcionario f = fDao.findOne(new Integer(id));
			Endereco end = f.getEndereco();
			
			f.setNome(func.getNome());
			f.setSalario(func.getSalario());
			
			if(func.getEndereco() != null) {
				end.setCidade(func.getEndereco().getCidade());
				end.setCep(func.getEndereco().getCep());
				
				f.setEndereco(end);
			}
			
			fDao.save(f);
			fDao.flush();
			
			return new ResponseEntity<String>("Dados atualizados", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Erro ao excluir gravar os dados", HttpStatus.BAD_REQUEST);
		}
	}
	
}
