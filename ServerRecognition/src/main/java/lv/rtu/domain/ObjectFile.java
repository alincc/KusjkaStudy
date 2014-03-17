package lv.rtu.domain;

import java.io.Serializable;
import java.util.Arrays;

public class ObjectFile implements Serializable {

    private String message;
    private String data;
    private byte[] fileBytes;
    private User user;

    public ObjectFile(String message) {
        this.message = message;
    }

    public ObjectFile(String message, String data, byte[] fileBytes, User user) {
        this.message = message;
        this.data = data;
        this.fileBytes = fileBytes;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
        if (data != null ? !data.equals(objectFile.getData())
                : objectFile.getData() != null)
            return false;
        if (fileBytes != null ? !Arrays.equals(fileBytes,
                objectFile.getFileBytes()) : objectFile.getFileBytes() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (fileBytes != null ? fileBytes.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        if(user != null)
        return "ObjectFile{ Message= " + message + '\'' + ", Data='"
                + data + '\'' + ", fileBytesLengths='" + fileBytes + '\''
                + "User : " + user.getId() + '}';
        else
            return "ObjectFile{ Message= " + message + '\'' + ", Data='"
                    + data + '\'' + ", fileBytesLengths='" + fileBytes + '}';
    }
}
