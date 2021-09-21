package it.lh.service.impl;

import it.lh.dao.UserDao;
import it.lh.dao.impl.UserDaoImpl;
import it.lh.domain.PageBean;
import it.lh.domain.User;
import it.lh.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();//接口
    @Override
    public List<User> findAll() {
        //调用DAO数据库表映射层的接口来操作数据
        return dao.findAll();//调用
    }

    @Override
    public User login(User user) {
        if(user==null){
            return null;
        }
        return dao.findUserByPasswordAndUsername(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean addUser(User user) {
        return dao.registerUser(user);
    }

    @Override
    public boolean deleteUser(String id) {
        int i = Integer.parseInt(id);
        return dao.deleteUserById(i);
    }

    @Override
    public User findUserById(String id) {
        int i = -1;//不能存在的一个数
        try{
            i = Integer.parseInt(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  dao.findUserById(i);

    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    public void deleteUsers(String[] uids) {
        for(String id:uids){
            int i =  Integer.parseInt(id);
            dao.deleteUserById(i);
        }
    }
    /**
     * 根据当前行和页码查找对应的数据，分页和条件查询
     * @param currentPage 用户点击的当前的页
     * @param rows 当前的行
     * @param parameterMap 查询涉及的参数都在里面
     * @return PageBean
     */
    @Override
    public PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> parameterMap) {
        int cur = Integer.parseInt(currentPage);
        int row = Integer.parseInt(rows);
        System.out.println(cur+" "+row);
//        //移除干扰字段_报错不允许修改锁定的值
//        parameterMap.remove("currentPage");
//        parameterMap.remove("rows");
        //1、创建空的pageBean
        PageBean<User> pb = new PageBean<>();
        pb.setCurrentPage(cur);
        pb.setRow(row);
        //分组查询，参数来控制查到的数量
        int totalCount = dao.findTotalCount(parameterMap);
        pb.setTotalCount(totalCount);

        //计算开始记录的索引
        int start = (cur-1)*row;
        List<User> userByPage = dao.findUserByPage(start, row,parameterMap);
        pb.setList(userByPage);
        //计算总的页码
        int res = totalCount % row;
        int totalPage = res ==0?totalCount/row:totalCount/row+1;
        pb.setTotalPages(totalPage);
        return pb;
    }
}
