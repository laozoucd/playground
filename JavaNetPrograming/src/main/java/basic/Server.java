package basic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("服务器已启动");


        for (; ; ) {
            Socket client = serverSocket.accept();
            System.out.println("客户端地址：" + client.getInetAddress() + " 端口：" + client.getPort());
            ClientHandler clientHandler = new ClientHandler(client);
            clientHandler.start();
        }
    }


    private static class ClientHandler extends Thread {
        private Socket mSocket;


        public ClientHandler(Socket socket) {
            mSocket = socket;
        }

        @Override
        public void run() {
            super.run();

            try {
                InputStream inputStream = mSocket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                //InputStreamReader reader = new InputStreamReader(inputStream);

                OutputStream outputStream = mSocket.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                while (true) {
                    String str = bufferedReader.readLine();
                    System.out.println("服务器收到 " + str);
                    if (str != null) {
                        bufferedWriter.write(str + "\n");
                        bufferedWriter.flush();
                        if (str.equalsIgnoreCase("bye")) {
                            bufferedReader.close();
                            bufferedWriter.close();
                            break;
                        }
                    }

                }
                mSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
