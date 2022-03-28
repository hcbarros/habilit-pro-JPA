package br.com.habilit_pro;

import br.com.habilit_pro.connection.JpaConnectionFactory;
import br.com.habilit_pro.dao.UsuarioDAO;
import br.com.habilit_pro.enums.Perfil;
import br.com.habilit_pro.enums.Regional;
import br.com.habilit_pro.enums.Segmento;
import br.com.habilit_pro.enums.TipoEmpresa;
import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.models.pessoa.Usuario;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;
import br.com.habilit_pro.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {


        JpaConnectionFactory factory = new JpaConnectionFactory();

        TrilhaService trilhaService = new TrilhaService(factory.getEntityManager());

        Empresa empresa = new Empresa("Senai", "13.004.510/0259-20", TipoEmpresa.MATRIZ, null,
                Segmento.FUMO, "PE", "Recife", Regional.SUL);

        EmpresaService empresaService = new EmpresaService(factory.getEntityManager());
        empresaService.create(empresa);

        Trilha t = new Trilha(empresa, "Programador");
        trilhaService = new TrilhaService(factory.getEntityManager());
        trilhaService.create(t);

        trilhaService = new TrilhaService(factory.getEntityManager());

        System.out.println(trilhaService.listAll());

        UsuarioService usuarioService = new UsuarioService(factory.getEntityManager());
        System.out.println(usuarioService.listAll());


        Empresa empresa2 = new Empresa("Senai2", "13.004.510/0317-34", TipoEmpresa.MATRIZ, null,
                Segmento.FUMO, "PE", "Gravatá", Regional.SUL);
        EmpresaService empresaService2 = new EmpresaService(factory.getEntityManager());
        empresaService2.create(empresa2);

        empresaService2 = new EmpresaService(factory.getEntityManager());

        empresaService2.update(1l, empresa);

        empresaService2 = new EmpresaService(factory.getEntityManager());

        System.out.println(empresaService2.listAll());

        Trilha t2 = new Trilha(empresa2, "Desenvolvedor");
        TrilhaService trilhaService2 = new TrilhaService(factory.getEntityManager());
        trilhaService2.update(1L, t2);

        TrilhaService trilhaService4 = new TrilhaService(factory.getEntityManager());
        trilhaService4.delete(1L);

        TrilhaService trilhaService3 = new TrilhaService(factory.getEntityManager());
        System.out.println(trilhaService3.listAll());

        usuarioService = new UsuarioService(factory.getEntityManager());

        Usuario usuario = new Usuario("Henrique", "237.096.290-98",
                "henrique@gmail.com", "henrique123", Perfil.ADMINISTRATIVO);

        usuarioService.create(usuario);

        usuarioService = new UsuarioService(factory.getEntityManager());

       System.out.println(usuarioService.listAll());


        Trabalhador trabalhador = new Trabalhador("Henrique",
                "546.816.460-40", empresa2, "Tecnologia", "Programador");

        TrabalhadorService trabalhadorService = new TrabalhadorService(factory.getEntityManager());

        trabalhadorService.create(trabalhador);

        trabalhadorService = new TrabalhadorService(factory.getEntityManager());

        System.out.println(trabalhadorService.listAll());

        Trabalhador trabalhador2 = new Trabalhador("Henrique",
                "651.176.140-11", empresa2, "Tecnologia", "Programador");

        trabalhadorService = new TrabalhadorService(factory.getEntityManager());

        trabalhadorService.create(trabalhador2);

        trabalhadorService = new TrabalhadorService(factory.getEntityManager());

        System.out.println(trabalhadorService.listAll());

        ModuloService moduloService = new ModuloService(factory.getEntityManager());

        System.out.println(moduloService.listAll());

        Modulo novoModulo = new Modulo(t, "Novo módulo", "Tarefa de teste",
                "habilidade 1", "habilidade 2", "habilidade 3", "habilidade 4");

        moduloService = new ModuloService(factory.getEntityManager());

        moduloService.create(novoModulo);

        moduloService = new ModuloService(factory.getEntityManager());

        System.out.println(moduloService.listAll());

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");

        Scanner scanner = new Scanner(System.in);

        String nomeUsuario = null;
        List<Usuario> listUsuario = new ArrayList<>();
        String senha = "";

        while (senha.isEmpty()) {
            System.out.print("Informe o nome do usuário: ");
            nomeUsuario = scanner.nextLine();
            usuarioService = new UsuarioService(factory.getEntityManager());
            listUsuario = usuarioService.listByName(nomeUsuario);
            if(!listUsuario.isEmpty()) {
                Usuario user = listUsuario.get(0);
                System.out.print("Informe o senha do usuário: ");
                senha = scanner.nextLine();
                if(!user.getSenha().equalsIgnoreCase(senha)) {
                    senha = "";
                    System.out.println("\nSenha inválida!");
                }
            }
            else {
                System.out.println("\nUsuário não encontrado!");
            }
        }

        System.out.println("ACESSO LIBERADO!");

        
        //DELETANDO QUASE TUDO, MENOS USUARIOS

        empresaService = new EmpresaService(factory.getEntityManager());
        List<Empresa> empresas = empresaService.listAll();
        empresas.forEach(e -> {
            EmpresaService eService = new EmpresaService(factory.getEntityManager());
            eService.delete(e.getId());
        });


    }
}
