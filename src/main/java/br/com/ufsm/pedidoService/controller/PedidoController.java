package br.com.ufsm.pedidoService.controller;

import br.com.ufsm.pedidoService.dto.ProdutoQuantidadeDTO;
import br.com.ufsm.pedidoService.dto.UsuarioDTO;
import br.com.ufsm.pedidoService.dto.UsuarioProdutoQuantidadeDTO;
import br.com.ufsm.pedidoService.model.Pedido;
import br.com.ufsm.pedidoService.repository.PedidoRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @GetMapping("")
    public List<Pedido> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Pedido listarUm(@PathVariable Long id) {

        return repository.findById(id).get();//.orElseThrow(() -> new PedidoNotFoundException());
        //return repositorio.findById(id).orElseThrow(PedidoNotFoundException::new); poderia ser escrito dessa forma, meu ide recomenda assim

    }

    @PostMapping
    public Pedido criarPedido(@Valid @RequestBody Pedido novoPedido) {
        return repository.save(novoPedido);
    }

    @PostMapping("/aaaaa")
    public Pedido criarPedido(@RequestBody UsuarioProdutoQuantidadeDTO dadosPedido) throws IOException, InterruptedException, JSONException {

        String uriProduto = "http://localhost:8081/api/produtos/"+dadosPedido.getIdProduto()+"/"+ dadosPedido.getQuantidade();
        String uriUsuario = "http://localhost:8080/auth";

        JSONObject data = new JSONObject().put("email", dadosPedido.getEmail()).put("senha", dadosPedido.getSenha());

        HttpClient clientUsuario = HttpClient.newBuilder().build();
        HttpRequest requestUsuario = HttpRequest.newBuilder()
                .uri(URI.create(uriUsuario))
                .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
                .build();

        HttpResponse<?> responseUsuario = clientUsuario.send(requestUsuario, HttpResponse.BodyHandlers.discarding());
        System.out.println(responseUsuario.toString() + responseUsuario.statusCode());


        HttpClient clientProduto = HttpClient.newHttpClient();
        HttpRequest requestProduto = HttpRequest.newBuilder().uri(URI.create(uriProduto)).header("Content-Type", "application/json").GET().build();

        HttpResponse<String> responseProduto =
                clientProduto.send(requestProduto, HttpResponse.BodyHandlers.ofString());

        System.out.println(responseProduto.body());

        //Esse return é fajuto, botei só pra parar de dar erro enquanto tava codificando aqui em cima
        return repository.save(new Pedido(1L, 1L, 1L, 2000));
    }

    @PutMapping("/pedido/{id}")
    public Optional<Pedido> atualizarPedido(@Valid @RequestBody Pedido pedidoAtualizado, @PathVariable Long id) {

        return repository.findById(id)
                .map(pedido -> {
                    pedido.setId(pedidoAtualizado.getId());
                    return repository.save(pedido);
                });
    }

    @DeleteMapping("/pedido/{id}")
    public void deletePedido(@PathVariable Long id) {
        repository.deleteById(id);
    }
}