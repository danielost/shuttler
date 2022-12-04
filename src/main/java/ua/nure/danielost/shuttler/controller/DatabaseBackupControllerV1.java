package ua.nure.danielost.shuttler.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ua.nure.danielost.shuttler.utility.DatabaseUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@Configuration
//@EnableScheduling
public class DatabaseBackupControllerV1 {

//    @Scheduled(cron = "0 30 1 * * ?")
    public void schedule() throws IOException, InterruptedException {
        System.out.println("Backup Started at " + new Date());

        Date backupDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String backupDateStr = format.format(backupDate);
        String backupName = "src\\main\\resources\\backups\\shuttler_backup_" + backupDateStr + ".sql";

        boolean backedUp = DatabaseUtil.backup("root", "wcWXN4U7!A36", "shuttler", backupName);

        if (backedUp) {
            System.out.println("Backup Complete at " + new Date());
        } else {
            System.out.println("Backup Failure");
        }
    }
}
