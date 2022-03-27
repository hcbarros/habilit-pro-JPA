package br.com.habitit_pro.testes;

import br.com.habilit_pro.enums.Avaliacao;
import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;
import br.com.habilit_pro.services.ModuloService;
import br.com.habilit_pro.services.TrabalhadorService;
import br.com.habilit_pro.services.TrilhaService;
import br.com.habitit_pro.testes.generic.GenericTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void _06_deveAtualizarUmTrabalhador() {
        Trabalhador t1 = service.getById(3L);
        Trabalhador t2 = getService().getById(4L);

        Trabalhador novo = new Trabalhador();
        novo.setNome("Nome teste");
        novo.setSetor("Fábrica de software");
        novo.setFuncao("Mecânico");
        novo.setEmpresa(t1.getEmpresa());

        Trabalhador resposnse = getService().update(t2.getId(), novo);

        assertNotEquals(t2.getNome(), resposnse.getNome());
        assertNotEquals(t2.getSetor(), resposnse.getSetor());
        assertNotEquals(t2.getFuncao(), resposnse.getFuncao());
        assertNotEquals(t2.getEmpresa().getNome(), resposnse.getEmpresa().getNome());
    }


    @Test
    public void _07_deveApresentarUmaDataQuandoAFuncaoDoTrabalhadorForAlterada() {
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
    public void _08_devePermitirEdicaoEmpresaTrabalhadorSemAfetarHistoricoTrilhasAnteriores() {
        Trabalhador t1 = service.getById(1L);
        Trabalhador t2 = getService().getById(2L);

        Empresa empresaAntiga = t1.getEmpresa();

        t1.setEmpresa(t2.getEmpresa());
        Trabalhador t1Atualizado = getService().update(t1.getId(), t1);

        Empresa empresaNova = t1Atualizado.getEmpresa();

        List<Trilha> trilhasT1Atualizado = getStaticService(TrilhaService.class)
                .listTrilhasByTrabalhadorId(t1Atualizado.getId());

        assertNotEquals(empresaAntiga.getNome(), empresaNova.getNome());
        assertEquals(trilhasT1Atualizado.get(0).getEmpresa().getNome(), empresaAntiga.getNome());
    }

    @Test
    public void _09_devePermitirEdicaoSetorEFuncaoTrabalhadorSemAfetarHistoricoTrilhasAnteriores() {
        Trabalhador tAntigo = service.getById(1L);

        tAntigo.setFuncao("Analista de negócios");
        tAntigo.setSetor("Financiamento");

        Trabalhador tAtual = getService().update(tAntigo.getId(), tAntigo);

        tAtual.getModulosTrabalhador().forEach(mt -> {
            assertNotEquals(mt.getFuncao(), tAntigo.getFuncao());
            assertNotEquals(mt.getSetor(), tAntigo.getSetor());
        });
    }

    @Test
    public void _10_devePermitirAdicionarModuloQueOTrabalhadorParticipaOuParticipou() {
        Trabalhador trabalhador = service.getById(1L);

        Modulo modulo = getStaticService(ModuloService.class).getById(2L);

        ModuloTrabalhador mt = new ModuloTrabalhador(modulo, Avaliacao.NOTA_5, "Primeira anotação");

        Trabalhador response = getService().addModuloTrabalhador(trabalhador.getId(), mt);

        assertNotEquals(trabalhador.getModulosTrabalhador().size(), response.getModulosTrabalhador().size());
    }

    @Test
    public void _11_devePermitirRemoverModuloQueOTrabalhadorParticipaOuParticipou() {
        Trabalhador trabalhador = service.getById(1L);

        ModuloTrabalhador mt = new ModuloTrabalhador();
        mt.setId(4L);
        Trabalhador response = getService().removeModuloTrabalhador(trabalhador.getId(), mt);

        assertNotEquals(trabalhador.getModulosTrabalhador().size(), response.getModulosTrabalhador().size());
    }

    @Test
    public void _12_deveDeletarUmTrabalhador() {
        Trabalhador trabalhador = service.getById(1L);
        getService().delete(trabalhador.getId());
        Trabalhador trabalhadorInexistente = getService().getById(1L);

        assertNotNull(trabalhador.getId());
        assertNull(trabalhadorInexistente);
    }


}
