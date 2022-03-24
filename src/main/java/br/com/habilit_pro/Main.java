package br.com.habilit_pro;

import br.com.habilit_pro.connection.JpaConnectionFactory;
import br.com.habilit_pro.dao.UsuarioDAO;
import br.com.habilit_pro.enums.Perfil;
import br.com.habilit_pro.enums.Regional;
import br.com.habilit_pro.enums.Segmento;
import br.com.habilit_pro.enums.TipoEmpresa;
import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.models.pessoa.Usuario;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;
import br.com.habilit_pro.services.EmpresaService;
import br.com.habilit_pro.services.TrabalhadorService;
import br.com.habilit_pro.services.TrilhaService;
import br.com.habilit_pro.services.UsuarioService;

import javax.persistence.EntityManager;
import java.util.List;

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

//
//        Empresa empresa2 = new Empresa("Senai2", "13.004.510/0317-34", TipoEmpresa.MATRIZ, null,
//                Segmento.FUMO, "PE", "Gravatá", Regional.SUL);
//        EmpresaService empresaService2 = new EmpresaService(factory.getEntityManager());
//        empresaService2.create(empresa2);
//
//        empresaService2 = new EmpresaService(factory.getEntityManager());
//
//        empresaService2.update(1l, empresa);
//
//        empresaService2 = new EmpresaService(factory.getEntityManager());
//
//        System.out.println(empresaService2.listAll());
//
//        Trilha t2 = new Trilha(empresa2, "Desenvolvedor");
//        TrilhaService trilhaService2 = new TrilhaService(factory.getEntityManager());
//        trilhaService2.update(1L, t2);
//
//
//        TrilhaService trilhaService4 = new TrilhaService(factory.getEntityManager());
//        trilhaService4.delete(1L);
//
//
//        TrilhaService trilhaService3 = new TrilhaService(factory.getEntityManager());
//        System.out.println(trilhaService3.listAll());
//
//
//        UsuarioService usuarioService = new UsuarioService(factory.getEntityManager());
//
//        Usuario usuario = new Usuario("Henrique", "02657226400",
//                "henrique@gmail.com", "henrique123", Perfil.ADMINISTRATIVO);
//
//        usuarioService.create(usuario);
//
//        usuarioService = new UsuarioService(factory.getEntityManager());
//
//       System.out.println(usuarioService.listAll());
//
//
//
//        Trabalhador trabalhador = new Trabalhador("Henrique",
//                "02657226400", empresa2, "Tecnologia", "Programador");
//
//        TrabalhadorService trabalhadorService = new TrabalhadorService(factory.getEntityManager());
//
//        trabalhadorService.create(trabalhador);
//
//        trabalhadorService = new TrabalhadorService(factory.getEntityManager());
//
//        System.out.println(trabalhadorService.listAll());
//
//
//        Trabalhador trabalhador2 = new Trabalhador("Henrique",
//                "24493910453", empresa2, "Tecnologia", "Programador");
//
//        trabalhadorService = new TrabalhadorService(factory.getEntityManager());
//
//        trabalhadorService.create(trabalhador2);
//
//        trabalhadorService = new TrabalhadorService(factory.getEntityManager());
//
//        System.out.println(trabalhadorService.listAll());

    }
}
