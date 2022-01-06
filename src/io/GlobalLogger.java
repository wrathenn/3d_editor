package io;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GlobalLogger {
    private static Logger logger;

    private GlobalLogger() {}

    private static void setLogger() {
        try {
            String date = (new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
            FileHandler fh = new FileHandler(System.getProperty("user.dir") + "/logs/log" + date);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            logger.addHandler(fh);
        }
        catch (IOException e) {
            System.out.println("Ошибка при создании logger'а");
        }
    }

    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger("global_logger");
            setLogger();
        }

        return logger;
    }
}
