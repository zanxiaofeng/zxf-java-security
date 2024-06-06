package zxf.file;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class NIOFileTests {

    public static void main(String[] args) {
        createFile();
        createFileWithCustomMode();
        createFolder();
        createFolders();
    }

    // The mode of newly created file is 666 + umask
    private static void createFile() {
        try {
            Path pathToFile = Paths.get("myFile-nio.txt");
            Path newPath = Files.createFile(pathToFile);
            System.out.println("File Created: " + newPath);
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    private static void createFileWithCustomMode() {
        try {
            Path pathToFile = Paths.get("myFile-nio-mode.txt");
            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-xr-x");
            Path newPath = Files.createFile(pathToFile, PosixFilePermissions.asFileAttribute(perms));
            System.out.println("File Created: " + newPath);
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    // The mode of newly created Directory is 777 + umask
    private static void createFolder() {
        try {
            Path pathToDirectory = Paths.get("myDirectory-nio");
            Path newPath = Files.createDirectory(pathToDirectory);
            System.out.println("Directory Created: " + newPath);
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    // The mode of newly created Directories is 777 + umask
    private static void createFolders() {
        try {
            Path pathToDirectory = Paths.get("myParentDir-nio/mySubDirectory-nio");
            Path newPath = Files.createDirectories(pathToDirectory);
            System.out.println("Directory Created: " + newPath);
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }
}
