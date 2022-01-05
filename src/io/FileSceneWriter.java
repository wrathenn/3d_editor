package io;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileSceneWriter {
    private BufferedWriter file;

    public FileSceneWriter(String filepath) throws IOException {
        file = new BufferedWriter(new java.io.FileWriter(filepath));
    }


}
