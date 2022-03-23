package br.com.habilit_pro;

import br.com.habilit_pro.connection.JpaConnectionFactory;
import br.com.habilit_pro.enums.Regional;
import br.com.habilit_pro.enums.Segmento;
import br.com.habilit_pro.enums.TipoEmpresa;
import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.services.EmpresaService;
import br.com.habilit_pro.services.TrilhaService;

import javax.persistence.EntityManager;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        JpaConnectionFactory factory = new JpaConnectionFactory();

        Empresa empresa = new Empresa("Senai", "13.004.510/0259-20", TipoEmpresa.MATRIZ, null,
                Segmento.FUMO, "PE", "Recife", Regional.SUL);

        EmpresaService empresaService = new EmpresaService(factory.getEntityManager());
        empresaService.create(empresa);

        Trilha t = new Trilha(empresa, "Programador");
        TrilhaService trilhaService = new TrilhaService(factory.getEntityManager());
        trilhaService.create(t);

        Empresa empresa2 = new Empresa("Senai2", "13.004.510/0317-34", TipoEmpresa.MATRIZ, null,
                Segmento.FUMO, "PE", "Gravatá", Regional.SUL);
        EmpresaService empresaService2 = new EmpresaService(factory.getEntityManager());
        empresaService2.create(empresa2);


        Trilha t2 = new Trilha(empresa2, "Desenvolvedor");
        TrilhaService trilhaService2 = new TrilhaService(factory.getEntityManager());
        trilhaService2.update(1L, t2);


        TrilhaService trilhaService4 = new TrilhaService(factory.getEntityManager());
        trilhaService4.delete(1L);


        TrilhaService trilhaService3 = new TrilhaService(factory.getEntityManager());
        System.out.println(trilhaService3.listAll());


    }
}
