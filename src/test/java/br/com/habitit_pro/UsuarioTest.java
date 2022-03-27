package br.com.habitit_pro;

import br.com.habilit_pro.models.pessoa.Usuario;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;
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

        assertEquals(usuario.getNome(),"Alex");
        assertEquals(usuario.getCpf(),"554.250.480-92");

    }

//    @Test
//    public void _02_deveRetornarTodosOsTrabalhadores() {
//        List<Trabalhador> trabalhadores = service.listAll();
//        String[] nomes = {"Alex","Adriana","Rodrigo"};
//        String[] cpfs = {"554.250.480-92", "865.177.570-90", "468.521.560-52"};
//        for(int i = 0; i < trabalhadores.size(); i++) {
//            assertEquals(trabalhadores.get(i).getNome(), nomes[i]);
//            assertEquals(trabalhadores.get(i).getCpf(), cpfs[i]);
//        }
//    }
//
//    @Test
//    public void _03_deveRetornarTodosOsTrabalhadoresPorNome() {
//        List<Trabalhador> trabalhadores = service.listByName("Adriana");
//
//        assertEquals(trabalhadores.size(),1);
//        assertEquals(trabalhadores.get(0).getNome(),"Adriana");
//        assertEquals(trabalhadores.get(0).getCpf(),"865.177.570-90");
//        assertEquals(trabalhadores.get(0).getFuncao(),"Analista de RH");
//        assertEquals(trabalhadores.get(0).getSetor(),"RH");
//    }
//
//    @Test
//    public void _04_deveCriarUmTrabalhador() {
//        Trabalhador trabalhador = service.getById(1L);
//
//        Trabalhador trabalhadorNovato = new Trabalhador("Trabalhador Novato", "213.615.800-70",
//                trabalhador.getEmpresa(), "Tecnologia", "Analista de sistemas");
//
//        getService().create(trabalhadorNovato);
//
//        assertNotNull(trabalhadorNovato.getId());
//        assertEquals(trabalhadorNovato.getNome(),"Trabalhador Novato");
//        assertEquals(trabalhadorNovato.getCpf(),"213.615.800-70");
//        assertEquals(trabalhadorNovato.getEmpresa().getNome(), trabalhador.getEmpresa().getNome());
//    }

}