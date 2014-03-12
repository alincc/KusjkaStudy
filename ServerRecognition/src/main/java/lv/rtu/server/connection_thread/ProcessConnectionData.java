package lv.rtu.server.connection_thread;

import lv.rtu.db.DataBaseFiller;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.AudioUtils;
import lv.rtu.domain.ObjectFile;
import lv.rtu.maping.Mapping;
import lv.rtu.recognition.RecognitionEngine;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class ProcessConnectionData {

    private UserTableImplementationDAO table = new UserTableImplementationDAO();

    public void objectAnalysis(ObjectFile objectFile) {

        switch (objectFile.getMessage()) {
            case "Transfer File":
                try {

                    if (objectFile.getFileName().contains(".")) {

                        if (objectFile.getFileName().contains("jpg")) {
                            ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                            BufferedImage image = ImageIO.read(byteArrayStream);
                            String fileName = "./resources" + objectFile.getFileName();
                            ImageIO.write(image, "jpg", new File(fileName));
                            byteArrayStream.close();
                        }

                        if (objectFile.getFileName().contains("wav")) {
                            AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                            String fileName = "./resources" + objectFile.getFileName();
                            AudioUtils.saveAudioStreamToFile(stream, fileName);
                            stream.close();
                        }

                    } else {

                        if (objectFile.getFileName().contains("image")) {
                            ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                            BufferedImage image = ImageIO.read(byteArrayStream);
                            RecognitionEngine.recogniseImage(image);
                            System.out.println(objectFile.toString());
                            byteArrayStream.close();
                        }

                        if (objectFile.getFileName().contains("audio")) {
                            Date date = new java.util.Date();
                            String time = new Timestamp(date.getTime()).toString();
                            time = time.replaceAll("[^A-Za-z0-9 ]+", "_");
                            AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                            String fileName = "./resources/tmp/" + time + ".wav";
                            AudioUtils.saveAudioStreamToFile(stream, fileName);
                            stream.close();
                            RecognitionEngine.recogniseAudio(fileName);
                            System.out.println(objectFile.toString());
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
                String[] array = objectFile.getFileName().split("-");
                Mapping.addElementToMap(array[0], array[1]);
                break;

            case "Fill DB":
                System.out.println("Fil DB");
                DataBaseFiller.fillDB();
                break;

            case "Train":
                System.out.println("Train");
                RecognitionEngine.trainRecognizers();
                break;

            case "exit":
                System.out.println("exit");
                break;
        }
    }

}
