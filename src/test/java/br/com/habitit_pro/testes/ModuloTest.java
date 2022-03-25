package br.com.habitit_pro.testes;

import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.services.ModuloService;
import br.com.habitit_pro.testes.generic.GenericTest;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModuloTest extends GenericTest<ModuloService> {


    @Test
    public void _01_deveRetornarModuloPorId() {
        Modulo modulo = service.getById(1L);
        assertEquals(modulo.getNome(), "Orientação a objetos");
        // assertEquals(empresa.getCnpj(), "80.641.905/0001-80");
    }




}
