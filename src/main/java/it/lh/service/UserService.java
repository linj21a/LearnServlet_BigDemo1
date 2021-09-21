package it.lh.service;

import it.lh.domain.PageBean;
import it.lh.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {
    /**
     * 查找所有的用户信息
     * @return 返回一个list
     */
    public List<User> findAll();

    /**
     * 登陆方法
     * @param user 登陆对象
     * @return 存在则返回，不存在在为null
     */
    public User login(User user);
    public boolean addUser(User user);

    /**
     * 删除一个user
     * @param id 需要传入的参数
     * @return  是否删除成功
     */
    public boolean  deleteUser(String id);

    User findUserById(String id);

    void updateUser(User user);

    void deleteUsers(String[] uids);

    /**
     * 根据当前行和页码查找对应的数据
     * @param currentPage 用户点击的当前的页
     * @param rows 当前的行
     * @param parameterMap
     * @return PageBean
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> parameterMap);
}
