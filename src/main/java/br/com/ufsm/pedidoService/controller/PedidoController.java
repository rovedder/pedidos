package br.com.ufsm.pedidoService.controller;

import br.com.ufsm.pedidoService.dto.ProdutoQuantidadeDTO;
import br.com.ufsm.pedidoService.model.Pedido;
import br.com.ufsm.pedidoService.repository.PedidoRepository;
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
    public Pedido criarPedido(/*@RequestBody UsuarioDTO usuario, */@RequestBody ProdutoQuantidadeDTO produtoQuantidade) throws IOException, InterruptedException {

        String uriProduto = "http://localhost:8081/api/produtos/"+produtoQuantidade.getIdProduto()+"/"+ produtoQuantidade.getQuantidade();
        System.out.println(uriProduto);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uriProduto)).header("Content-Type", "application/json").GET().build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());


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