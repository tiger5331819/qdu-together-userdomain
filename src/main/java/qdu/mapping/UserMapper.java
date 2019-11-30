package qdu.mapping;

import java.util.List;

import qdu.together.userdomain.dao.User;

public interface UserMapper{
    public User findbyid(String id);
    public int update(User user);
    public int delete(String id);
    public int insert(User user);
    public List<User> findAll();
}