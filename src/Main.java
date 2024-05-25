package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        File f = new File("./v6");
        File[] files = f.listFiles();

        PictureBuild[] parts = new PictureBuild[files.length];
        int index = 0;
        for (File file : files) {
            parts[index] = new PictureBuild(file);
            index++;
        }

        for (PictureBuild p : parts){
            p.start();
        }

        for (PictureBuild p : parts){
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        File resultFile = new File("v6.png");
        Arrays.sort(parts, (o1, o2) -> o1.getPart() - o2.getPart());
        try (FileOutputStream fos = new FileOutputStream(resultFile)) {
            for (PictureBuild p : parts){
                fos.write(p.getData());
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        for (PictureBuild p : parts){
            if (p.check()) System.out.println("Верно");
            else{
                System.out.println("Не верно");
            }
        }

    }

    public static int counter(byte[] arr){
        int countOfOne = 0;
        int binary;
        for (byte b : arr){
            binary = b & 0xFF;
            while (binary != 0) {
                countOfOne += binary & 1;
                binary = binary >> 1;
            }
        }
        return countOfOne % 2;
    }
}
