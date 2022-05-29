package br.com.habitit_pro.testes;

import br.com.habilit_pro.enums.Satisfacao;
import br.com.habilit_pro.models.Trilha;

import br.com.habilit_pro.services.TrilhaService;

import br.com.habitit_pro.testes.generic.GenericTest;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrilhaTest extends GenericTest<TrilhaService>  {

    @Test
    public void _01_deveRetornarTrilhaPorId() {
        Trilha trilha = service.getById(1L);
        assertEquals(trilha.getOcupacao(), "Desenvolvimento de software");
        assertEquals(trilha.getEmpresa().getNome(), "Tabajara LTDA");
    }

    @Test
    public void _02_deveRetornarTodasAsTrilhas() {
        List<Trilha> trilhas = service.listAll();
        assertEquals(trilhas.get(0).getNome(),"Desenvolvimento_de_softwareTabajara_LTDA12022");
        assertEquals(trilhas.get(1).getNome(), "Trabalho_em_equipeConstruções_piratas12022");
        assertEquals(trilhas.get(2).getNome(), "Produtividade_no_trabalhoFake_Enterprise12022");
    }

    @Test
    public void _03_deveRetornarTodasAsTrilhasPorNome() {
        List<Trilha> trilhas = service.listByName("Trabalho_em_equipeConstruções_piratas12022");
        assertEquals(trilhas.size(), 1);
        assertEquals(trilhas.get(0).getOcupacao(), "Trabalho em equipe");
        assertEquals(trilhas.get(0).getApelido(), "Trabalho_em_equipe1");
    }

    @Test
    public void _04_deveCriarUmaTrilha() {
        Trilha trilha = service.getById(1L);
        Trilha novaTrilha = new Trilha(trilha.getEmpresa(), "Ocupação de teste");
        getService().create(novaTrilha);
        assertNotNull(novaTrilha.getId());
    }

    @Test
    public void _05_deveAtualizarUmaTrilha() {
        Trilha trilha = service.getById(4L);
        Trilha trilhaTest = new Trilha(trilha.getEmpresa(),"Ocupação alterada",
                Satisfacao.NIVEL_5, "Primeira anotação");

        Trilha trilhaAlterada = getService().update(trilha.getId(), trilhaTest);
        assertEquals(trilhaAlterada.getOcupacao(), "Ocupação alterada");
        assertEquals(trilhaAlterada.getSatisfacao(), Satisfacao.NIVEL_5);
        assertEquals(trilhaAlterada.getAnotacoes(), "Primeira anotação");
    }

    @Test
    public void _06_deveTer_0_A_N_ModulosCadastrados() {
        Trilha trilha = service.getById(1L);
        assertFalse(trilha.getModulos().isEmpty());
    }

    @Test
    public void _07_deveDeletarUmaTrilhaRetornandoNuloCasoTenteConsultarNovamente() {
        Trilha trilha = service.getById(4L);
        getService().delete(4L);
        Trilha trilhaInexistente = getService().getById(4L);

        assertNotNull(trilha.getId());
        assertNull(trilhaInexistente);
    }

    @Test(expected = RuntimeException.class)
    public void _08_deveLancarUmaExcecaoCasoTenteSalvarTrilhaSemOcupacao() {
        Trilha trilha = service.getById(1L);
        Trilha trilhaTest = new Trilha(trilha.getEmpresa(), null);
        service.create(trilhaTest);
    }

}
