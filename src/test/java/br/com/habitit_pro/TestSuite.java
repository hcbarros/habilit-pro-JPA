package br.com.habitit_pro;

import br.com.habitit_pro.testes.EmpresaTest;
import br.com.habitit_pro.testes.ModuloTest;
import br.com.habitit_pro.testes.TrilhaTest;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({EmpresaTest.class,
                     TrilhaTest.class,
                     ModuloTest.class})

public class TestSuite {

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

}