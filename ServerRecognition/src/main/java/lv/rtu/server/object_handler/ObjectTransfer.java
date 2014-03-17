package lv.rtu.server.object_handler;

import lv.rtu.domain.ObjectFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


public class ObjectTransfer {

    public ObjectFile receiveFile(ObjectInputStream inStream) {

        ObjectFile file = null;

        try {
            file = (ObjectFile) inStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return file;
    }

}
