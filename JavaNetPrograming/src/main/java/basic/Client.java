package basic;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(Inet4Address.getLocalHost(),12345);
        socket.connect(socketAddress);

        System.out.println("客服端发起连接");

        System.out.println("客户端地址：" + socket.getLocalAddress().getHostAddress() + " 端口：" + socket.getLocalPort());
        System.out.println("服务器地址：" + socket.getInetAddress().getHostAddress() + " 端口：" + socket.getPort());

        //获取键盘输入流
        InputStream in = System.in;
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(in));


        //socket 输出流
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        //socket 输入流
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        while (true) {
            String str = keyboardReader.readLine();
            System.out.println("键盘输入 " + str);
            writer.write(str + "\n");
            writer.flush();
            String echo = reader.readLine();

            System.out.println("客户端收到 " + echo);

            if ("bye".equalsIgnoreCase(echo)) {
                System.out.println("断开连接");
                keyboardReader.close();
                writer.close();
                reader.close();
                break;
            }
        }

        socket.close();
    }
}
