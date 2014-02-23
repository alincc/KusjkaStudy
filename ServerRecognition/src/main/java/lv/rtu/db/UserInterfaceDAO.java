package lv.rtu.db;

import lv.rtu.domain.User;

interface UserInterfaceDAO {
    public Object insert(User user);
    public Object select(Long id);
    public Object findUserByImageFileName(String string);
    public Object findUserByAudioFileName(String string);
    public Object update(User user);
    public Object delete(Long id);
}
