package ru.kpfu.itis.renett.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.renett.exceptions.DataBaseException;
import ru.kpfu.itis.renett.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJDBCImpl implements UserRepository {
    //language=sql
    private static final String SQL_SELECT_ALL = "SELECT * FROM \"user\" ORDER BY id";
    //language=sql
    private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM \"user\" WHERE login = ?";
    //language=sql
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM \"user\" WHERE email = ?";
    //language=sql
    private static final String SQL_INSERT_USER = "INSERT INTO \"user\"(first_name, second_name, email, login, password_hash) VALUES (?, ?, ?, ?, ?);";
    //language=sql
    private static final String SQL_FIND_BY_ID = "SELECT * FROM \"user\" WHERE id = ?";
    //language=sql
    private static final String SQL_UPDATE_BY_ID = "UPDATE \"user\" set first_name = ?, second_name = ?, email = ?, login = ?, password_hash = ? WHERE id=?";
    //language=sql
    private static final String SQL_DELETE_BY_ID = "DELETE FROM \"user\" WHERE id = ?;";

    //user columns
    private static final String id = "id";
    private static final String firstName = "first_name";
    private static final String secondName = "second_name";
    private static final String email = "email";
    private static final String login = "login";
    private static final String password = "password_hash";

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<User> userRowMapper = (row, rowNumber) -> User.builder()
        .id(row.getInt(id))
        .firstName(row.getString(firstName))
        .secondName(row.getString(secondName))
        .email(row.getString(email))
        .login(row.getString(login))
        .passwordHash(row.getString(password))
        .build();

    @Override
    public List<User> findAll() throws DataBaseException {
       return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(User user) throws DataBaseException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            int j = 1;
            preparedStatement.setString(j++, user.getFirstName());
            preparedStatement.setString(j++, user.getSecondName());
            preparedStatement.setString(j++, user.getEmail());
            preparedStatement.setString(j++, user.getLogin());
            if (user.getPasswordHash() != null) {
                preparedStatement.setString(j++, user.getPasswordHash());
            } else {
                preparedStatement.setObject(j++, null);
            }

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                Integer id = resultSet.getInt(1);
                user.setId(id);
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with saving user", e);
        }
    }

    @Override
    public void update(User user) throws DataBaseException {
        jdbcTemplate.update(SQL_UPDATE_BY_ID, user.getFirstName(), user.getSecondName(), user.getEmail(), user.getLogin(), user.getPasswordHash(), user.getId());
    }

    @Override
    public void delete(User user) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, user.getId());
    }

    @Override
    public Optional<User> findByLogin(String login) {
        List<User> userList = jdbcTemplate.query(SQL_FIND_BY_LOGIN, userRowMapper, login);

        if (userList.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(userList.get(0));
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> userList = jdbcTemplate.query(SQL_FIND_BY_EMAIL, userRowMapper, email);

        if (userList.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(userList.get(0));
        }
    }

    @Override
    public Optional<User> findById(int id) throws DataBaseException {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, userRowMapper, id));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }
}
