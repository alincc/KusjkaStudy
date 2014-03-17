package lv.rtu.file_processing;

import lv.rtu.domain.ObjectFile;

public class FileProcessing implements Runnable {

    ObjectFile objectFile;

    public FileProcessing(ObjectFile objectFile) {
        this.objectFile = objectFile;
    }

    @Override
    public void run() {

        if (objectFile.getData() != null){

            //RecognitionEngine.recognize(objectFile)

        } else {

            //StorageEngine.store(objectFile)

        }
    }
}
