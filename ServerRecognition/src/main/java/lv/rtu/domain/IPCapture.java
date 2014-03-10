package lv.rtu.domain;

import ipcapture.Base64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IPCapture implements Runnable {

    private String urlString, user, pass;
    private byte[] curFrame;
    private boolean frameStarted;
    private boolean frameAvailable;
    private Thread streamReader;
    private HttpURLConnection conn;
    private BufferedInputStream httpIn;
    private ByteArrayOutputStream jpgOut;
    private volatile boolean keepAlive;

    public IPCapture(String urlString, String user, String pass) {
        super();
        this.urlString = urlString;
        this.user = user;
        this.pass = pass;
        this.curFrame = new byte[0];
        this.frameStarted = false;
        this.frameAvailable = false;
        this.keepAlive = false;
    }

    public boolean isAlive() {
        return streamReader.isAlive();
    }

    public boolean isAvailable() {
        return frameAvailable;
    }

    public void start() {
        if (streamReader != null && streamReader.isAlive()) {
            System.out.println("Camera already started");
            return;
        }
        streamReader = new Thread(this, "HTTP Stream reader");
        keepAlive = true;
        streamReader.start();
    }

    public void start(String urlString, String user, String pass) {
        this.urlString = urlString;
        this.user = user;
        this.pass = pass;
        this.start();
    }

    public void stop() {
        if (streamReader == null || !streamReader.isAlive()) {
            System.out.println("Camera already stopped");
            return;
        }
        keepAlive = false;
        try {
            streamReader.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public void dispose() {
        stop();
    }

    public void run() {
        URL url;
        Base64Encoder base64 = new Base64Encoder();

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL");
            return;
        }

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + base64.encode(user + ":" + pass));
        } catch (IOException e) {
            System.err.println("Unable to connect: " + e.getMessage());
            return;
        }
        try {
            httpIn = new BufferedInputStream(conn.getInputStream(), 8192);
            jpgOut = new ByteArrayOutputStream(8192);
        } catch (IOException e) {
            System.err.println("Unable to open I/O streams: " + e.getMessage());
            return;
        }

        int prev = 0;
        int cur = 0;

        try {
            while (keepAlive && (cur = httpIn.read()) >= 0) {
                if (prev == 0xFF && cur == 0xD8) {
                    frameStarted = true;
                    jpgOut.close();
                    jpgOut = new ByteArrayOutputStream(8192);
                    jpgOut.write((byte) prev);
                }
                if (frameStarted) {
                    jpgOut.write((byte) cur);
                    if (prev == 0xFF && cur == 0xD9) {
                        curFrame = jpgOut.toByteArray();
                        frameStarted = false;
                        frameAvailable = true;
                    }
                }
                prev = cur;
            }
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
        try {
            jpgOut.close();
            httpIn.close();
        } catch (IOException e) {
            System.err.println("Error closing I/O streams: " + e.getMessage());
        }
        conn.disconnect();
    }

    public void read() {
        ByteArrayInputStream jpgIn;
        BufferedImage bufImg;
        try {
            jpgIn = new ByteArrayInputStream(curFrame);
            bufImg = ImageIO.read(jpgIn);
            jpgIn.close();
        } catch (IOException e) {
            System.err.println("Error acquiring the frame: " + e.getMessage());
            frameAvailable = false;
            return;
        }

        frameAvailable = false;
    }
}