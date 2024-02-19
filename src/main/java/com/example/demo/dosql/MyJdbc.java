//package com.example.demo.dosql;
//
//import com.example.demo.entity.MyEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class MyJdbc {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public List<MyEntity> findall() {
//        String sql = "select * from temp_table2";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MyEntity.class));
//    }
//
//}
