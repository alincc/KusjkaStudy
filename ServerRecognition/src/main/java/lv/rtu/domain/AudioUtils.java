package lv.rtu.domain;

import javax.sound.sampled.*;
import java.io.*;

public class AudioUtils {

    public static AudioFormat getAudioFormat() {
        float sampleRate = 8000;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEnding = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEnding);
        return format;
    }

    public static byte[] getSoundBytes(String filename) {
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(filename)));
            byte[] bytes = new byte[(int) (audioInputStream.getFrameLength()) * (audioInputStream.getFormat().getFrameSize())];
            audioInputStream.read(bytes);
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getAudioInfo(AudioInputStream audioInputStream)
    {
        AudioFormat audioFormat = audioInputStream.getFormat();
        float sample_rate = audioFormat.getSampleRate();
        System.out.println("sample rate = "+sample_rate);

        //Calculate the length in seconds of the sample
        float T = audioInputStream.getFrameLength() / audioFormat.getFrameRate();
        System.out.println("T = "+T+ " (length of sampled sound in seconds)");

        //Calculate the number of equidistant points in time
        int n = (int) (T * sample_rate) / 2;
        System.out.println("n = "+n+" (number of equidistant points)");

        //Calculate the time interval at each equidistant point
        float h = (T / n);
        System.out.println("h = "+h+" (length of each time interval in seconds)");

        //Determine the original Endian encoding format
        boolean isBigEndian = audioFormat.isBigEndian();

        //this array is the value of the signal at time i*h
        int x[] = new int[n];
    }

    public static AudioInputStream soundBytesToAudio(byte[] soundBytes){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(soundBytes);
        long length = (long)(soundBytes.length / getAudioFormat().getFrameSize());
        AudioInputStream audioInputStreamTemp = new AudioInputStream(byteArrayInputStream, getAudioFormat(), length);
        return audioInputStreamTemp;
    }

    public static void saveAudioStreamToFile(AudioInputStream stream, String filename){
        File file = new File(filename);
        try {
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{

        }
    }

}
