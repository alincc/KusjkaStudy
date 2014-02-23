package lv.rtu.db;

import lv.rtu.domain.User;

import java.sql.*;

public class UserTableImplementationDAO  extends DBConnector implements UserInterfaceDAO {

    @Override
    public UserTableImplementationDAO insert(User user) {
        Connection connection = connection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO USER_TABLE VALUES(?,?,?,?,?,?)");
            statement.setLong(1, user.getId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getUserSurname());
            statement.setString(4, user.getUserPrivileges());
            statement.setString(5, user.getAudioFilesName());
            statement.setString(6, user.getPhotoFilesName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return this;
    }

    @Override
    public User select(Long id) {
        Connection connection = connection();
        PreparedStatement statement;
        User user = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM USER_TABLE WHERE USER_ID = ?");
            statement.setLong(1, id);
            ResultSet response = statement.executeQuery();
            if (response.next())
                user = new User(id , response.getString("USER_NAME") , response.getString("USER_SURNAME")
                        , response.getString("USER_PRIVILEGES"), response.getString("AUDIO_FILE_NAME")
                        , response.getString("PHOTO_FILE_NAME"));
            response.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return user;
    }

    @Override
    public Object findUserByImageFileName(String string) {
        Connection connection = connection();
        PreparedStatement statement;
        User user = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM USER_TABLE WHERE PHOTO_FILE_NAME LIKE ?");
            statement.setString(1, "%"+string+"%");
            ResultSet response = statement.executeQuery();
            if (response.next())
                user = new User(response.getLong("USER_ID") , response.getString("USER_NAME") , response.getString("USER_SURNAME")
                        , response.getString("USER_PRIVILEGES"), response.getString("AUDIO_FILE_NAME")
                        , response.getString("PHOTO_FILE_NAME"));
            response.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return user;
    }

    @Override
    public Object findUserByAudioFileName(String string) {
        Connection connection = connection();
        PreparedStatement statement;
        User user = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM USER_TABLE WHERE AUDIO_FILE_NAME LIKE ?");
            statement.setString(1, "%"+string+"%");
            ResultSet response = statement.executeQuery();
            if (response.next())
                user = new User(response.getLong("USER_ID") , response.getString("USER_NAME") , response.getString("USER_SURNAME")
                        , response.getString("USER_PRIVILEGES"), response.getString("AUDIO_FILE_NAME")
                        , response.getString("PHOTO_FILE_NAME"));
            response.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return user;
    }

    @Override
    public UserTableImplementationDAO update(User user) {
        Connection connection = connection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("UPDATE USER_TABLE SET USER_NAME = ? , USER_SURNAME = ? , USER_PRIVILEGES = ? ," +
                    "AUDIO_FILE_NAME = ? , PHOTO_FILE_NAME = ?  WHERE USER_ID = ?");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserSurname());
            statement.setString(3, user.getUserPrivileges());
            statement.setString(4, user.getAudioFilesName());
            statement.setString(5, user.getPhotoFilesName());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return this;
    }

    @Override
    public UserTableImplementationDAO delete(Long id) {
        Connection connection = connection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("DELETE FROM USER_TABLE WHERE USER_ID = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return this;
    }


}
