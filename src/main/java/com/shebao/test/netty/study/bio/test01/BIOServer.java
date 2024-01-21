package com.shebao.test.netty.study.bio.test01;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    private static final Integer BYTE = 1024;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try (ServerSocket serverSocket = new ServerSocket(6666)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("有客服端连接");
                executorService.execute(() -> handler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[BYTE];
            InputStream is = socket.getInputStream();
            while (true) {
                int read = is.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("关闭socket连接");
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
