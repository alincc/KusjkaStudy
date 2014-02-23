package lv.rtu.server.file_handler;

public class TransmittedFile {

    private String fileType = null;
    private String usageType = null;
    private String storageType = null;
    private String fileName = null;
    private String filePath = null;

    public TransmittedFile(String fileType, String usageType, String storageType, String filePath ,  String fileName) {

        this.fileType = fileType;
        this.usageType = usageType;
        this.storageType = storageType;
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
