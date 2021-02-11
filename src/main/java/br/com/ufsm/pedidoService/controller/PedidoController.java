package br.com.ufsm.pedidoService.controller;

import br.com.ufsm.pedidoService.dto.ProdutoQuantidadeDTO;
import br.com.ufsm.pedidoService.dto.UsuarioDTO;
import br.com.ufsm.pedidoService.dto.UsuarioProdutoQuantidadeDTO;
import br.com.ufsm.pedidoService.form.PedidosForm;
import br.com.ufsm.pedidoService.model.Pedido;
import br.com.ufsm.pedidoService.repository.PedidoRepository;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @GetMapping
    public List<Pedido> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Pedido listarUm(@PathVariable Long id) {

        return repository.findById(id).get();//.orElseThrow(() -> new PedidoNotFoundException());
        //return repositorio.findById(id).orElseThrow(PedidoNotFoundException::new); poderia ser escrito dessa forma, meu ide recomenda assim

    }
    
    private boolean AutenticacaoUsuario(Long idUsuario, String token) throws IOException, InterruptedException {
    	String uriUsuario = "http://localhost:8082/usuarios/" + idUsuario;
    	 HttpClient clientUsuario = HttpClient.newBuilder().build();
         HttpRequest requestUsuario = HttpRequest.newBuilder()
                 .uri(URI.create(uriUsuario)).header(HttpHeaders.AUTHORIZATION, token).GET().build();
         
         HttpResponse<String> responseUsuario = 
         		clientUsuario.send(requestUsuario, HttpResponse.BodyHandlers.ofString());
         
         if(responseUsuario.statusCode() == 200)
        	 return true;
         
         return false;
    }

    @PostMapping
    public ResponseEntity<Pedido >criarPedido(@RequestBody @Valid PedidosForm dadosPedido, 
    		@RequestHeader("Authorization") String token) throws IOException, InterruptedException, JSONException {

        if(AutenticacaoUsuario(dadosPedido.getIdUsuario(), token)) {
	    	
        	String uriProduto = "http://localhost:8081/api/produtos/" + dadosPedido.getIdProduto() + "/" +  dadosPedido.getQuantidade();
	        
	        HttpClient clientProduto = HttpClient.newHttpClient();
	        HttpRequest requestProduto = HttpRequest.newBuilder()
	        		.uri(URI.create(uriProduto)).header("Content-Type", "application/json").GET().build();
	
	       
	        HttpResponse<String> responseProduto =
	                clientProduto.send(requestProduto, HttpResponse.BodyHandlers.ofString());
	
	        JSONObject json = new JSONObject(responseProduto.body());
	
	        boolean disponibilidade = json.getBoolean("disponibilidade");
	      
	        if(disponibilidade) {
	        	Double valorTotal = json.getDouble("preco");
		        
		        Pedido novoPedido = new Pedido(dadosPedido.getIdUsuario(), dadosPedido.getIdProduto(), valorTotal);
		        repository.save(novoPedido);
		        return ResponseEntity.ok(novoPedido);
	        }
	        
	        ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    	
    }

    @PutMapping("/{id}")
    public Optional<Pedido> atualizarPedido(@Valid @RequestBody Pedido pedidoAtualizado, @PathVariable Long id) {

        return repository.findById(id)
                .map(pedido -> {
                    pedido.setId(pedidoAtualizado.getId());
                    return repository.save(pedido);
                });
    }

    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable Long id) {
        repository.deleteById(id);
    }
}