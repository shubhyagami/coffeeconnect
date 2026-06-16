package com.coffeeconnect.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("render")
public class RenderDataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource(Environment env) throws URISyntaxException {
        String raw = env.getProperty("DATABASE_URL");
        if (raw == null || raw.isBlank()) {
            throw new IllegalStateException("DATABASE_URL env var is not set");
        }
        URI uri = new URI(raw);
        String userInfo = uri.getUserInfo();
        String username = userInfo != null ? userInfo.split(":")[0] : "";
        String password = userInfo != null ? userInfo.split(":")[1] : "";
        String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName("org.postgresql.Driver");
        return ds;
    }
}
