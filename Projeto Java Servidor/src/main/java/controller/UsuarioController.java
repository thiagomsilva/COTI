package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.Endereco;
import model.Funcionario;
import model.Usuario;
import persistence.UsuarioRepository;

@RestController
@RequestMapping("usuario/")
public class UsuarioController {

	@Autowired
	UsuarioRepository uDao;
	
	@RequestMapping(value="cadastrar", method=RequestMethod.POST)
	@ResponseBody
	//public ResponseEntity<String> cadastrar(@RequestBody Usuario user){
	public ResponseEntity<String> cadastrar(@RequestParam String nome, @RequestParam String login, @RequestParam String senha){
		try {
			Usuario user = new Usuario();
			user.setLogin(login);
			user.setSenha(senha);
			user.setNome(nome);
			
			Random rand = new Random();
			user.setToken(String.valueOf(rand.nextInt()));
			
			uDao.save(user);
			uDao.flush();
			return new ResponseEntity<String>("Dados enviados, " + user.getNome(), HttpStatus.OK); 
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Erro ao gravar usuario", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="listar", method=RequestMethod.GET)
	public ResponseEntity<String> listar(){
		String resp;
		try {
			List<Usuario> lista = new ArrayList<Usuario>();
			lista = uDao.findAll();
			return new ResponseEntity<String>( new Gson().toJson(lista) , HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			resp = "Usuario não pode ser carregado";
			return new ResponseEntity<String>(resp, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="getToken", method=RequestMethod.POST, 
			consumes= { MediaType.APPLICATION_JSON_VALUE })	
	public @ResponseBody ResponseEntity<String> getToken(@RequestBody Usuario usu){
		JsonObject json = new JsonObject();
		try {
			
			Usuario u = uDao.buscarNome(usu.getLogin(), usu.getSenha());
			if(u == null) throw new Exception("Usuario nao encontrado");
			
			
			json.addProperty("value", "Dados encontrados");
			json.addProperty("token", u.getToken());
			return new ResponseEntity<String>(new Gson().toJson(json), HttpStatus.OK);
			
		}catch (Exception e) {
			e.printStackTrace();
			json.addProperty("value", e.getMessage());
			json.addProperty("token", "");
			
			return new ResponseEntity<String>(new Gson().toJson(json), HttpStatus.BAD_REQUEST);
		}
	}
}
