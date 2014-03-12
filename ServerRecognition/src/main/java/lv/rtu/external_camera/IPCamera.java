package lv.rtu.external_camera;

public class IPCamera {

    private String link;
    private String login;
    private String password;

    public IPCamera() {

    }

    public IPCamera(String link, String login, String password) {
        this.link = link;
        this.login = login;
        this.password = password;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
