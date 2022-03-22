package br.com.habilit_pro;

import br.com.habilit_pro.connection.JpaConnectionFactory;
import br.com.habilit_pro.enums.Regional;
import br.com.habilit_pro.enums.Segmento;
import br.com.habilit_pro.enums.TipoEmpresa;
import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Trilha;

import javax.persistence.EntityManager;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("".matches("(?=.*[a-zA-Z])[0-9a-zA-Z$*&@# ]"));

        EntityManager em = new JpaConnectionFactory().getEntityManager();

        Empresa empresa = new Empresa("Senai", "13.004.510/0259-20", TipoEmpresa.MATRIZ, null,
                Segmento.FUMO, "PE", "Recife", Regional.SUL);

        em.getTransaction().begin();

        em.persist(empresa);
        em.getTransaction().commit();

        Trilha t = new Trilha(empresa, "Programador");
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.persist(new Trilha(empresa, "Programador"));
        em.getTransaction().commit();

        em.getTransaction().begin();

        List<Trilha> list = em.createNativeQuery("select * from Trilha",
                Trilha.class).getResultList();

        System.out.println(list);

        em.close();
    }
}
