package br.com.habitit_pro.testes;

import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.services.ModuloService;
import br.com.habitit_pro.testes.generic.GenericTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModuloTest extends GenericTest<ModuloService> {

    @Test
    public void _01_deveRetornarModuloPorId() {
        Modulo modulo = service.getById(1L);
        System.out.println(modulo);
        assertEquals(modulo.getNome(), "Orientação a objetos");
        assertEquals(modulo.getPrazo_limite(), 10);
        assertEquals(modulo.getTrilha().getNome(), "Trabalho_em_equipeTabajara_LTDA12022");
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
        Modulo moduloAlterado = new Modulo(modulo.getTrilha(), "Módulo_alterado", "Tarefa de teste",
                "habilidade 1", "habilidade 2", "habilidade 3", "habilidade 4");
//        getService().update(modulo.getId(), )
//
//        assertNotNull(novoModulo.getId());
//        assertEquals(novoModulo.getNome(), "Novo módulo");
//        assertEquals(novoModulo.getHabilidades().size(), 4);
    }



}
