package org.example;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

public class PictureBuild extends Thread {
    private int size;
    private byte[] data;
    private int even;
    private int part;
    private File file;
    private int real = -1;

    public PictureBuild(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        readFile();
    }

    public int getPart() {
        return part;
    }

    public int getEven() {
        return even;
    }

    public boolean check() {
        if (real == -1) even();
        return even == real;
    }
// even подсчитывает количество единиц в двоичном представлении каждого байта из массива data и вычисляет контрольное число четности,
// которое будет равно 0 или 1 в зависимости от четности общего количества единиц.
    private void even(){
        int countOfOne = 0;
        int binary;
        for (byte b : data) {
            binary = b & 0xFF;
            while (binary != 0){
                countOfOne += binary & 1;
                binary = binary >> 1;
            }
        }
        real = countOfOne % 2;
    }

    private void readFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))){
            size = dis.readInt();
            data = dis.readNBytes(size);
            even = dis.readInt();
            part = dis.readInt();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getData() {
        return data;
    }

    public String getInfo(){
        return "Файл" + file.getName() + ", размер: " + size + " количество считанных символов: " + Arrays.toString(data) + " контрольное число: " + even + " номер части: " + part;
    }
}

