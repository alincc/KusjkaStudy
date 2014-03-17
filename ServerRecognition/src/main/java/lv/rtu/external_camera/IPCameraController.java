package lv.rtu.external_camera;

import lv.rtu.maping.IPCamMapping;
import lv.rtu.recognition.RecognitionEngine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;

public class IPCameraController implements Runnable {

    private IPCapture cam;
    private IPCamera ipCamera;
    private volatile boolean keepAlive;

    IPCameraController(IPCamera ipCamera) {
        this.ipCamera = ipCamera;
    }

    void setup() {
        keepAlive = true;
        cam = new IPCapture(ipCamera.getLink(), ipCamera.getLogin(), ipCamera.getPassword());
        cam.start();
    }

    BufferedImage getImage() {
        if (cam.isAvailable()) {
            return cam.read();
        }
        return null;
    }

    void stop() {
        keepAlive = false;
    }

    @Override
    public void run() {
        try {
            setup();
            DatagramSocket ssocket = new DatagramSocket();
            String[] data = IPCamMapping.getDestination(ipCamera).split("[ .,?!]+");
            String host = data[0];
            Integer portClient = Integer.parseInt(data[1]);
            BufferedImage image;
            while (keepAlive) {
                image = getImage();
                if (image != null) {
                    String result = RecognitionEngine.recogniseImage(image);
                    ssocket.send(new DatagramPacket(result.getBytes(), result.getBytes().length,
                            InetAddress.getByName(host), portClient));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
