package br.com.habilit_pro.models.pessoa;

import br.com.habilit_pro.enums.Perfil;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario extends Pessoa {

    @Pattern(regexp = "^[\\w\\.-]+@([\\w\\-]+\\.)+[a-zA-Z]{2,4}$", message = "Email inválido!")
    private String email;

    @NotNull(message = "A senha não deve ser nula!")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-zA-Z]).{8,}",
            message = "A senha deve ter ao menos 1 letra, 1 número e no mínimo 8 caracteres!")
    private String senha;

    @NotEmpty(message = "O usuário deve possuir algum perfil!")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    @ElementCollection(targetClass = Perfil.class, fetch = FetchType.EAGER)
    private Set<Perfil> perfis;

    public Usuario() {
        super();
        perfis = new HashSet<>();
    }

    public Usuario(String nome, String cpf, String email, String senha, Perfil perfil) {
        super(nome, cpf);
        this.email = email;
        this.senha = senha;
        this.perfis = new HashSet<>();
        addPerfil(perfil);
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

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

    public void addPerfil(Perfil perfil) {
        if(perfil != null && !perfis.contains(perfil)) {
            perfis.add(perfil);
        }
    }

    @Override
    public String toString() {
        String perfis = "";
        for(Perfil p: this.perfis) {
            perfis += "\n\t- "+p.getNome();
        }
        return  "\nId: "+ getId() +
                "\nNome: "+getNome() +
                "\nCPF: "+getCpf() +
                "\nEmail: "+email +
                "\nSenha: "+senha +
                (perfis.isEmpty() ? "" : "\nPerfis: "+ perfis);
    }

}
