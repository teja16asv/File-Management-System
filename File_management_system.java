import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class File_management_system {

    // Ensures the file has a .txt extension
    private static String ensureTxtExtension(String fileName) {
        return fileName.endsWith(".txt") ? fileName : fileName + ".txt";
    }

    // Lists all available .txt files
    private static void listFiles() {
        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        System.out.println("\nAvailable Files:");
        if (files != null && files.length > 0) {
            for (File file : files) {
                System.out.println("- " + file.getName());
            }
        } else {
            System.out.println("No text files found.");
        }
    }

    public static void writeToFile(String fileName, String content) {
        fileName = ensureTxtExtension(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("File written successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void appendToFile(String fileName, String content) {
        fileName = ensureTxtExtension(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            System.out.println("File appended successfully.");
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    public static void readFromFile(String fileName) {
        fileName = ensureTxtExtension(fileName);
        if (!Files.exists(Paths.get(fileName))) {
            System.out.println("File not found!");
            return;
        }
        System.out.println("Reading from file:");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void modifyFile(String fileName, String target, String replacement) {
        fileName = ensureTxtExtension(fileName);
        if (!Files.exists(Paths.get(fileName))) {
            System.out.println("File not found!");
            return;
        }
        try {
            Path path = Paths.get(fileName);
            String content = new String(Files.readAllBytes(path));
            content = content.replaceAll(target, replacement);
            Files.write(path, content.getBytes());
            System.out.println("File modified successfully.");
        } catch (IOException e) {
            System.err.println("Error modifying file: " + e.getMessage());
        }
    }

    public static void deleteFile(String fileName) {
        fileName = ensureTxtExtension(fileName);
        if (!Files.exists(Paths.get(fileName))) {
            System.out.println("File not found!");
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(fileName));
            System.out.println("File deleted successfully.");
        } catch (IOException e) {
            System.err.println("Error deleting file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- File Management System ---");
            listFiles();
            System.out.println("\nChoose an operation:");
            System.out.println("1. Create/Write File");
            System.out.println("2. Append to File");
            System.out.println("3. Read File");
            System.out.println("4. Modify File");
            System.out.println("5. Delete File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (choice == 6) {
                System.out.println("Exiting...");
                scanner.close();
                return;
            }
            
            System.out.print("Enter file name: ");
            String fileName = scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter content to write: ");
                    String content = scanner.nextLine();
                    writeToFile(fileName, content);
                    break;
                case 2:
                    System.out.print("Enter content to append: ");
                    String appendContent = scanner.nextLine();
                    appendToFile(fileName, appendContent);
                    break;
                case 3:
                    readFromFile(fileName);
                    break;
                case 4:
                    System.out.print("Enter word to replace: ");
                    String target = scanner.next();
                    System.out.print("Enter replacement word: ");
                    String replacement = scanner.next();
                    modifyFile(fileName, target, replacement);
                    break;
                case 5:
                    deleteFile(fileName);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}


