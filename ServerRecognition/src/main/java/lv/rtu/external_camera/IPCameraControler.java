package lv.rtu.external_camera;

import lv.rtu.domain.IPCapture;

public class IPCameraControler {


    IPCapture cam;

    void setup() {
        cam = new IPCapture("http://212.219.113.227/axis-cgi/mjpg/video.cgi", "", "");
        cam.start();
    }

    void draw() {
        if (cam.isAvailable()) {
            cam.read();
        }
    }
}
