package br.com.ufsm.pedidoService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoQuantidadeDTO {

    private Long idProduto;
    private Integer quantidade;

}
