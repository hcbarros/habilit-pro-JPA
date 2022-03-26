package br.com.habitit_pro.testes.generic;

import br.com.habilit_pro.connection.JpaConnectionFactory;
import br.com.habilit_pro.services.generic.Service;

import org.junit.Before;

import javax.persistence.EntityManager;

import java.lang.reflect.ParameterizedType;


public class GenericTest<T extends Service> {

    private static final JpaConnectionFactory factory = new JpaConnectionFactory();;
    private Class<T> classe;
    protected T service;

    public GenericTest() {
        classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Before
    public void setUp() throws Exception {
        getService();
    }

    public T getService() {
        service = getStaticService(classe);
        return service;
    }

    public static <T> T getStaticService(Class<T> tClass) {
        try {
            return tClass.getDeclaredConstructor(EntityManager.class)
                    .newInstance(factory.getEntityManager());
        }
        catch (Exception ex) {
            throw new RuntimeException("ERRO: "+ex.getMessage());
        }
    }

}
