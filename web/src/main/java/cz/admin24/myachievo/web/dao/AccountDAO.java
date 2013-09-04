package cz.admin24.myachievo.web.dao;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO extends JdbcDaoSupport {
    private static final String INSERT_NEW_ACCOUNT = "INSERT INTO account (id) VALUES (?)";


    public UUID createAccount() {
        UUID uuid = UUID.randomUUID();
        getJdbcTemplate().update(INSERT_NEW_ACCOUNT, uuid);
        return uuid;
    }


    @Autowired
    public void setJdbcTemplateAutowired(JdbcTemplate jdbcTemplate) {
        setJdbcTemplate(jdbcTemplate);
    }

}
