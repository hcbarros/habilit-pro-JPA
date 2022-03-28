package br.com.habitit_pro.testes;

import br.com.habilit_pro.enums.Perfil;
import br.com.habilit_pro.models.pessoa.Usuario;

import br.com.habilit_pro.services.UsuarioService;
import br.com.habitit_pro.testes.generic.GenericTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioTest extends GenericTest<UsuarioService> {

    @Test
    public void _01_deveRetornarUsuarioPorId() {
        Usuario usuario = service.getById(1L);

        assertEquals(usuario.getNome(),"José");
        assertEquals(usuario.getCpf(),"058.677.000-38");
        assertEquals(usuario.getEmail(),"jose@gmail.com");
        assertEquals(usuario.getSenha(),"jose1234");
    }

    @Test
    public void _02_deveRetornarTodosOsUsuarios() {
        List<Usuario> usuarios = service.listAll();
        String[] nomes = {"José","Maria","Manoel"};
        String[] cpfs = {"058.677.000-38", "612.768.110-39", "729.295.050-79"};
        String[] emails = {"jose@gmail.com","maria@gmail.com","manoel@gmail.com"};
        for(int i = 0; i < usuarios.size(); i++) {
            assertEquals(usuarios.get(i).getNome(), nomes[i]);
            assertEquals(usuarios.get(i).getCpf(), cpfs[i]);
            assertEquals(usuarios.get(i).getEmail(), emails[i]);
        }
    }

    @Test
    public void _03_deveRetornarTodosOsUsuariosPorNome() {
        List<Usuario> usuarios = service.listByName("Maria");

        assertEquals(usuarios.size(),1);
        assertEquals(usuarios.get(0).getNome(),"Maria");
        assertEquals(usuarios.get(0).getCpf(),"612.768.110-39");
        assertEquals(usuarios.get(0).getEmail(),"maria@gmail.com");
    }

    @Test
    public void _04_deveCriarUmUsuario() {
        Usuario usuario = new Usuario("Alexandre", "272.370.930-29",
                "alex@gmail.com", "alex9876", Perfil.OPERACIONAL);

        service.create(usuario);

        assertNotNull(usuario.getId());
    }

    @Test(expected = RuntimeException.class)
    public void _05_deveLancarUmaExcecaoCasoTenteCriarUmUsuarioComCpfInvalido() {
        Usuario usuario = new Usuario("Teste", "086.008.720-48",
                "cpfinvalido@gmail.com", "alex9876", Perfil.ADMINISTRATIVO);

        service.create(usuario);
    }

    @Test
    public void _06_deveAtualizarUmUsuarioEAdicionarVariosPerfisAEle() {
        Usuario usuario = service.getById(1L);

        Usuario novo = new Usuario("Nome teste", null,
                "nomeTeste@gmail.com", "senha123", Perfil.RH);
        for(Perfil p: Perfil.values()) {
            novo.addPerfil(p);
        }
        Usuario resposnse = getService().update(usuario.getId(), novo);

        assertEquals(resposnse.getNome(), novo.getNome());
        assertEquals(resposnse.getEmail(), novo.getEmail());
        assertEquals(resposnse.getSenha(), novo.getSenha());
        assertNotEquals(usuario.getPerfis().size(), resposnse.getPerfis().size());
    }

    @Test
    public void _07_deveASenhaPossuirNoMinimo8CaracteresEDeveConterLetrasENumeros() {
        Usuario usuario = service.getById(1L);
        assertTrue(usuario.getSenha().matches("(?=.*\\d)(?=.*[a-zA-Z]).{8,}"));
    }

    @Test
    public void _08_deveDeletarUmUsuario() {
        Usuario usuario = service.getById(3L);
        getService().delete(usuario.getId());

        Usuario usuarioInexistente = getService().getById(3L);

        assertNotNull(usuario.getId());
        assertNull(usuarioInexistente);
    }


}