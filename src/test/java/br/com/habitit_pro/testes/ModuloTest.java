package br.com.habitit_pro.testes;

import br.com.habilit_pro.enums.Status;
import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.services.ModuloService;
import br.com.habitit_pro.testes.generic.GenericTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModuloTest extends GenericTest<ModuloService> {

    @Test
    public void _01_deveRetornarModuloPorId() {
        Modulo modulo = service.getById(1L);
        assertEquals(modulo.getNome(), "Orientação a objetos");
        assertEquals(modulo.getPrazo_limite(), 10);
        assertTrue(modulo.getHabilidades().contains("RACIOCÍNIO LÓGICO"));
    }

    @Test
    public void _02_deveRetornarTodosOsModulos() {
        List<Modulo> modulos = service.listAll();
        String[] nomes = {"Orientação a objetos","Relacionamento interpessoal","Foco na atividade"};
        for(int i = 0; i < modulos.size(); i++) {
            assertEquals(modulos.get(i).getNome(), nomes[i]);
            assertEquals(modulos.get(i).getPrazo_limite(), 10);
            assertFalse(modulos.get(i).getHabilidades().isEmpty());
        }
    }

    @Test
    public void _03_deveRetornarTodosOsModulosPorNome() {
        List<Modulo> modulos = service.listByName("Relacionamento interpessoal");
        assertEquals(modulos.size(), 1);
        assertEquals(modulos.get(0).getNome(), "Relacionamento interpessoal");
    }

    @Test
    public void _04_deveCriarUmModulo() {
        Modulo modulo = service.getById(1L);
        Modulo novoModulo = new Modulo(modulo.getTrilha(), "Novo módulo", "Tarefa de teste",
                "habilidade 1", "habilidade 2", "habilidade 3", "habilidade 4");
        getService().create(novoModulo);

        assertNotNull(novoModulo.getId());
        assertEquals(novoModulo.getNome(), "Novo módulo");
        assertEquals(novoModulo.getHabilidades().size(), 4);
    }

    @Test
    public void _05_deveAtualizarUmModulo() {
        Modulo modulo = service.getById(4L);
        Modulo moduloAlterado = new Modulo(modulo.getTrilha(), "Módulo_alterado","Tarefa alterada");
        Modulo response = getService().update(modulo.getId(), moduloAlterado);

        assertNotEquals(modulo.getNome(), response.getNome());
        assertNotEquals(modulo.getTarefaValidacao(), response.getTarefaValidacao());

        assertEquals(response.getNome(), "Módulo_alterado");
    }

    @Test
    public void _06_deveAdicionarHabilidades() {
        Modulo modulo = service.getById(4L);
        Modulo response = getService()
                .addHabilidades(modulo.getId(),"teste1", "teste2", "teste3");

        List.of("teste1","teste2","teste3").forEach(hab -> {
            assertTrue(response.getHabilidades().contains(hab));
        });
    }

    @Test
    public void _07_deveRemoverHabilidades() {
        Modulo modulo = service.getById(4L);

        Set<String> habs = modulo.getHabilidades();
        Modulo response = getService().removeHabilidades(modulo.getId(),
                                                habs.toArray(new String[habs.size()]));

        assertFalse(modulo.getHabilidades().isEmpty());
        assertTrue(response.getHabilidades().isEmpty());
    }

    @Test
    public void _08_deveLiberarAvaliacaoCasoStatusEstejaEmFaseDeAvaliacao() {
        Modulo modulo = service.getById(4L);

        Modulo response = getService().updateStatus(modulo.getId(), Status.EM_FASE_AVALIACAO);

        assertNull(modulo.getInicioAvaliacao());
        assertNotNull(response.getInicioAvaliacao());
    }

    @Test
    public void _09_deveAlterarStatusParaEm_AndamentoERegistrarDataInicioCasoModuloSejaIniciado() {
        Modulo modulo = service.getById(3L);

        Modulo response = getService().iniciarModulo(modulo.getId(), OffsetDateTime.now());

        assertNull(modulo.getInicioModulo());
        assertNull(modulo.getStatus());
        assertNotNull(response.getInicioModulo());
        assertEquals(response.getStatus(), Status.EM_ANDAMENTO);
    }

    @Test
    public void _10_deveAlterarStatusParaEm_Fase_AvaliacaoERegistrarDataFimCasoModuloSejaFinalizado() {
        Modulo modulo = service.getById(2L);

        Modulo response = getService().finalizarModulo(modulo.getId(), OffsetDateTime.now());

        assertNull(modulo.getFimModulo());
        assertNull(modulo.getStatus());
        assertNotNull(response.getFimModulo());
        assertEquals(response.getStatus(), Status.EM_FASE_AVALIACAO);
    }

    @Test
    public void _11_deve_Data_de_inicio_ou_de_fim_de_modulo_apresentar_formato_OffsetDateTime() {
        Modulo modulo = service.getById(2L);;
        String regex = "[\\d]{4}-[\\d]{2}-[\\d]{2}T[\\d]{2}:[\\d]{2}:[\\d]{2}.[\\d]+-[\\d]{2}:[\\d]{2}";
        assertTrue(modulo.getFimModulo().toString().matches(regex));
    }

    @Test
    public void _12_deveDeletarModulo() {
        Modulo modulo = service.getById(4L);
        getService().delete(4L);
        Modulo moduloInexistente = getService().getById(4L);

        assertNotNull(modulo.getId());
        assertNull(moduloInexistente);
    }

}
