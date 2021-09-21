package it.lh.dao;

import it.lh.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 操作用户的user对象接口
 */
public interface UserDao {
    /**
     * 查找所有的user返回
     * @return list
     */
    public List<User> findAll();

    /**
     * 登陆方法
     * @return
     */
    public User findUserByPasswordAndUsername(String username,String password);

    /**
     * 插入user
     * 实现注册功能或者添加功能
     * @return  插入是否成功
     */
    boolean registerUser(User user);
    boolean deleteUserById(int id);
    /**
     * 查找对应id的user
     * @param id
     * @return
     */
    User findUserById(int id);
    /**
     * 修改user信息
     * @param user 需要的参数
     */
    void updateUser(User user);
    /**
     *
     * @param start 查询的数据库开始索引
     * @param row 每行显示的记录数
     * @param parameterMap
     * @return List<User>
     */
    List<User> findUserByPage(int start, int row, Map<String, String[]> parameterMap);

    /**
     * 查询数据库总的user数据
     * @return
     * @param parameterMap
     */
    int findTotalCount(Map<String, String[]> parameterMap);
}
