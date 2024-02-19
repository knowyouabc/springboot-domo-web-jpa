//package com.example.demo.dosql;
//
//import com.example.demo.entity.MyEntity;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//@Service
//public class MyHikari {
//
//    @Autowired
//    private HikariDataSource dataSource;
//
//    public void findall() throws SQLException {
//        Connection connection = dataSource.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from temp_table2");
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        resultSet.getString("name");
//    }
//
//}
