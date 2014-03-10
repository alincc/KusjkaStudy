package lv.rtu.server.network_util;


import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class AvailablePortFinder {

    public static final int MIN_PORT_NUMBER = 5000;
    public static final int MAX_PORT_NUMBER = 49151;

    private AvailablePortFinder() {
    }

    public synchronized static Set getAvailablePorts() {
        return getAvailablePorts(MIN_PORT_NUMBER, MAX_PORT_NUMBER);
    }

    public synchronized static int getNextAvailable() {
        return getNextAvailable(MIN_PORT_NUMBER);
    }

    public synchronized static int getNextAvailable(int fromPort) {
        if ((fromPort < MIN_PORT_NUMBER) || (fromPort > MAX_PORT_NUMBER)) {
            throw new IllegalArgumentException("Invalid start port: "
                    + fromPort);
        }

        for (int i = fromPort; i <= MAX_PORT_NUMBER; i++) {
            if (available(i)) {
                return i;
            }
        }

        throw new NoSuchElementException("Could not find an available port "
                + "above " + fromPort);
    }

    public synchronized static boolean available(int port) {
        if ((port < MIN_PORT_NUMBER) || (port > MAX_PORT_NUMBER)) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

    public synchronized static Set getAvailablePorts(int fromPort, int toPort) {
        if (
                (fromPort < MIN_PORT_NUMBER) || (toPort > MAX_PORT_NUMBER)
                        || (fromPort > toPort)) {
            throw new IllegalArgumentException("Invalid port range: "
                    + fromPort + " ~ " + toPort);
        }

        Set result = new TreeSet();

        for (int i = fromPort; i <= toPort; i++) {
            ServerSocket s = null;

            try {
                s = new ServerSocket(i);
                result.add(new Integer(i));
            } catch (IOException e) {
            } finally {
                if (s != null) {
                    try {
                        s.close();
                    } catch (IOException e) {
                        /* should not be thrown */
                    }
                }
            }
        }

        return result;
    }
}
