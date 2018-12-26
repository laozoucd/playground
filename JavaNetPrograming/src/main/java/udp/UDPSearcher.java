package udp;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class UDPSearcher {

    public static final int LISTEN_PORT = 30000;

    public static void main(String[] args) throws IOException, InterruptedException {

        Listener listener = listen();
        sendBroadcast();

        List<Device> devices = listener.getDevicesAndClose();

        for (Device device : devices) {
            System.out.println("Device " + device.toString());
        }
    }


    private static void sendBroadcast() {

        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();

            String sendData = MessageCreator.buildWithPort(LISTEN_PORT);
            byte[] sendDataBytes = sendData.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendDataBytes,
                    sendDataBytes.length);

            sendPacket.setAddress(InetAddress.getByName("255.255.255.255"));
            sendPacket.setPort(20000);

            ds.send(sendPacket);


            System.out.println("发送广播已完成");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ds.close();
        }


    }

    private static Listener listen() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT,countDownLatch);
        listener.start();
        countDownLatch.await();
        return listener;
    }


    private static class Device {
        final int port;
        final String ip;
        final String sn;

        public Device(int port, String ip, String sn) {
            this.port = port;
            this.ip = ip;
            this.sn = sn;
        }
    }

    private static final class Listener extends Thread {
        private final int listenPort;
        private final CountDownLatch countDownLatch;
        private final List<Device> devices = new ArrayList<>();
        private boolean done = false;
        private DatagramSocket ds = null;

        public Listener(int listenPort, CountDownLatch countDownLatch) {
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();

            countDownLatch.countDown();

            try {

                ds = new DatagramSocket(listenPort);
                while (!done) {
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf,buf.length);

                    ds.receive(receivePack);

                    int dataLen = receivePack.getLength();
                    String data =  new String(receivePack.getData(),0,dataLen);

                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    String sn = MessageCreator.parseSn(data);

                    if (sn != null) {
                       Device device = new Device(port,ip,sn);
                       devices.add(device);
                    }
                }

            } catch (Exception e) {

            } finally {
                close();
            }

        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        List<Device> getDevicesAndClose() {
            done = true;
            close();
            return devices;
        }
    }
}
