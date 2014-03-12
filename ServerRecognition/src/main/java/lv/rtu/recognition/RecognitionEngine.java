package lv.rtu.recognition;

import lv.rtu.recognition.audio.AudioRecognitionEngine;
import lv.rtu.recognition.video.VideoRecognitionEngine;
import marf.util.MARFException;

import java.awt.image.BufferedImage;

public class RecognitionEngine {

    private static AudioRecognitionEngine audioRecognizer;
    private static VideoRecognitionEngine videoRecognizer;

    public synchronized static void trainRecognizers(){
        audioRecognizer = new AudioRecognitionEngine();
        videoRecognizer = new VideoRecognitionEngine();
        audioRecognizer.configure();
        audioRecognizer.trainFolder("./resources/data/audio/training");
        videoRecognizer.trainRecognizer("./resources/data/images/training");
    }

    public synchronized static String recogniseImage(BufferedImage image){
        if(videoRecognizer != null){
            String result = videoRecognizer.recognise(image);
            if(result != null){
               return result;
            }else{
                return "This user wasn't found";
            }
        }
        return "Null recognition object";
    }

    public synchronized static String[] recogniseAudio(String fileName){
        if(audioRecognizer != null){
            try {
                return audioRecognizer.ident(fileName);
            } catch (MARFException e) {
                e.printStackTrace();
            }
            return new String[] {"This user wasn't found"};
        }
        return new String[] {"Null recognition object"};
    }
}
