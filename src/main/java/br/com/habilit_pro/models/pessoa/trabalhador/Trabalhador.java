package br.com.habilit_pro.models.pessoa.trabalhador;

import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.pessoa.Pessoa;
import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Trabalhador extends Pessoa {

    private String setor;

    private String funcao;

    private LocalDate dataAlteracao;

    @NotNull(message = "Um trabalhador deve estar associado a uma empresa!")
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "empresa_id_trabalhador", referencedColumnName = "id")
    private Empresa empresa;

    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "modulo_trabalhador_id")
    private Set<ModuloTrabalhador> modulosTrabalhador;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinTable(name="trabalhador_trilha",
//            joinColumns={@JoinColumn(name="trabalhador_id")},
//            inverseJoinColumns={@JoinColumn(name="trilha_id")})
//    private Set<Trilha> trilhas;


    public Trabalhador() {
        super();
        modulosTrabalhador = new HashSet<>();
        //trilhas = new HashSet<>();
    }

    public Trabalhador(String nome, String cpf, Empresa empresa, String setor, String funcao) {
        super(nome, cpf);
        setEmpresa(empresa);
        setSetor(setor);
        setFuncao(funcao);
        modulosTrabalhador = new HashSet<>();
        //trilhas = new HashSet<>();
    }


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        if(this.funcao == null || !this.funcao.equalsIgnoreCase(funcao)) {
            dataAlteracao = LocalDate.now();
        }
        this.funcao = funcao;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Set<ModuloTrabalhador> getModulosTrabalhador() {
        return modulosTrabalhador;
    }

    public void setModulosTrabalhador(Set<ModuloTrabalhador> modulosTrabalhador) {
        this.modulosTrabalhador = modulosTrabalhador;
    }

//    public Set<Trilha> getTrilhas() {
//        return trilhas;
//    }
//
//    public void setTrilhas(Set<Trilha> trilhas) {
//        this.trilhas = trilhas;
//    }

    @Override
    public String toString() {
        return  "\nId: "+ getId() +
                "\nNome: "+getNome() +
                "\nCPF: "+getCpf() +
                "\nEmpresa atual: "+empresa.getNome() +
                "\nFunção atual: "+ funcao +
                "\nInício da função atual: "+ dataAlteracao +
                "\nSetor da empresa: "+setor;
    }

}
