package lv.rtu.streaming.video;

public class VideoStreaming implements Runnable {

    private int port;

    public VideoStreaming(int port) {
        this.port = port;
    }

    @Override
    public void run() {
       /*
        try {

            // Create a socket to listen on the port.
            DatagramSocket dsocket = new DatagramSocket(port);
            DatagramSocket ssocket = new DatagramSocket();
            // Create a buffer to read datagrams into. If a
            // packet is larger than this buffer, the
            // excess will simply be discarded!
            byte[] buffer = new byte[10048];

            // Create a packet to receive data into the buffer
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // Now loop forever, waiting to receive packets and printing them.
            while (true) {
                // Wait to receive a datagram
                dsocket.receive(packet);
                ssocket.send(new DatagramPacket(packet.getData(), packet
                        .getData().length, InetAddress.getByName("localhost"),
                        port));


           // * InputStream in = new ByteArrayInputStream(packet.getData());
		   // * BufferedImage bImageFromConvert = ImageIO.read(in);
		   // *
			//* ImageIO.write(bImageFromConvert, "jpg", new File(
			//* "C:/gstreamer/new-darksouls.jpg"));
			//*
			//* // Reset the length of the packet before reusing it.
			//* packet.setLength(buffer.length);


            }
        } catch (Exception e) {
            System.err.println(e);
        } */
    }

}

