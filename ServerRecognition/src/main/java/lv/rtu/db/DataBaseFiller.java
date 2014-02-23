package lv.rtu.db;

import lv.rtu.domain.User;

import java.io.File;
import java.util.regex.Pattern;

public class DataBaseFiller {

    public static void fillDB() {

        DatabaseTools tools = new DatabaseTools();
        tools.clear("server_user");

        UserTableImplementationDAO table = new UserTableImplementationDAO();
        User user = new User(13l, "Aleksandr", "Kovalcuks", "admin", getFiles("./resources/data/audio/training", "aleksandrs"), getFiles("./resources/data/images/training", "aleksandrs"));
        table.insert(user);
        user = new User(14l, "Aihua", "Nirid", "user", getFiles("./resources/data/audio/training", "aihua"), getFiles("./resources/data/images/training", "aihua"));
        table.insert(user);
        user = new User(15l, "Alexandrm", "Nugi", "user", getFiles("./resources/data/audio/training", "alexandrm"), getFiles("./resources/data/images/training", "alexandrm"));
        table.insert(user);
        user = new User(16l, "Emily", "Gugi", "user", getFiles("./resources/data/audio/training", "emily"), getFiles("./resources/data/images/training", "emily"));
        table.insert(user);
        user = new User(17l, "Jim", "Omori", "user", getFiles("./resources/data/audio/training", "jim"), getFiles("./resources/data/images/training", "jim"));
        table.insert(user);
        user = new User(18l, "Ke", "Omori", "user", getFiles("./resources/data/audio/training", "ke"), getFiles("./resources/data/images/training", "ke"));
        table.insert(user);
    }

    public static String getFiles(String fileName, String name) {

        File[] aoFiles = new File(fileName).listFiles();

        String audioFiles = "";

        for (int i = 0; i < aoFiles.length; i++) {
            String[] files = aoFiles[i].toString().split(Pattern.quote(File.separator));
            String file = files[files.length - 1];
            if (file.contains(name)) {
                audioFiles = audioFiles + file + ",";
            }
        }
        return audioFiles;
    }

}
