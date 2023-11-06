package raf.sk_schedule.util.persistence;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.exception.ScheduleIOException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScheduleFileWriter {


    public static File createFileIfNotExists(String filePath) {
        File file = new File(filePath);
        System.out.println(filePath);
        if (!file.exists()) {
            // Get the file name from the path
            String fileName = file.getName();

            try {
                // Create a new file in the same directory with the extracted name
                File newFile = new File(file.getParent(), fileName);

                if (newFile.createNewFile()) {
                    System.out.println("File created: " + newFile.getAbsolutePath());
                    return newFile; // Return the newly created File object
                } else {
                    throw new ScheduleException("Failed to create the file.");
                }
            } catch (IOException e) {
                throw new ScheduleIOException( "An error occurred: " + e.getMessage());
            }
        }

        return file; // If the file already exists, return the existing File object
    }

    public static void writeStringToFile(File file, String content) {
        try {
            FileWriter fileWriter = new FileWriter(file, true); // 'true' enables append mode
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(content);
            writer.newLine(); // Add a newline character after the content
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
