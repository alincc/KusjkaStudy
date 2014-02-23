package lv.rtu.server.file_handler;

import lv.rtu.server.system_exception.FileTransferException;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class FileTransferConfiguration {

    private String fileType = null;
    private String usageType = null;
    private String storageType = null;
    private String fileName = null;
    private String extention = null;

    @SuppressWarnings("deprecation")
    public TransmittedFile incomingFileConfig(Socket clientSocket, DataInputStream is,
                                              PrintStream os) throws FileTransferException {

        try {

            storageType = is.readLine();
            fileType = is.readLine();
            usageType = null;

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Storage :" + storageType + " | File Type :" + fileType);

        if (storageType.equals(FileConstants.STADLY_DATA)) {
            try {
                usageType = is.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        StringBuilder filePath = new StringBuilder();
        filePath.append("./resources");

        if (storageType.equals(FileConstants.TEMPORARY_DATA))
            filePath.append("/tmp");
        else if (storageType.equals(FileConstants.STADLY_DATA))
            filePath.append("/data");
        else {
            os.print("Error code : 1 (Storage type does not match)");
            throw new FileTransferException(
                    "Incorect Storage type. In socket :  "
                            + clientSocket.getRemoteSocketAddress().toString());
        }

        if (fileType.equals(FileConstants.IMAGE_DATA)) {
            filePath.append("/images");
            extention = ".jpg";
        } else if (fileType.equals(FileConstants.AUDIO_DATA)) {
            filePath.append("/audio");
            extention = ".wav";
        } else {
            os.print("Error code : 2 (File type does not match)");
            throw new FileTransferException("Incorect File type. In socket :  "
                    + clientSocket.getRemoteSocketAddress().toString());
        }

        if (usageType != null) {
            if (fileType.equals(FileConstants.TRAINING_DATA))
                filePath.append("/training");
            else if (fileType.equals(FileConstants.TESTING_DATA))
                filePath.append("/testing");
            else {
                os.print("Error code : 3 (Usage type does not match)");
                throw new FileTransferException(
                        "Incorrect Usage type. In socket :  "
                                + clientSocket.getRemoteSocketAddress()
                                .toString());
            }
        }

        try {
            fileName = is.readLine();
            System.out.println("Recived name : " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new TransmittedFile(fileType, usageType, storageType, filePath + fileName + extention, fileName + extention);
    }

}
