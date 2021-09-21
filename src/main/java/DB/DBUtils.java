package DB;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
    /**
     * 1、加载默认配置，生产dataSource对象
     */

    private static DataSource dataSource;
    static{
        Properties properties = new Properties();
        InputStream resourceAsStream = DBUtils.class.getClassLoader().getResourceAsStream("Druid.property");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
             dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2、配置数据库连接池
     * 获取连接池里面的对象，数据源就是一个池子，里面创建了多个数据库connection对象
     *
     */
    public static DataSource getDataSource(){
        return  dataSource;
    }
    /**
     * 3、获取连接池里面的数据库链接
     *
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(Connection connection){
        try {
            if(connection==null){
                return;
            }else
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
