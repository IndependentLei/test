package com.shebao.test.netty.study.nio.test01;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel {
    public static void main(String[] args) throws Exception {
//        write();
//        read();
//        readAndWrite();
        transferFrom();
    }

    public static void write(){
        String str = "测试NIO";
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("F:\\test\\11.txt","rw")){
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(str.getBytes());
            byteBuffer.flip();
            channel.write(byteBuffer);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void read(){
        try (FileInputStream is = new FileInputStream("F:\\test\\11.txt")){
            FileChannel channel = is.getChannel();
            ByteBuffer allocate = ByteBuffer.allocate(is.available());
            channel.read(allocate);
            System.out.println(new String(allocate.array()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void readAndWrite() throws Exception {
        FileInputStream fis = new FileInputStream("F:\\test\\11.txt");
        FileOutputStream fos = new FileOutputStream("F:\\test\\22.txt");
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        while (true) {
            byteBuffer.clear(); // 清空 缓冲区
            int read = fisChannel.read(byteBuffer);
            if (-1 == read) {
                break;
            }
            byteBuffer.flip();
            fosChannel.write(byteBuffer);
        }
        fis.close();
        fos.close();
    }

    private static void transferFrom() throws Exception{
        FileInputStream fis = new FileInputStream("F:\\test\\11.txt");
        FileOutputStream fos = new FileOutputStream("F:\\test\\33.txt");
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();
        fosChannel.transferFrom(fisChannel,0,fosChannel.size());
        fisChannel.close();
        fosChannel.close();
        fos.close();
        fis.close();
    }
}
