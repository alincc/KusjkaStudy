package lv.rtu.server.connection_thread;

import lv.rtu.db.DataBaseFiller;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.AudioUtils;
import lv.rtu.domain.ObjectFile;
import lv.rtu.recognition.RecognitionEngine;
import lv.rtu.recognition.audio.AudioRecognitionEngine;
import lv.rtu.recognition.video.VideoRecognitionEngine;
import lv.rtu.server.network_util.AvailablePortFinder;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class ProcessConnectionData {

    private static UserTableImplementationDAO table = new UserTableImplementationDAO();

    public static void objectAnalysis(ObjectFile objectFile) {

        switch (objectFile.getMessage()) {
            case "Transfer File":
                try {

                    if(objectFile.getFileName().contains(".")){

                        if(objectFile.getFileName().contains("jpg")){
                            ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                            BufferedImage image = ImageIO.read(byteArrayStream);
                            String fileName = "./resources"+objectFile.getFileName();
                            ImageIO.write(image, "jpg", new File(fileName));
                            byteArrayStream.close();
                        }

                        if(objectFile.getFileName().contains("wav")){
                            AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                            String fileName = "./resources"+objectFile.getFileName();
                            AudioUtils.saveAudioStreamToFile(stream, fileName);
                            stream.close();
                        }

                    } else {

                        if(objectFile.getFileName().contains("image")){
                            ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                            BufferedImage image = ImageIO.read(byteArrayStream);
                            VideoRecognitionEngine.recognise(image);
                            System.out.println(objectFile.toString());
                            byteArrayStream.close();
                        }

                        if(objectFile.getFileName().contains("audio")){
                            Date date= new java.util.Date();
                            String time = new Timestamp(date.getTime()).toString();
                            time = time.replaceAll("[^A-Za-z0-9 ]+","_");
                            AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                            String fileName = "./resources/tmp/"+time+".wav";
                            AudioUtils.saveAudioStreamToFile(stream, fileName);
                            stream.close();
                            AudioRecognitionEngine.ident(fileName);
                            System.out.println(objectFile.toString());
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

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