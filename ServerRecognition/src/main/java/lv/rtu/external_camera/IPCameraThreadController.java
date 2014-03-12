package lv.rtu.external_camera;

import lv.rtu.maping.IPCamMapping;

import java.util.ArrayList;
import java.util.List;

public class IPCameraThreadController {

    private List<IPCameraController> objectPool = new ArrayList<IPCameraController>();

    public void runAllIPCameras(){
        if(!IPCamMapping.getMap().isEmpty())
            for(Object object : IPCamMapping.getMap().keySet()){
                IPCamera ipCamera = (IPCamera)object;
                IPCameraController threadController = new IPCameraController(ipCamera);
                objectPool.add(threadController);
                Thread thread = new Thread(threadController);
                thread.start();
            }
    }

    public void stopAllIPCameras(){
        for(IPCameraController threadController: objectPool){
            threadController.stop();
        }
    }

}
