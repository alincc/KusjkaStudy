package lv.rtu.maping;

import lv.rtu.external_camera.IPCamera;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IPCamMapping {

    private static Map<IPCamera, String> map = new HashMap<IPCamera, String>();

    public static Map getMap() {
        return map;
    }

    public synchronized static void addElementToMap(IPCamera ipCamera, String destination) {
        map.put(ipCamera, destination);
    }

    public synchronized static String getDestination(IPCamera ipCamera) {
        return map.get(ipCamera);
    }

    public synchronized static void mappingToFile() {
        File file = new File("./mapping/ipCamMapping");
        FileOutputStream f;
        ObjectOutputStream s = null;
        try {
            f = new FileOutputStream(file);
            s = new ObjectOutputStream(f);
            s.writeObject(map);
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
        File file = new File("./mapping/ipCamMapping");
        FileInputStream f;
        ObjectInputStream s;
        try {
            f = new FileInputStream(file);
            if(file.length() > 0){
                s = new ObjectInputStream(f);
                map = (HashMap<IPCamera, String>) s.readObject();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
