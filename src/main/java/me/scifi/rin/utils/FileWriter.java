package me.scifi.rin.utils;

import lombok.SneakyThrows;
import me.scifi.rin.Rin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWriter {

    private final File file;

    @SneakyThrows
    public FileWriter() {
        this.file = new File(Rin.getDirectory() + "/links.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

    }

    public void write(List<String> list) {
        try {

            java.io.FileWriter fileWriter = new java.io.FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(String string : list) {
                bufferedWriter.write(string);
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
