package cz.admin24.myachievo.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import cz.admin24.myachievo.web.entity.Account;

@Repository
public class AccountDAO extends JdbcDaoSupport {
    private static final String INSERT_NEW_ACCOUNT     = "INSERT INTO account (id, username, password, firstName, lastName) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ACCOUNT_BY_ID   = "select id, username, password, firstName, lastName from Account where id = ?";
    private static final String SELECT_ACCOUNT_BY_NAME = "select id, username, password, firstName, lastName from Account where id = ?";


    @Autowired
    public void setJdbcTemplateAutowired(JdbcTemplate jdbcTemplate) {
        setJdbcTemplate(jdbcTemplate);
    }


    public UUID createAccount(String username, String password, String firstName, String lastName) {
        UUID uuid = UUID.randomUUID();
        getJdbcTemplate().update(INSERT_NEW_ACCOUNT, uuid, username, password, firstName, lastName);
        return uuid;
    }


    public Account findById(UUID id) {
        return getJdbcTemplate().query(SELECT_ACCOUNT_BY_ID, new Object[] { id }, new AccountRSExtractor());
    }


    public Account findByUsername(String name) {
        return getJdbcTemplate().query(SELECT_ACCOUNT_BY_NAME, new Object[] { name }, new AccountRSExtractor());
    }

    private static class AccountRSExtractor implements ResultSetExtractor<Account> {

        @Override
        public Account extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (!rs.next()) {
                return null;
            }
            UUID id = UUID.fromString(rs.getString("id"));
            String username = rs.getString("username");
            String password = rs.getString("password");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");

            return new Account(id, username, password, firstName, lastName);
        }

    }

}
