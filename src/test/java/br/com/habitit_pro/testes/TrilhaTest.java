package br.com.habitit_pro.testes;

import br.com.habilit_pro.enums.Satisfacao;
import br.com.habilit_pro.models.Trilha;

import br.com.habilit_pro.services.TrilhaService;

import br.com.habitit_pro.testes.generic.GenericTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrilhaTest extends GenericTest<TrilhaService>  {

    private void init() {
        List<Trilha> trilhas = service.listAll();
        String[] ocupacoes = {trilhas.get(1).getOcupacao(), trilhas.get(2).getOcupacao(), trilhas.get(0).getOcupacao()};
        for(int i = 0; i < trilhas.size(); i++) {
            trilhas.get(i).setOcupacao(ocupacoes[i]);
            getService().update((long)(i+1), trilhas.get(i));
        }
    }

    @Test
    public void _01_deveRetornarTrilhaPorId() {
        init();
        Trilha trilha = getService().getById(1L);
        assertEquals(trilha.getOcupacao(), "Trabalho em equipe");
        assertEquals(trilha.getEmpresa().getNome(), "Tabajara LTDA");
    }

    @Test
    public void _02_deveRetornarTodasAsTrilhas() {
        List<Trilha> trilhas = service.listAll();
        assertEquals(trilhas.get(0).getNome(),"Trabalho_em_equipeTabajara_LTDA12022");
        assertEquals(trilhas.get(1).getNome(), "Produtividade_no_trabalhoConstruções_piratas12022");
        assertEquals(trilhas.get(2).getNome(), "Desenvolvimento_de_softwareFake_Enterprise12022");
    }

    @Test
    public void _03_deveRetornarTodasAsTrilhasPorNome() {
        List<Trilha> trilhas = service.listByName("Trabalho_em_equipeTabajara_LTDA12022");
        assertEquals(trilhas.size(), 1);
        assertEquals(trilhas.get(0).getOcupacao(), "Trabalho em equipe");
        assertEquals(trilhas.get(0).getApelido(), "Trabalho_em_equipe1");
    }

    @Test
    public void _04_deveCriarUmaTrilha() {
        Trilha trilha = (Trilha) service.getById(1L);
        Trilha novaTrilha = new Trilha(trilha.getEmpresa(), "Ocupação de teste");
        getService().create(novaTrilha);
        Trilha trilhaTest = (Trilha) getService().getById(4L);

        assertEquals(trilhaTest.getNome(), "Ocupação_de_testeTabajara_LTDA12022");
        assertEquals(trilhaTest.getApelido(), "Ocupação_de_teste1");
    }

    @Test
    public void _05_deveIncrementarContadorEAlterarNomeTrilhaCasoJaExistaOutraComMesmaOcupacaoEEmpresa() {
        Trilha trilha = (Trilha) service.getById(4L);
        Trilha novaTrilha = new Trilha(trilha.getEmpresa(), "Ocupação de teste");
        getService().create(novaTrilha);
        Trilha trilhaResult = (Trilha) getService().getById(5L);

        assertNotEquals(trilhaResult.getNome(), trilha.getNome());
        assertEquals(trilhaResult.getNome(), "Ocupação_de_testeTabajara_LTDA22022");
        assertEquals(trilhaResult.getApelido(), "Ocupação_de_teste2");
    }

    @Test
    public void _06_deveAtualizarUmaTrilha() {
        Trilha trilha = (Trilha) service.getById(4L);
        Trilha trilhaTest = new Trilha(trilha.getEmpresa(),"Ocupação alterada",
                Satisfacao.NIVEL_5, "Primeira anotação");

        Trilha trilhaAlterada = (Trilha) getService().update(trilha.getId(), trilhaTest);
        assertEquals(trilhaAlterada.getNome(), "Ocupação_alteradaTabajara_LTDA12022");
        assertEquals(trilhaAlterada.getApelido(), "Ocupação_alterada1");
        assertEquals(trilhaAlterada.getSatisfacao(), Satisfacao.NIVEL_5);
        assertEquals(trilhaAlterada.getAnotacoes(), "Primeira anotação");
    }

    @Test
    public void _07_deveTer_0_A_N_ModulosCadastrados() {
        Trilha trilha = (Trilha) service.getById(1L);
        assertFalse(trilha.getModulos().isEmpty());
    }

    @Test
    public void _08_deveDeletarUmaTrilhaRetornandoNuloCasoTenteConsultarNovamente() {
        service.delete(5L);
        Trilha trilha = (Trilha) getService().getById(5L);
        assertNull(trilha);
    }

    @Test(expected = RuntimeException.class)
    public void _09_deveLancarUmaExcecaoCasoTenteSalvarTrilhaSemOcupacao() {
        Trilha trilha = (Trilha) service.getById(1L);
        Trilha trilhaTest = new Trilha(trilha.getEmpresa(), null);
        service.create(trilhaTest);
    }

}
