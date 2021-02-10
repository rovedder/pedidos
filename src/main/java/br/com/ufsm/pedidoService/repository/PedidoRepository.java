package br.com.ufsm.pedidoService.repository;

import br.com.ufsm.pedidoService.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}