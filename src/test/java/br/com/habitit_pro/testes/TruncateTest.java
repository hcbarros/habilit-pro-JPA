package br.com.habitit_pro.testes;

import br.com.habilit_pro.models.pessoa.Usuario;

import br.com.habilit_pro.services.*;
import br.com.habitit_pro.testes.generic.GenericTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TruncateTest extends GenericTest<EmpresaService> {

    @Test
    public void _01_deveDeletarTodosOsDadosDeTodasAsTabelasComExcecaoDeUsuario() {

        service.listAll().forEach(e -> getService().delete(e.getId()));

        assertTrue(getStaticService(EmpresaService.class).listAll().isEmpty());
        assertTrue(getStaticService(TrabalhadorService.class).listAll().isEmpty());
        assertTrue(getStaticService(TrilhaService.class).listAll().isEmpty());
        assertTrue(getStaticService(ModuloService.class).listAll().isEmpty());
        assertTrue(getStaticService(ModuloTrabalhadorService.class).listAll().isEmpty());
    }

    @Test
    public void _02_deveDeletarOsDadosDeUsuarioESeusPerfis() {
        List<Usuario> usuarios = getStaticService(UsuarioService.class).listAll();
        usuarios.forEach(u -> getStaticService(UsuarioService.class).delete(u.getId()));

        assertTrue(getStaticService(UsuarioService.class).listAll().isEmpty());
    }

}
