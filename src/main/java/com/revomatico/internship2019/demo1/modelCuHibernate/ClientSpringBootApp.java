//package com.revomatico.internship2019.demo1.modelCuHibernate;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Arrays;
//import javax.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//
//@SpringBootApplication
//@ComponentScan("package com.revomatico.internship2019.demo1.readers.event")
//public class ClientSpringBootApp {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public static void main(String[] args) {
//        System.setProperty("spring.datasource.url","jdbc:h2:mem:testdb");
//        SpringApplication.run(ClientSpringBootApp.class, args);
//    }
//
//    @PostConstruct
//    private void initDb() {
//        String sqlStatements[] = {
//                "insert into test(NAME, DATE) values('Concert5','20-iul-2019')",
//                "insert into test(NAME, DATE) values('Concert6','30-iul-2019')"
//        };
//
//        Arrays.asList(sqlStatements).stream().forEach(sql -> {
//            System.out.println(sql);
//            jdbcTemplate.execute(sql);
//        });
//
//
//        jdbcTemplate.query("select id,NAME,DATE from test",
//                new RowMapper<Object>() {
//                    @Override
//                    public Object mapRow(ResultSet rs, int i) throws SQLException {
//                        System.out.println(String.format("id:%s,NAME:%s,DATE:%s",
//                                rs.getString("id"),
//                                rs.getString("NAME"),
//                                rs.getString("DATE")));
//                        return null;
//                    }
//                });
//    }
//}