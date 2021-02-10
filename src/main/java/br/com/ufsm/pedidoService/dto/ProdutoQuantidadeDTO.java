package br.com.ufsm.pedidoService.dto;

public class ProdutoQuantidadeDTO {

    private Long idProduto;
    private Integer quantidade;

    public ProdutoQuantidadeDTO(Long idProduto, Integer quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
