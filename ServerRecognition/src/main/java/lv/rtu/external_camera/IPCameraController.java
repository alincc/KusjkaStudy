package lv.rtu.external_camera;

import lv.rtu.recognition.RecognitionEngine;

import java.awt.image.BufferedImage;

public class IPCameraController implements Runnable{

    private IPCapture cam;
    private IPCamera ipCamera;
    private volatile boolean keepAlive;

    IPCameraController( IPCamera ipCamera){
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

    void stop(){
        keepAlive = false;
    }

    @Override
    public void run() {
        setup();
        BufferedImage image;
        while(keepAlive){
            image = getImage();
            if(image != null){
                RecognitionEngine.recogniseImage(image);
            }
        }
    }
}
