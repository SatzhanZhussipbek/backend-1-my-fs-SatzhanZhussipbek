import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        MyFile.help();
        while(true) {
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.contains("ls")) {
               MyFile.listDirectory(input.substring(3));
            }
            if (input.contains("ls_py")) {
                MyFile.listPythonFiles(input.substring(6));
            }
            if (input.contains("exit")) {
                MyFile.exit();
                return;
            }
            if (input.contains("is_dir")) {
                MyFile.isDirectory(input.substring(7));
            }
            if (input.contains("define")) {
                MyFile.define(input.substring(7));
            }
            if (input.contains("readmod")) {
                MyFile.printPermissions(input.substring(8));
            }
            if (input.contains("setmod")) {
                MyFile.setPermissions(input.substring(7),
                        input.substring(input.length()-3));
            }
            if (input.contains("cat")) {
                MyFile.printContent(input.substring(4));
            }
            if (input.contains("append")) {
                MyFile.appendFooter(input.substring(7));
            }
            if (input.contains("bc")) {
                MyFile.createBackup(input.substring(3));
            }
            if (input.contains("greplong")) {
                MyFile.printLongestWord(input.substring(9));
            }
            if (input.contains("help")) {
                MyFile.help();
            }
        }


    }
}
