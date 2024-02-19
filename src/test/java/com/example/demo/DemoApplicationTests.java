//package com.example.demo;
//
//import com.example.demo.dosql.MyHikari;
//import com.example.demo.dosql.MyJdbc;
//import com.example.demo.entity.TempTable2;
//import com.example.demo.jpa.TempTable2Repository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.sql.SQLException;
//
//@SpringBootTest
//class DemoApplicationTests {
//
//	@Autowired
//	private MyJdbc myJdbc;
//
//	@Autowired
//	private MyHikari myHikari;
//
//	@Autowired
//	private TempTable2Repository tempTable2Repository;
//
//	@Test
//	void contextLoads() {
//	}
//
//	@Test
//	void jdbcTest() {
//		myJdbc.findall();
//	}
//
//	@Test
//	void hikariTest() throws SQLException {
//		myHikari.findall();
//	}
//
//	@Test
//	void tempTable2RepositoryTest() {
//		tempTable2Repository.findById(10L);
//	}
//
//	//swagger
//	//logger
//}
