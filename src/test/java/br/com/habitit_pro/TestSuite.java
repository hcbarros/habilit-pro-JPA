package br.com.habitit_pro;

import br.com.habitit_pro.testes.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({EmpresaTest.class,
                     TrabalhadorTest.class,
                     UsuarioTest.class,
                     ModuloTest.class,
                     TrilhaTest.class,
                     TruncateTest.class})

public class TestSuite {

}