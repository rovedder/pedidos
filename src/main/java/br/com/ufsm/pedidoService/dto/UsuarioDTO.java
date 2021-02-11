package br.com.ufsm.pedidoService.dto;

public class UsuarioDTO {

    private String email;
    private String senha;

    public UsuarioDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public UsuarioDTO(UsuarioDTO usuario) {
        this.email = usuario.email;
        this.senha = usuario.getSenha();
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

    public void setSenha(String password) {
        this.senha = senha;
    }
}
