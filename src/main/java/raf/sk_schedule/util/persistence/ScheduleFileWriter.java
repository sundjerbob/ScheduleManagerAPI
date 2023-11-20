package raf.sk_schedule.util.persistence;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.exception.ScheduleIOException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for handling file operations related to the scheduling component.
 */
public class ScheduleFileWriter {


    /**
     * Creates a new file if it does not already exist.
     *
     * @param filePath The path of the file to be created.
     * @return The File object representing the created or existing file.
     * @throws ScheduleException   If there is an issue creating the file.
     * @throws ScheduleIOException If an I/O error occurs while creating the file.
     */
    public static File initializeFile(String filePath) {
        File file = new File(filePath);
        System.out.println(filePath);
        if (!file.exists()) {
            // Get the file name from the path
            String fileName = file.getName();

            try {
                // Create a new file in the same directory with the extracted name
                File newFile = new File(file.getParent(), fileName);
                if (newFile.createNewFile())
                    return newFile; // Return the newly created File object
            } catch (IOException e) {
                throw new ScheduleIOException(e);
            }
        }

        return file; // If the file already exists, return the existing File object
    }

    /**
     * Writes a string content to a file.
     *
     * @param file    The File object representing the target file.
     * @param content The string content to be written to the file.
     */
    public static void writeStringToFile(File file, String content) {
        try {
            FileWriter fileWriter = new FileWriter(file, true); // 'true' enables append mode
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(content);
            writer.newLine(); // Add a newline character after the content
            writer.close();
        } catch (IOException e) {
            throw new ScheduleIOException(e);
        }
    }
}
