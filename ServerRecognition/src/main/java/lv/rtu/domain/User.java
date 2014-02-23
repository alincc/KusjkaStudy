package lv.rtu.domain;

import java.io.Serializable;

public class User  implements Serializable {

    private Long id;
    private String userName;
    private String userSurname;
    private String userPrivileges;
    private String audioFilesName;
    private String photoFilesName;

    public User(){}

    public User(Long id ,String userName ,String userSurname ,String userPrivileges
            ,String audioFilesName ,String photoFilesName){
        this.id = id;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userPrivileges = userPrivileges;
        this.audioFilesName = audioFilesName;
        this.photoFilesName = photoFilesName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserPrivileges() {
        return userPrivileges;
    }

    public void setUserPrivileges(String userPrivileges) {
        this.userPrivileges = userPrivileges;
    }

    public String getAudioFilesName() {
        return audioFilesName;
    }

    public void setAudioFilesName(String audioFilesName) {
        this.audioFilesName = audioFilesName;
    }

    public String getPhotoFilesName() {
        return photoFilesName;
    }

    public void setPhotoFilesName(String photoFilesName) {
        this.photoFilesName = photoFilesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!(o instanceof User))
            return false;

        User comparedObject = (User) o;

        if (id != null ? !id.equals(comparedObject.getId()) : comparedObject.getId() != null)
            return false;
        if (userName != null ? !userName.equals(comparedObject.getUserName()) : comparedObject.getUserName() != null)
            return false;
        if (userSurname != null ? !userSurname.equals(comparedObject.getUserSurname()) : comparedObject.getUserSurname() != null)
            return false;
        if (userPrivileges != null ? !userPrivileges.equals(comparedObject.getUserPrivileges()) : comparedObject.getUserPrivileges() != null)
            return false;
        if (audioFilesName != null ? !audioFilesName.equals(comparedObject.getAudioFilesName()) : comparedObject.getAudioFilesName() != null)
            return false;
        if (photoFilesName != null ? !photoFilesName.equals(comparedObject.getPhotoFilesName()) : comparedObject.getPhotoFilesName() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userSurname != null ? userSurname.hashCode() : 0);
        result = 31 * result + (userPrivileges != null ? userPrivileges.hashCode() : 0);
        result = 31 * result + (audioFilesName != null ? audioFilesName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userName='" + userSurname + '\'' +
                ", userPrivileges='" + userPrivileges + '\'' +
                ", audioFilesName='" + audioFilesName + '\'' +
                ", photoFilesName='" + photoFilesName + '\'' +
                '}';
    }

}
