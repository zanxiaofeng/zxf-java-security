package zxf.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTests {
    public static void main(String[] args) {
        createFile();
        createFolder();
        createFolders();
    }

    // The mode of newly created file is 666 + umask
    private static void createFile() {
        try {
            File myFile = new File("myFile.txt");
            if (myFile.createNewFile()) {
                System.out.println("File Created: " + myFile.getName());
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    // The mode of newly created Directory is 777 + umask
    private static void createFolder() {
        File myDirectory = new File("myDirectory");
        if (myDirectory.mkdir()) {
            System.out.println("Directory Created: " + myDirectory.getName());
        } else {
            System.out.println("Directory already exists");
        }
    }

    // The mode of newly created Directories is 777 + umask
    private static void createFolders() {
        File myDirectory = new File("myParentDir/myDirectory");
        if (myDirectory.mkdirs()) {
            System.out.println("Directory Created: " + myDirectory.getName());
        } else {
            System.out.println("Directory already exists");
        }
    }


}