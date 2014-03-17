package lv.rtu.streaming.audio;

import lv.rtu.domain.AudioUtils;
import lv.rtu.domain.NameGenerator;
import lv.rtu.maping.Mapping;
import lv.rtu.recognition.RecognitionEngine;
import org.xiph.speex.SpeexDecoder;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class AudioStreaming implements Runnable {

    private int portServer;
    private int portClient;

    public AudioStreaming(int portServer, int portClient) {
        this.portServer = portServer;
        this.portClient = portClient;
    }

    @Override
    public void run() {
        try {

            DatagramSocket dsocket = new DatagramSocket(portServer);
            DatagramSocket ssocket = new DatagramSocket();

            byte[] buffer = new byte[1024];

            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);

            SpeexDecoder speexDecoder = new SpeexDecoder();
            speexDecoder.init(0, 8000, 1, false);

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
                if (storeBuffer.position() > 114000) {
                    String host = Mapping.getDestination(packet.getAddress().toString().substring(1));
                    System.out.println("Client IP : " + packet.getAddress().toString());

                    AudioInputStream stream = AudioUtils.soundBytesToAudio(storeBuffer.array());
                    storeBuffer.clear();

                    String fileName = "./resources/tmp/" + NameGenerator.getName() + ".wav";

                    AudioUtils.saveAudioStreamToFile(stream, fileName);
                    stream.close();

                    String[] result = RecognitionEngine.recogniseAudio(fileName);

                    ssocket.send(new DatagramPacket(result[0].getBytes(), result[0].getBytes().length,
                            InetAddress.getByName(host), portClient));
                }
            } while (arrayLength > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
