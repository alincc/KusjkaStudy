package lv.rtu.domain;

import java.io.Serializable;
import java.util.Arrays;

public class ObjectFile implements Serializable {

    private String message;
    private String fileName;
    private byte[] fileBytes;
    private User user;

    public ObjectFile(String message) {
        this.message = message;
    }

    public ObjectFile(String message, String fileName, byte[] fileBytes, User user) {
        this.message = message;
        this.fileName = fileName;
        this.fileBytes = fileBytes;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!(o instanceof ObjectFile))
            return false;

        ObjectFile objectFile = (ObjectFile) o;

        if (message != null ? !message.equals(objectFile.getMessage())
                : objectFile.getMessage() != null)
            return false;
        if (fileName != null ? !fileName.equals(objectFile.getFileName())
                : objectFile.getFileName() != null)
            return false;
        if (fileBytes != null ? !Arrays.equals(fileBytes,
                objectFile.getFileBytes()) : objectFile.getFileBytes() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (fileBytes != null ? fileBytes.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        if(user != null)
        return "ObjectFile{ Message= " + message + '\'' + ", fileName='"
                + fileName + '\'' + ", fileBytesLengths='" + fileBytes + '\''
                + "User : " + user.getId() + '}';
        else
            return "ObjectFile{ Message= " + message + '\'' + ", fileName='"
                    + fileName + '\'' + ", fileBytesLengths='" + fileBytes + '}';
    }
}
