package br.com.habitit_pro;

import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Trilha;

import br.com.habilit_pro.services.TrilhaService;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrilhaTest extends GenericTest {

    @BeforeClass
    public static void setUpClass() {
        List<Trilha> trilhas = getService(TrilhaService.class).listAll();
        String[] ocupacoes = {trilhas.get(1).getOcupacao(), trilhas.get(2).getOcupacao(), trilhas.get(0).getOcupacao()};
        for(int i = 0; i < trilhas.size(); i++) {
            trilhas.get(i).setOcupacao(ocupacoes[i]);
            service = getService(TrilhaService.class);
            service.update((long)(i+1), trilhas.get(i));
        }
    }

    @Test
    public void _01_deveRetornarTrilhaPorId() {
        Trilha trilha = (Trilha) service.getById(1L);
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
        getService(TrilhaService.class).create(novaTrilha);
        service = getService(TrilhaService.class);

//        assertEquals(trilhas.size(), 1);
//        assertEquals(trilhas.get(0).getOcupacao(), "Trabalho em equipe");
//        assertEquals(trilhas.get(0).getApelido(), "Trabalho_em_equipe1");
    }



}
