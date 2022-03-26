package br.com.habitit_pro.testes;

import br.com.habilit_pro.enums.Regional;
import br.com.habilit_pro.enums.Segmento;
import br.com.habilit_pro.enums.TipoEmpresa;
import br.com.habilit_pro.models.Empresa;

import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.services.EmpresaService;
import br.com.habitit_pro.testes.generic.GenericTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmpresaTest extends GenericTest<EmpresaService> {

    @Test
    public void _01_deveRetornarEmpresaPorId() {
        Empresa empresa = service.getById(1L);
        assertEquals(empresa.getNome(), "Tabajara LTDA");
        assertEquals(empresa.getCnpj(), "80.641.905/0001-80");
    }

    @Test
    public void _02_deveRetornarTodasAsEmpresas() {
        List<Empresa> empresas = service.listAll();
        String[][] matriz = {{"Tabajara LTDA","80.641.905/0001-80"},{"Construções piratas","48.491.224/0001-70"},{"Fake Enterprise","05.130.415/0001-01"}};
        for(int i = 0; i < matriz.length; i++) {
            assertEquals(empresas.get(i).getNome(), matriz[i][0]);
            assertEquals(empresas.get(i).getCnpj(), matriz[i][1]);
        }
    }

    @Test
    public void _03_deveRetornarTodasAsEmpresasPorNome() {
        List<Empresa> empresas = service.listByName("Fake Enterprise");
        assertEquals(empresas.size(), 1);
        assertEquals(empresas.get(0).getCnpj(), "05.130.415/0001-01");
        assertEquals(empresas.get(0).getRegional(), Regional.OESTE);
        assertEquals(empresas.get(0).getSegmento(), Segmento.TIC);
    }

    @Test
    public void _04_deveCriarUmaEmpresa() {
        Empresa empresa = new Empresa("Empresa_teste", "29.615.423/0001-06",
                TipoEmpresa.MATRIZ,"nome_invisível", Segmento.SANEAMENTO_BASICO,
                "SC", "Florianópolis", Regional.LITORAL_SUL);
        service.create(empresa);

        assertNotNull(empresa.getId());
        assertEquals(empresa.getNome(), "Empresa_teste");
        assertEquals(empresa.getCnpj(), "29.615.423/0001-06");
        assertNull(empresa.getNomeFilial());
    }

    @Test
    public void _05_deveAtualizarEmpresaEPermitirEdicaoDeNomeFilial() {
        Empresa empresa = new Empresa("Empresa_teste_alterada", "29.615.423/0001-06",
                TipoEmpresa.FILIAL,"nome_filial_visivel", Segmento.CELULOSE_E_PAPEL,
                "SC", "Tubarão", Regional.NORTE_NORDESTE);

        Empresa empresa_alterada = service.update(4L, empresa);

        assertEquals(empresa_alterada.getNome(), "Empresa_teste_alterada");
        assertEquals(empresa_alterada.getCnpj(), "29.615.423/0001-06");
        assertEquals(empresa_alterada.getNomeFilial(), "nome_filial_visivel");
        assertEquals(empresa_alterada.getTipo(), TipoEmpresa.FILIAL);
        assertEquals(empresa_alterada.getRegional(), Regional.NORTE_NORDESTE);
    }

    @Test(expected = RuntimeException.class)
    public void _06_deveLancarExcecaoCasoTenteCriarEmpresaComCnpjInvalido() {
        Empresa empresa = new Empresa("Empresa_teste", "29.615.423/0001-00",
                TipoEmpresa.MATRIZ,"nome_invisível", Segmento.SANEAMENTO_BASICO,
                "SC", "Florianópolis", Regional.LITORAL_SUL);
        service.create(empresa);
    }

    @Test
    public void _07_devemAsEmpresasApresentarCnpjComFormatoEspecifico() {
        List<Empresa> empresas = service.listAll();
        empresas.forEach(e -> {
            assertTrue(e.getCnpj().matches("[\\d]{2}.[\\d]{3}.[\\d]{3}/[\\d]{4}-[\\d]{2}"));
        });
    }

    @Test
    public void _08_deveDeletarUmaEmpresaRetornandoNuloCasoTenteConsultarNovamente() {
        service.delete(4L);
        Empresa empresa = getService().getById(4L);
        assertNull(empresa);
    }

    @Test
    public void _09_deveSerPossivelAssociarTrilhasAUmaEmpresa() {
        Empresa empresa = service.getById(1L);
        Set<Trilha> trilhas = empresa.getTrilhas();
        assertFalse(trilhas.isEmpty());
    }


}
