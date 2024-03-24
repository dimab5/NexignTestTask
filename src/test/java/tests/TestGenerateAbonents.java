package tests;

import models.Abonent;
import org.junit.Assert;
import org.junit.Test;
import services.CdrService;
import services.ICdrService;

import java.util.List;

public class TestGenerateAbonents {
    @Test
    public void test() {
        ICdrService cdrService = new CdrService();

        List<Abonent> abonents = cdrService.generateAbonents(10);
        Assert.assertEquals(abonents.size(), 10);
    }
}
