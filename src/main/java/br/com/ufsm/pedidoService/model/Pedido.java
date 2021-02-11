package br.com.ufsm.pedidoService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUsuario;
    private Long idProduto;
    private Double valorTotal;
    
    public Pedido(Long idUsuario, Long idProduto, Double valorTotal) {
    	this.idUsuario = idUsuario;
    	this.idProduto = idProduto;
    	this.valorTotal = valorTotal;
    }

}
