package video_recognition_test;

import lv.rtu.db.DataBaseFiller;
import lv.rtu.recognition.RecognitionEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Video_Recognition_Test {
    private final double passLimit = 0.9;
    private Map<Long , String> user = new HashMap<>();

    @Before
    public void setup() {
        DataBaseFiller.fillDB();
        RecognitionEngine.trainRecognizers();

        user.put(13l, "./resources/data/images/testing/aleksandrs-1.png");
        user.put(14l, "./resources/data/images/testing/aihua-1.jpg");
        user.put(15l, "./resources/data/images/testing/alexandrm-1.jpg");
        user.put(16l, "./resources/data/images/testing/emily-1.jpg");
        user.put(17l, "./resources/data/images/testing/jim-1.jpg");
        user.put(18l, "./resources/data/images/testing/ke-1.jpg");
    }

    @After
    public void tearDown() {

    }

    @Test
    public void recognizeByFace() {
        int counter = 0;
        for(Long file : user.keySet()){
            File imgLoc = new File(user.get(file));
            BufferedImage img;
            try {
                img = ImageIO.read(imgLoc);
                if(RecognitionEngine.recogniseImage(img).contains(String.valueOf(file))){
                   counter++;
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                ex.printStackTrace();
                try {
                    throw ex;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        assertThat("Recognition percentage is to low",(user.size()/counter > passLimit), is(true));
    }

}
