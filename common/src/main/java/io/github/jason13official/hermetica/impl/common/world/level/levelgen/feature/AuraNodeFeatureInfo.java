package io.github.jason13official.hermetica.impl.common.world.level.levelgen.feature;

import io.github.jason13official.hermetica.platform.Services;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class AuraNodeFeatureInfo {

  public static final Logger LOGGER = Logger.getLogger(AuraNodeFeatureInfo.class.getName());

  static {
    try {
      String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
      String fileName = "aura_nodes_log_" + timestamp + ".txt";

      Path filepath = Services.PLATFORM.getGameDirectory().resolve("logs").resolve(fileName);

      // Open a standard writer (true means append mode). No .lck files are created!
      FileWriter fileWriter = new FileWriter(filepath.toFile(), true);
      PrintWriter printWriter = new PrintWriter(fileWriter);

      // Create a custom Handler using the writer
      Handler customHandler = new Handler() {
        @Override
        public void publish(LogRecord record) {
          if (isLoggable(record)) {
            // printWriter.printf("[%s] %s%n", record.getLevel(), record.getMessage());
            printWriter.printf("%s%n", record.getMessage());
            printWriter.flush(); // Instantly pushes text to the file
          }
        }

        @Override
        public void flush() {
          printWriter.flush();
        }

        @Override
        public void close() throws SecurityException {
          printWriter.close();
        }
      };

      LOGGER.addHandler(customHandler);
      LOGGER.setUseParentHandlers(false);

      // Cleanly close the stream when the app exits
      Runtime.getRuntime().addShutdownHook(new Thread(customHandler::close));

    } catch (IOException e) {
      System.err.println("Failed to initialize custom log file: " + e.getMessage());
    }
  }
}

