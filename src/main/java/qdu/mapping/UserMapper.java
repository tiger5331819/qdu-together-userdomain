package qdu.mapping;

import java.util.List;

import qdu.together.userdomin.dao.User;

public interface UserMapper{
    public User findbyid(int id);
    public int update(User test);
    public int delete(int id);
    public int insert(User test);
    public List<User> find();
}