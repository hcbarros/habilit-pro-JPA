package br.com.habitit_pro.testes;

import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;
import br.com.habilit_pro.services.TrabalhadorService;
import br.com.habitit_pro.testes.generic.GenericTest;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class TrabalhadorTest extends GenericTest<TrabalhadorService> {

    @Test
    public void _01_deveRetornarTrabalhadorPorId() {
        Trabalhador trabalhador = service.getById(1L);

        assertEquals(trabalhador.getNome(),"Alex");
        assertEquals(trabalhador.getCpf(),"554.250.480-92");
        assertEquals(trabalhador.getFuncao(),"Programador I");
        assertEquals(trabalhador.getSetor(),"T.I.");
    }

    @Test
    public void _02_deveRetornarTodosOsTrabalhadores() {
        List<Trabalhador> trabalhadores = service.listAll();
        String[] nomes = {"Alex","Adriana","Rodrigo"};
        String[] cpfs = {"554.250.480-92", "865.177.570-90", "468.521.560-52"};
        for(int i = 0; i < trabalhadores.size(); i++) {
            assertEquals(trabalhadores.get(i).getNome(), nomes[i]);
            assertEquals(trabalhadores.get(i).getCpf(), cpfs[i]);
        }
    }

    @Test
    public void _03_deveRetornarTodosOsTrabalhadoresPorNome() {
        List<Trabalhador> trabalhadores = service.listByName("Adriana");

        assertEquals(trabalhadores.size(),1);
        assertEquals(trabalhadores.get(0).getNome(),"Adriana");
        assertEquals(trabalhadores.get(0).getCpf(),"865.177.570-90");
        assertEquals(trabalhadores.get(0).getFuncao(),"Analista de RH");
        assertEquals(trabalhadores.get(0).getSetor(),"RH");
    }

    @Test
    public void _04_deveCriarUmTrabalhador() {
        Trabalhador trabalhador = service.getById(1L);

        Trabalhador trabalhadorNovato = new Trabalhador("Trabalhador Novato", "213.615.800-70",
                trabalhador.getEmpresa(), "Tecnologia", "Analista de sistemas");

        getService().create(trabalhadorNovato);

        assertNotNull(trabalhadorNovato.getId());
        assertEquals(trabalhadorNovato.getNome(),"Trabalhador Novato");
        assertEquals(trabalhadorNovato.getCpf(),"213.615.800-70");
        assertEquals(trabalhadorNovato.getEmpresa().getNome(), trabalhador.getEmpresa().getNome());
    }

    @Test(expected = RuntimeException.class)
    public void _05_deveLancarUmaExcecaoCasoTenteCriarUmTrabalhadorComCpfInvalido() {
        Trabalhador trabalhador = service.getById(1L);

        Trabalhador trabalhadorNovato = new Trabalhador("Trabalhador", "043.086.820-01",
                trabalhador.getEmpresa(), "Tecnologia", "Analista de dados");

        getService().create(trabalhadorNovato);
    }

    @Test
    public void _06_deveApresentarUmaDataQuandoAFuncaoDoTrabalhadorForAlterada() {
        Trabalhador trabalhador = service.getById(1L);

        Trabalhador trab_Teste = new Trabalhador("Teste", "619.811.200-41",
                trabalhador.getEmpresa(), "Tecnologia", null);

        getService().create(trab_Teste);
        String funcaoInicial = trab_Teste.getFuncao();
        trab_Teste.setFuncao("Gerente de projetos");
        Trabalhador response = getService().update(trab_Teste.getId(), trab_Teste);

        assertNull(funcaoInicial);
        assertEquals(response.getFuncao(), "Gerente de projetos");
        assertNotNull(response.getDataAlteracao());
    }

    @Test
    public void _07_deveListarTrilhasAssociadasAUmTrabalhador() {
        List<Trilha> trilhas = (List<Trilha>) service.listTrilhasByTrabalhadorId(1L);


        System.out.println(trilhas);

        Trabalhador t1 = getService().getById(1L);

        System.out.println(t1.getModulosTrabalhador());


        Trabalhador t2 = getService().getById(2L);

        t1.setEmpresa(t2.getEmpresa());
        Trabalhador novoT1 = getService().update(t1.getId(), t1);

        trilhas = (List<Trilha>) getService().listTrilhasByTrabalhadorId(novoT1.getId());

        System.out.println(trilhas);
        System.out.println(novoT1.getModulosTrabalhador());



    }

}
