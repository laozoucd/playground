package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.UUID;

public class UDProvider {

    public static void main(String[] args) throws IOException {
        String sn = UUID.randomUUID().toString();

        Provider provider = new Provider(sn);
        provider.start();

        System.in.read();
        provider.exit();
    }

    private static class Provider extends Thread {

        private final String sn;
        private boolean done = false;
        private DatagramSocket ds = null;

        public Provider(String sn) {
            this.sn = sn;
        }

        @Override
        public void run() {
            super.run();

            System.out.println("UDPProvide已启动");

            try {

                ds = new DatagramSocket(20000);

                while (!done) {
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);

                    ds.receive(receivePack);

                    int dataLen = receivePack.getLength();
                    String data = new String(receivePack.getData(), 0, dataLen);

                    int responsePort = MessageCreator.parsePort(data);

                    System.out.println("UDPProvide收到信息:" + data);

                    if (responsePort != -1) {
                        String responseData = MessageCreator.buildWithSn(sn);
                        System.out.println("UDPProvide发送信息:" + responseData);
                        byte[] responseDataBytes = responseData.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes, responseDataBytes.length, receivePack.getAddress(), responsePort);

                        ds.send(responsePacket);
                    }
                }
            } catch (IOException e) {

            } finally {
                close();
            }


            System.out.println("UDPProvide已退出");
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        public void exit() {
            done = true;
            close();
        }
    }
}
