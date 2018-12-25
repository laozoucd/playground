package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDProvider {

    public static void main(String[] args) throws IOException {

        DatagramSocket ds = new DatagramSocket(2000);

        final byte[] buf = new byte[512];

        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);

        ds.receive(receivePack);

        //获取发送者信息
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int dataLen = receivePack.getLength();

        String data = new String(receivePack.getData(), 0, dataLen);

        System.out.println("收到数据：" + data);

        String responseDate = data;

        byte[] responseDataBytes = responseDate.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes,
                responseDataBytes.length,
                receivePack.getAddress(),
                receivePack.getPort());

        ds.send(responsePacket);

        System.out.println("程序结束：");

        ds.close();

    }
}
