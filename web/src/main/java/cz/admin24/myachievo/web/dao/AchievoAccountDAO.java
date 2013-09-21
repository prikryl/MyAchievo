package cz.admin24.myachievo.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Repository;

import cz.admin24.myachievo.web.entity.AchievoAccount;

@Repository
public class AchievoAccountDAO extends JdbcDaoSupport {
    private static final String INSERT_NEW_ACHIEVO_ACCOUNT           = "INSERT INTO achievoAccount (id, username, password, account_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ACHIEVO_ACCOUNT               = "UPDATE achievoAccount SET username = ?, password = ? WHERE account_id = ?";
    private static final String SELECT_ACHIEVO_ACCOUNT_BY_ACCOUNT_ID = "select id, username, password, account_id from achievoAccount where account_id = ?";

    @Autowired
    private TextEncryptor       textEncryptor;


    @Autowired
    public void setJdbcTemplateAutowired(JdbcTemplate jdbcTemplate) {
        setJdbcTemplate(jdbcTemplate);
    }


    public AchievoAccount findById(UUID accountId) {
        return getJdbcTemplate().query(SELECT_ACHIEVO_ACCOUNT_BY_ACCOUNT_ID, new Object[] { accountId }, new AchievoAccountRSExtractor());
    }


    @Transactional
    public void upsertCredentials(UUID accountId, String username, String password) {
        if (findById(accountId) == null) {
            UUID uuid = UUID.randomUUID();
            getJdbcTemplate().update(INSERT_NEW_ACHIEVO_ACCOUNT, uuid, username, textEncryptor.encrypt(password), accountId);
        } else {
            getJdbcTemplate().update(UPDATE_ACHIEVO_ACCOUNT, username, textEncryptor.encrypt(password), accountId);
        }
    }

    private class AchievoAccountRSExtractor implements ResultSetExtractor<AchievoAccount> {

        @Override
        public AchievoAccount extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (!rs.next()) {
                return null;
            }
            UUID id = UUID.fromString(rs.getString("id"));
            String username = rs.getString("username");
            String encryptedPassword = rs.getString("password");
            String accountId = rs.getString("account_id");

            return new AchievoAccount(id, username, encryptedPassword, accountId);
        }

    }

}
