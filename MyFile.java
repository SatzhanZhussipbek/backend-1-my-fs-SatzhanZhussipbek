import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class MyFile {
    // выводит список всех файлов и директорий для `path` - ls
    public static void listDirectory(String path) throws IOException {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            System.out.println(Arrays.toString(file.list())
                    .replace("[", "").replace("]", "").
                            replace(",", ""));
        }
        else if (!file.exists() || !file.isDirectory()) {
            System.out.println("Такого файла не существует!");
            System.out.println("Либо объект не является папкой.");
        }
    }
    // выводит список файлов с расширением `.py` в `path` - ls_py
    public static void listPythonFiles(String path) {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory() && file.exists()) {
            for (int i = 0; i < Objects.requireNonNull(file.list()).length; i++) {
                if (Objects.requireNonNull(file.list())[i].contains(".py")) {
                    list.add(Objects.requireNonNull(file.list())[i]);
                }
            }
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i) + " ");
            }
            System.out.println();
        }
        else if (!file.exists() || !file.isDirectory()) {
            System.out.println("Такого файла не существует!");
            System.out.println("Либо объект не является папкой.");
        }
    }
    // выводит `true`, если `path` это директория, в других случаях `false` - id_dir
    public static void isDirectory(String path) {
        File file = new File(path);
        if (file.exists()) {
            System.out.println(file.isDirectory());
        }
        else if (!file.exists()) {
            System.out.println("Такого файла не существует!");
        }
    }
    // выводит `директория` или `файл` в зависимости от типа `path` - define
    public static void define(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                System.out.println("It is a file.");
            } else if (file.isDirectory()) {
                System.out.println("It is a directory.");
            }
        }
        else if (!file.exists()) {
            System.out.println("Такого файла не существует!");
        }
    }
    // выводит права для файла в формате `rwx` для текущего пользователя - readmod
    // rwx, -wx,
    public static void printPermissions(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.canRead()) {
                System.out.print("r");
            } else {
                System.out.print("-");
            }
            if (file.canWrite()) {
                System.out.print("w");
                if (file.canExecute()) {
                    System.out.print("x");
                } else {
                    System.out.print("-");
                }
            } else {
                System.out.print("-");
            }
            System.out.println();
        }
        else if (!file.exists()) {
            System.out.println("Такого файла не существует!");
        }
    }
    // устанавливает права для файла `path` - setmod
    public static void setPermissions(String path, String permissions) {
        File file = new File(path);
        if (file.exists()) {
            try {
                if (permissions.charAt(0) == 'r') {
                    file.setReadable(true);
                } else if (permissions.charAt(0) == '-') {
                    file.setReadable(false);
                }
                if (permissions.charAt(1) == 'w') {
                    file.setWritable(true);
                } else if (permissions.charAt(1) == '-') {
                    file.setWritable(false);
                }
                if (permissions.charAt(2) == 'x') {
                    file.setExecutable(true);
                } else if (permissions.charAt(2) == '-') {
                    file.setExecutable(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (!file.exists()) {
            System.out.println("Такого файла не существует!");
        }


    }
    // выводит контент файла - cat
    public static void printContent(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            try {
                Scanner obj = new Scanner(file);
                while (obj.hasNextLine()) {
                    System.out.println(obj.nextLine());
                }
            } catch (Exception e) {
                System.out.printf("%s", e);
            }
        }
        else if (!file.exists() || !file.isFile()) {
            System.out.println("Такого файла не существует!");
            System.out.println("Либо объект не является файлом");
        }

    }
    // добавляет строке `# Autogenerated line` в конец `path` - append
    public static void appendFooter(String path) throws IOException {
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            FileWriter fileW = new FileWriter(path, true);
            fileW.append("#Autogenerated Line\r\n");
            fileW.close();
        }
        if (!file.exists() || !file.isFile()) {
            System.out.println("Такого файла не существует!");
            System.out.println("Либо объект не является файлом");
        }
    }
    // создает копию `path` в директорию `/tmp/${date}.backup` где, date - это дата в формате `dd-mm-yyyy`.
    // `path` может быть директорией или файлом. При директории, копируется весь контент. - bc
    public static void createBackup(String path) throws IOException, NullPointerException {
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = LocalDate.now().format(formats);
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                Files.copy(Path.of(path), Path.of("/tmp/${" + date + "}.backup"));
            } else if (file.isDirectory()) {
                Files.walk(Paths.get(path)).forEach(s -> {
                    Path d = Paths.get("/tmp/${" + date + "}.backup",
                            s.toString().substring(path.length()));
                    try {
                        Files.copy(s, d);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        else if (!file.exists()) {
            System.out.println("Такого файла или директории не существует!");
        }
    }
    // выводит самое длинное слово в файле - greplong
    public static void printLongestWord(String path) {
        ArrayList<String> strings = new ArrayList<>();
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            try {
                Scanner obj = new Scanner(file);
                while (obj.hasNextLine()) {
                    strings.add(Arrays.toString(obj.nextLine().split(" ")));
                }
            } catch (Exception e) {
                System.out.printf("%s", e);
            }
            int count = 0;
            int maxCount = 1;
            String maxWord = "";
            for (int i = 0; i < strings.size(); i++) {
                count = 0;
                for (int j = 0; j < strings.get(i).length(); j++) {
                    if (strings.get(i).charAt(j) != ',' && strings.get(i).charAt(j) != ' '
                            && strings.get(i).charAt(j) != '[' && strings.get(i).charAt(j) != ']') {
                        count++;
                        if (count > maxCount) {
                            maxCount = count;
                            maxWord = strings.get(i).substring(j + 1 - maxCount, j + 1);
                        }
                    } else if (strings.get(i).charAt(j) == ',' || strings.get(i).charAt(j) == ' ') {
                        count = 0;
                    }
                }
            }
            System.out.println(maxWord);
        }
        if (!file.exists() || !file.isFile()) {
            System.out.println("Такого файла не существует!");
            System.out.println("Либо объект не является файлом");
        }
    }
    // выводит список команд и их описание - help
    public static void help() {
        System.out.println("""
                MyFS 1.0 команды:
                    ls <path>               выводит список всех файлов и директорий для `path`
                    ls_py <path>            выводит список файлов с расширением `.py` в `path`
                    is_dir <path>           выводит `true`, если `path` это директория, в других случаях `false`
                    define <path>           выводит `директория` или `файл` в зависимости от типа `path`
                    readmod <path>          выводит права для файла в формате `rwx` для текущего пользователя
                    setmod <path> <perm>    устанавливает права для файла `path`
                    cat <path>              выводит контент файла
                    append <path>           добавляет строку `# Autogenerated line` в конец `path`
                    bc <path>               создает копию `path` в директорию `/tmp/${date}.backup` где, date - это дата в формате `dd-mm-yyyy`
                    greplong <path>         выводит самое длинное слово в файле
                    help                    выводит список команд и их описание
                    exit                    завершает работу программы""");
    }
    // завершает работу программы - exit
    public static void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
