package lv.rtu.streaming.audio;

import lv.rtu.domain.AudioUtils;
import lv.rtu.maping.Mapping;
import lv.rtu.recognition.audio.AudioRecognitionEngine;
import marf.util.MARFException;
import org.xiph.speex.SpeexDecoder;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Date;

public class AudioStreaming implements Runnable {

    private int portServer;
    private int portClient;

    public AudioStreaming(int portServer, int portClient) {
        this.portServer = portServer;
        this.portClient = portClient;
    }

    @Override
    public void run() {
        DatagramSocket dsocket = null;
        DatagramSocket ssocket = null;
        try {
            dsocket = new DatagramSocket(portServer);
            ssocket = new DatagramSocket();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        byte[] buffer = new byte[1024];

        DatagramPacket packet = new DatagramPacket(buffer,
                buffer.length);

        SpeexDecoder speexDecoder = new SpeexDecoder();
        speexDecoder.init(0, 8000, 1, false);

        try {
            ByteBuffer storeBuffer = ByteBuffer.allocate(115000);
            int arrayLength;
            do {
                dsocket.receive(packet);
                arrayLength = packet.getLength();
                speexDecoder.processData(packet.getData(), 0, packet.getData().length);
                byte data[] = new byte[speexDecoder.getProcessedDataByteSize()];
                speexDecoder.getProcessedData(data, 0);
                System.out.println("Data length: " + data.length);
                storeBuffer.put(data);
                if(storeBuffer.position() > 114000){
                    String host = Mapping.getDestination(packet.getAddress().toString().substring(1));
                    System.out.println("Client IP : " + packet.getAddress().toString());
                    Date date = new java.util.Date();
                    String time = new Timestamp(date.getTime()).toString();
                    time = time.replaceAll("[^A-Za-z0-9 ]+", "_");
                    AudioInputStream stream = AudioUtils.soundBytesToAudio(storeBuffer.array());
                    storeBuffer.clear();
                    String fileName = "./resources/tmp/" + time + ".wav";
                    AudioUtils.saveAudioStreamToFile(stream, fileName);
                    stream.close();
                    String[] result = AudioRecognitionEngine.ident(fileName);
                    ssocket.send(new DatagramPacket(result[0].getBytes(), result[0].getBytes().length,
                            InetAddress.getByName(host),portClient));
                }
            } while (arrayLength > 0);
        } catch (IOException e) {
            System.err.println("I/O problems: " + e);
            System.exit(-3);
        } catch (MARFException e) {
            e.printStackTrace();
        }
    }
}
