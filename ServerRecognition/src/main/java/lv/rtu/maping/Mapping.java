package lv.rtu.maping;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Mapping {

    private static Map<String, String> map = new HashMap<String, String>();

    public static Map getMap() {
        return map;
    }

    public synchronized static void addElementToMap(String source, String destination) {
        map.put(source, destination);
    }

    public synchronized static String getDestination(String source) {
        return map.get(source);
    }

    public synchronized static void mappingToFile() {
        File file = new File("./mapping/mapping");
        FileOutputStream f = null;
        ObjectOutputStream s = null;
        try {
            f = new FileOutputStream(file);
            s = new ObjectOutputStream(f);
            s.writeObject(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static void mappingFromFile() {
        File file = new File("./mapping/mapping");
        FileInputStream f = null;
        ObjectInputStream s = null;
        try {
            f = new FileInputStream(file);
            s = new ObjectInputStream(f);
            map = (HashMap<String, String>) s.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
