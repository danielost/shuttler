package ua.nure.danielost.shuttler.utility;

import java.io.IOException;

public class DatabaseUtilTest {

    public static void main(String[] args) throws IOException, InterruptedException {
//        DatabaseUtil.backup("root", "wcWXN4U7!A36", "shuttler", "backup_test.sql");

        DatabaseUtil.restore("root", "wcWXN4U7!A36", "shuttler", "backup_test.sql");
    }
}
