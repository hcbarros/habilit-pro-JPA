package br.com.habitit_pro;

import br.com.habilit_pro.connection.JpaConnectionFactory;
import br.com.habilit_pro.services.*;

import org.junit.AfterClass;
import org.junit.Before;

import javax.persistence.EntityManager;

public class GenericTest {

    private static final JpaConnectionFactory factory = new JpaConnectionFactory();;
    protected static Service service;

    @Before
    public void setUp() throws Exception {
        service = getService(service.getClass());
    }

    public static Service getService(Class<? extends Service> classe) {
        try {
            service = classe.getDeclaredConstructor(EntityManager.class)
                    .newInstance(factory.getEntityManager());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return service;
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

}
