package storage.migrations;

import org.flywaydb.core.Flyway;

public class Init {
    public static void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:h2:C:\\prog\\жаба лабы\\Nexign_Test_Task\\src\\main\\resources\\myDB",
                        "dima", "pass")
                .locations("db/migration")
                .load();

        flyway.migrate();
    }
}
