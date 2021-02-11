package br.com.ufsm.pedidoService.dto;

public class UsuarioProdutoQuantidadeDTO {

    private String email;
    private String senha;
    private Long idProduto;
    private Integer quantidade;

    public UsuarioProdutoQuantidadeDTO() {
    }

    public UsuarioProdutoQuantidadeDTO(String email, String senha, Long idProduto, Integer quantidade) {
        this.email = email;
        this.senha = senha;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
