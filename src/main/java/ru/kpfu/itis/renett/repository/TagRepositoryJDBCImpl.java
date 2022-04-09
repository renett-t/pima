package ru.kpfu.itis.renett.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.renett.exceptions.DataBaseException;
import ru.kpfu.itis.renett.models.Tag;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TagRepositoryJDBCImpl implements TagRepository {
    //language=sql
    private static final String SQL_SELECT_ALL = "SELECT * FROM tag";
    //language=sql
    private static final String SQL_FIND_BY_ID = "SELECT * FROM tag WHERE id = ?";
    //language=sql
    private static final String SQL_UPDATE_BY_ID = "UPDATE tag set title = ? WHERE id=?";
    //language=sql
    private static final String SQL_DELETE_BY_ID = "DELETE FROM tag WHERE id = ?;";
    //language=sql
    private static final String SQL_INSERT_TAG = "INSERT INTO tag(title) VALUES (?);";
    //language=sql
    private static final String SQL_FIND_ALL_BY_ARTICLE_ID = "SELECT * FROM\n" +
            "    tag left join article_tag on tag.id = article_tag.tag_id\n" +
            "WHERE article_id = ?;";
    //language=sql
    private static final String SQL_INSERT_NEW_TAG = "INSERT INTO article_tag(article_id, tag_id) VALUES (?, ?);";
    //language=sql
    private static final String SQL_DELETE_OLD_TAG = "DELETE FROM article_tag WHERE article_id = ? AND tag_id=?;";

    //tag columns
    private static final String id = "id";
    private static final String title = "title";

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public TagRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Tag> tagRowMapper = (row, rowNumber) -> Tag.builder()
            .id(row.getInt(id))
            .title(row.getString(title))
            .build();

    @Override
    public void save(Tag entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_TAG, Statement.RETURN_GENERATED_KEYS)) {
            int j = 1;
            preparedStatement.setString(j++, entity.getTitle());
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                Integer id = resultSet.getInt(1);
                entity.setId(id);
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with saving tag", e);
        }
    }

    @Override
    public void update(Tag entity) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID, entity.getTitle(), entity.getId());
    }

    @Override
    public void delete(Tag entity) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, entity.getId());
    }

    @Override
    public Optional<Tag> findById(int id) {
        List<Tag> tagList = jdbcTemplate.query(SQL_FIND_BY_ID, tagRowMapper, id);

        if (tagList.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(tagList.get(0));
        }
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, tagRowMapper);
    }

    @Override
    public List<Tag> findAllArticleTags(int articleId) {
       return jdbcTemplate.query(SQL_FIND_ALL_BY_ARTICLE_ID, tagRowMapper, articleId);
    }

    @Override
    public void saveNewTags(List<Tag> newTags, int articleId) {
        for (Tag tag : newTags) {
            jdbcTemplate.update(SQL_INSERT_NEW_TAG, articleId, tag.getId());
        }
    }

    @Override
    public void deleteOldTags(List<Tag> oldTags, int articleId) {
        for (Tag tag : oldTags) {
            jdbcTemplate.update(SQL_DELETE_OLD_TAG, articleId, tag.getId());
        }
    }
}
