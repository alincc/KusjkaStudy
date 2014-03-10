package lv.rtu.recognition;

import lv.rtu.recognition.audio.AudioRecognitionEngine;
import lv.rtu.recognition.video.VideoRecognitionEngine;

import java.awt.image.BufferedImage;

public class RecognitionEngine {

    public synchronized static void trainRecognizers(){
        AudioRecognitionEngine.configure();
        AudioRecognitionEngine.trainFolder("./resources/data/audio/training");
        VideoRecognitionEngine.trainRecognizer("./resources/data/images/training");
    }

    public synchronized static String recogniseImage(BufferedImage image){

        String result = VideoRecognitionEngine.recognise(image);
        if(result != null){
           return result;
        }else{
            return "This user wasn't found";
        }
    }
}
