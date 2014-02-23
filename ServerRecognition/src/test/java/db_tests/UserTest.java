package db_tests;

import lv.rtu.db.DatabaseTools;
import lv.rtu.domain.User;
import lv.rtu.db.UserTableImplementationDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Before
    public void noSetup() {
        DatabaseTools tools = new DatabaseTools();
        tools.clear("server_user");
    }

    @After
    public void noTearDown() {

    }

    @Test
    public void insertTest() {
        UserTableImplementationDAO table = new UserTableImplementationDAO();
        User user = new User(1l ,"Nikita" ,"Copy" ,"user" , "test,test2" ,"test3");
        table.insert(user);
        User dbUser = table.select(user.getId());
        assertThat(" User from Database have to be the same that was inserted ",user.equals(dbUser), is(true));
    }

    @Test
    public void selectTest() {
        UserTableImplementationDAO table = new UserTableImplementationDAO();
        User user = new User(13l ,"Kiril" ,"Obuhov" ,"admin" , null ,null);
        table.insert(user);
        User dbUser = table.select(user.getId());
        assertThat(" User from Database have to be the same that was inserted ",user.equals(dbUser), is(true));
    }

    @Test
    public void selectImageNameTest() {
        UserTableImplementationDAO table = new UserTableImplementationDAO();
        User user = new User(13l ,"Kiril" ,"Obuhov" ,"admin" ,"hi,ho,ni" ,"nom,mon,tom");
        table.insert(user);
        User dbUser = (User)table.findUserByImageFileName("mon");
        System.out.println(dbUser.toString());
        assertThat(" User from Database have to be the same that was inserted ",user.equals(dbUser), is(true));
    }

    @Test
    public void selectAudioNameTest() {
        UserTableImplementationDAO table = new UserTableImplementationDAO();
        User user = new User(13l ,"Kiril" ,"Obuhov" ,"admin" , "hi,ho,ni" ,"nom,mon,tom");
        table.insert(user);
        User dbUser = (User)table.findUserByAudioFileName("ho");
        System.out.println(dbUser.toString());
        assertThat(" User from Database have to be the same that was inserted ",user.equals(dbUser), is(true));
    }


    @Test
    public void updateTest() {
        UserTableImplementationDAO table = new UserTableImplementationDAO();
        User user = new User(13l ,"Kiril" ,"Obuhov" ,"admin" , null ,null);
        table.insert(user);
        user = new User(13l ,"Toma" ,"Tomovec" ,"user" , "test2" , "test1");
        table.update(user);
        User dbUser = table.select(user.getId());
        assertThat(" User from Database have to be the same that was inserted ",user.equals(dbUser), is(true));
    }

    @Test
    public void deleteTest() {
        UserTableImplementationDAO table = new UserTableImplementationDAO();
        User user = new User(13l ,"Kiril" ,"Obuhov" ,"admin" , null ,null);
        table.insert(user);
        table.delete(user.getId());
        User dbUser = table.select(user.getId());
        assertThat(" User from Database have to be the same that was inserted ",dbUser==null, is(true));
    }

}
