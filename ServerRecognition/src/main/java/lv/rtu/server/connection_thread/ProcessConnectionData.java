package lv.rtu.server.connection_thread;

import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.ObjectFile;
import lv.rtu.recognition.video.VideoRecognitionEngine;
import lv.rtu.server.network_util.AvailablePortFinder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class ProcessConnectionData {

    private static UserTableImplementationDAO table = new UserTableImplementationDAO();

    public static void objectAnalysis(ObjectFile objectFile) {

        switch (objectFile.getMessage()) {
            case "Transfer File":
                try {
                    ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                    BufferedImage image = ImageIO.read(byteArrayStream);
                    VideoRecognitionEngine.recognise(image);
                    System.out.println(objectFile.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //FileProcessing fileEngine = new FileProcessing(objectFile);
                //fileEngine.run();
                break;

            case "Video Stream":
                int videoPortNumber = AvailablePortFinder.getNextAvailable();
                //VideoEngine
                break;

            case "Audio Stream":
                int audioPortNumber = AvailablePortFinder.getNextAvailable();
                // AudioEngine
                break;

            case "Combined Stream":
                break;

            case "Add User":
                table.insert(objectFile.getUser());
                break;

            case "Update User":
                table.update(objectFile.getUser());
                break;

            case "Delete User":
                table.delete(objectFile.getUser().getId());
                break;

            case "Set Mapping":
                // Change mapping config
                break;

            case "exit":
                System.out.println("exit");

                break;
        }
    }

}
