package com.library;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class InsertData implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public InsertData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        Long countBook = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM book", Long.class);
        if (countBook == null || countBook == 0) {
            try {
                String sql = new String(Files.readAllBytes(Paths.get("/app/db/bookDataDummy.sql")), StandardCharsets.UTF_8);
                jdbcTemplate.execute(sql);
                System.out.println("Data injected successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Book table already have data");
        }

        Long countCustomer = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customer", Long.class);
        if (countCustomer == null || countCustomer == 0) {
            try {
                String sql = new String(Files.readAllBytes(Paths.get("/app/db/customerDataDummy.sql")), StandardCharsets.UTF_8);
                jdbcTemplate.execute(sql);
                System.out.println("Data injected successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Customer table already have data");
        }
    }
}
