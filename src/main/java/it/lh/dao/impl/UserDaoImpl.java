package it.lh.dao.impl;

import it.lh.dao.UserDao;
import it.lh.domain.User;
import it.lh.util.DBUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(DBUtils.getDataSource());
    @Override
    public List<User> findAll() {
        //使用JDBC操作数据库，返回对应的数据
        //1、定义sql
        String sql = "select * from user";
        return template.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public User findUserByPasswordAndUsername(String username,String password) {
        String sql = "select * from user where username= ? and password= ?";
        User user;
        try{
             user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        }catch (Exception e){
            e.printStackTrace();
            user = null;
        }
        return user;
    }

    @Override
    public boolean registerUser(User user) {
        if(user==null){
            return  false;
        }
        String sql = "insert into user value(null,?,?,?,?,?,?,null,null)";
        int update = template.update(sql, user.getName(),user.getAge(),
                user.getGender(),user.getEmail(),user.getAddress(),user.getQq());
        return update != 0;

    }

    @Override
    public boolean deleteUserById(int id) {
        String sql = "delete from user where id = ?";
        int update = template.update(sql,id);
        return update!=0;
    }

    @Override
    public User findUserById(int id) {
        String sql = "select * from user where id=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    @Override
    public void updateUser(User user) {
        int id = user.getId();
        String sql = "update user set name=?,age=?,gender=?,email=?,qq=?,address=? where id=?";
        template.update(sql,user.getName(),user.getAge(),user.getGender(),user.getEmail(),user.getQq(),user.getAddress(),user.getId());
    }

    @Override
    public List<User> findUserByPage(int start, int row, Map<String, String[]> parameterMap) {
        String sql = "select * from user  where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = parameterMap.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {

            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }

            //获取value
            String value = parameterMap.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }

        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(row);
        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);

        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }

    @Override
    public int findTotalCount(Map<String, String[]> parameterMap) {
        //1、定义一个模板sql
        String sql = "select count(*) from user where 1 = 1 ";
        //2、遍历map
        Set<String> strings = parameterMap.keySet();
        StringBuilder sb = new StringBuilder(sql);
        List<Object> keys = new ArrayList<>();
        for(String key:strings){
            if(!key.equals("currentPage") && !key.equals("rows")){
                String value = parameterMap.get(key)[0];//只有一个值
                //判断value是否有效
                if(value!=null && !"".equals(value)){
                    //拼接sql
                    sb.append(" and ").append(key).append(" like ?");//明显存在sql注入问题
                    keys.add("%"+value+"%");//?的值
                }
            }

        }
        System.out.println(sb);
        for(Object o:keys){
            System.out.println(o);
        }
        Integer res = template.queryForObject(sb.toString(),Integer.class,keys.toArray());
        return res==null?0:res;
    }

}
