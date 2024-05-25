package org.example;

import java.io.*;
import java.util.Arrays;

public class PaintPartThread extends Thread{
    private int size;
    private byte[] data;
    private int even;
    private int part;
    private File file;
    private int really = -1;

    public PaintPartThread(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        readFromFile();
    }

    public int getPart() {
        return part;
    }

    public int getEven() {
        return even;
    }

    public boolean check(){
        if (really == -1) counterEven();
        return even == really;
    }

    private void counterEven(){
        int countOfOne = 0;
        int binary;
        for (byte b : data){
            binary = b & 0xFF;
            while (binary != 0){
                countOfOne += binary & 1;
                binary = binary >> 1;
            }
        }
        really = countOfOne % 2;
    }

    private void readFromFile(){
        try(DataInputStream dis = new DataInputStream(new FileInputStream(file))){
            size = dis.readInt();
            data = dis.readNBytes(size);
            even = dis.readInt();
            part = dis.readInt();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public byte[] getData() {
        return data;
    }

    public String getInfo(){
        return "прочитали файл "
                + file.getName() + " кол-во байт данных : " + size +
                " кол-во считанных символов: " + Arrays.toString(data) +
                " контрольное число: " + even +
                " номер части: " + part;
    }
}
