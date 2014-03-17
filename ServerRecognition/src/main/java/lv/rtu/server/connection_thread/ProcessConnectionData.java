package lv.rtu.server.connection_thread;

import lv.rtu.db.DataBaseFiller;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.AudioUtils;
import lv.rtu.domain.NameGenerator;
import lv.rtu.domain.ObjectFile;
import lv.rtu.external_camera.IPCamera;
import lv.rtu.maping.IPCamMapping;
import lv.rtu.maping.Mapping;
import lv.rtu.recognition.RecognitionEngine;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

public class ProcessConnectionData {

    private UserTableImplementationDAO table = new UserTableImplementationDAO();

    public void objectAnalysis(ObjectFile objectFile) {

        switch (objectFile.getMessage()) {
            case "Transfer File":
                try {
                    if (objectFile.getData().contains(".")) {

                        if (objectFile.getData().contains("jpg")) {
                            ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                            BufferedImage image = ImageIO.read(byteArrayStream);
                            String fileName = "./resources" + objectFile.getData();
                            ImageIO.write(image, "jpg", new File(fileName));
                            byteArrayStream.close();
                        }

                        if (objectFile.getData().contains("wav")) {
                            AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                            String fileName = "./resources" + objectFile.getData();
                            AudioUtils.saveAudioStreamToFile(stream, fileName);
                            stream.close();
                        }

                    } else {

                        if (objectFile.getData().contains("image")) {
                            ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                            BufferedImage image = ImageIO.read(byteArrayStream);
                            RecognitionEngine.recogniseImage(image);
                            System.out.println(objectFile.toString());
                            byteArrayStream.close();
                        }

                        if (objectFile.getData().contains("audio")) {
                            AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                            String fileName = "./resources/tmp/" + NameGenerator.getName() + ".wav";
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
                String[] array = objectFile.getData().split("-");
                Mapping.addElementToMap(array[0], array[1]);
                break;

            case "Set IP Camera Mapping":
                String[] configData = objectFile.getData().split("-");
                IPCamera camera = new IPCamera(configData[0], configData[1], configData[2]);
                IPCamMapping.addElementToMap(camera, configData[3]);
                break;

            case "Fill DB":
                System.out.println("Fill DB");
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
