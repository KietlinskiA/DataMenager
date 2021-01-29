import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;

public class Main {

    private static final String firstPath = "F:\\IdeaProjects\\DataMenager";
    private static final String secondPath = "\\src\\main\\resources\\";
    private static final String basePath = firstPath + secondPath;
    // 0-HOME, 1-DEV, 2-TEST
    private static final String[] myPaths = {basePath + "HOME", basePath + "DEV", basePath + "TEST"};
    private static final Path home = Paths.get(myPaths[0]); //HOME
    private static int countMoveFiles = 0;

    public static void main(String[] args) throws IOException {
        init();

        moveXMLFiles();
        moveJARFiles();

        countMoveFilesWriter();
    }

    private static void init() throws IOException {
        System.out.println("Tworzę strukturę katalogów...");

        // Base path
        if (Files.isDirectory(Paths.get(basePath))) {
            Files.delete(Paths.get(basePath));
        }
        Files.createDirectory(Paths.get(basePath));

        // Home, Dev, Test
        for (String s : myPaths) {
            Path path = Paths.get(s);
            if (!Files.isDirectory(path)) {
                Files.createDirectory(path);
            }
        }

        // count.txt
        String countMoveFilesPath = home.toString() + "\\count.txt";
        if (!Files.exists(Paths.get(countMoveFilesPath))) {
            Files.createFile(Paths.get(countMoveFilesPath));
        }

        prepareData();
    }

    private static void prepareData() throws IOException {
        // Create prepare data
        Files.createFile(Paths.get(home.toString() + "\\example1.jar"));
        Files.createFile(Paths.get(home.toString() + "\\example2.xml"));
        Files.createFile(Paths.get(home.toString() + "\\example3.jar"));
        Files.createFile(Paths.get(home.toString() + "\\example4.jar"));
        Files.createFile(Paths.get(home.toString() + "\\example5.jar"));
        Files.createFile(Paths.get(home.toString() + "\\example6.jar"));
        Files.createFile(Paths.get(home.toString() + "\\example7.xml"));
        Files.createFile(Paths.get(home.toString() + "\\example8.jar"));
        Files.createFile(Paths.get(home.toString() + "\\example9.xml"));
        Files.createFile(Paths.get(home.toString() + "\\example10.jar"));
        Files.createFile(Paths.get(home.toString() + "\\example11.jar"));
        Files.createFile(Paths.get(home.toString() + "\\example12.xml"));
        Files.createFile(Paths.get(home.toString() + "\\example13.jar"));
    }

    private static void moveXMLFiles() throws IOException {
        //Find files with XML extension
        PathMatcher pathMatcherXML = FileSystems.getDefault().getPathMatcher("regex:.*(?i:xml)");

        DirectoryStream<Path> stream = Files.newDirectoryStream(home, file -> pathMatcherXML.matches(file.getFileName()));

        for (Path path : stream) {
            if (!Files.isDirectory(path)) {
                Files.move(path, Paths.get(myPaths[1] + "\\" + path.getFileName().toString()));
                countMoveFiles++;
            } else {
                System.out.println("Taki plik już istnieje: "+path.toString());
            }
        }
        stream.close();
    }

    private static void moveJARFiles() throws IOException {
        //Find files with JAR extension
        PathMatcher pathMatcherJAR = FileSystems.getDefault().getPathMatcher("regex:.*(?i:jar)");

        DirectoryStream<Path> stream = Files.newDirectoryStream(home, file -> pathMatcherJAR.matches(file.getFileName()));

        for (Path path : stream) {
            if (!Files.isDirectory(path)) {
                int x = 1;

                if (path.toFile().lastModified() % 2 != 0) {
                    x++;
                }

                Files.move(path, Paths.get(myPaths[x] + "\\" + path.getFileName().toString()));
                countMoveFiles++;
            } else {
                System.out.println("Taki plik już istnieje: "+path.toString());
            }
        }
        stream.close();
    }

    private static void countMoveFilesWriter() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(home + "\\count.txt"));
        bufferedWriter.write("Liczba przeniesionych folderów: " + countMoveFiles);
        bufferedWriter.close();
    }
}