package program;

import services.CdrService;
import services.ICdrService;
import services.IUdrService;
import services.UdrService;
import storage.migrations.Init;

public class Main {
    public static void main(String[] args) {
        Init.migrate();

        ICdrService cdrService = new CdrService();
        cdrService.generateCdr();

        IUdrService udrService = new UdrService();
        udrService.generateReport();
    }
}
