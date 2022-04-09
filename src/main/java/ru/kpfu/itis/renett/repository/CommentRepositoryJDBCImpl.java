package ru.kpfu.itis.renett.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.renett.exceptions.DataBaseException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.User;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class CommentRepositoryJDBCImpl implements CommentRepository{
    //language=sql
    private static final String SQL_SELECT_ALL_COMMENTS = "SELECT * FROM comment ORDER BY id";
    //language=sql
    private static final String SQL_FIND_COMMENT_BY_ID = "SELECT * FROM comment WHERE id = ?;";
    //language=sql
    private static final String SQL_UPDATE_COMMENT_BY_ID = "UPDATE comment set body = ? WHERE id = ?";
    //language=sql
    private static final String SQL_DELETE_COMMENT_BY_ID = "DELETE FROM comment WHERE id = ?;";
    //language=sql
    private static final String SQL_SAVE_COMMENT = "INSERT INTO comment(body, author_id, article_id, parent_comment_id) VALUES (?, ?, ?, ?);";
    //language=sql
    private static final String SQL_FIND_ALL_COMMENTS_BY_ARTICLE_ID = "WITH RECURSIVE _comment AS\n" +
            "                   (SELECT id, body, author_id, article_id, parent_comment_id, published_at,\n" +
            "                           1 AS level\n" +
            "                    FROM comment\n" +
            "                    WHERE parent_comment_id IS NULL AND article_id = ?\n" +
            "                    UNION\n" +
            "                    SELECT comment.id, comment.body, comment.author_id, comment.article_id, comment.parent_comment_id, comment.published_at,\n" +
            "                           (level + 1) AS level\n" +
            "                    FROM comment INNER JOIN _comment ON _comment.id = comment.parent_comment_id)\n" +
            "SELECT * FROM _comment ORDER BY level;";

    //comment columns
    private static final String id = "id";
    private static final String body = "body";
    private static final String authorId = "author_id";
    private static final String articleId = "article_id";
    private static final String parentCommentId = "parent_comment_id";
    private static final String publishedAt = "published_at";

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public CommentRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Comment> commentRowMapper = (row, rowNumber) -> {
        try {
            int parentId = row.getInt(parentCommentId);
            Comment parentComment = null;
            if (parentId > 0) {
                parentComment = new Comment(parentId);
            }

            return Comment.builder()
                    .id(row.getInt(id))
                    .body(row.getString(body))
                    .article(new Article(row.getInt(articleId)))
                    .author(new User(row.getInt(authorId)))
                    .publishedAt(row.getTimestamp(publishedAt))
                    .parentComment(parentComment)
                    .childComments(new ArrayList<>())
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    };

    @Override
    public void save(Comment entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COMMENT, Statement.RETURN_GENERATED_KEYS)) {
            int j = 1;
            preparedStatement.setString(j++, entity.getBody());
            preparedStatement.setInt(j++, entity.getAuthor().getId());
            preparedStatement.setInt(j++, entity.getArticle().getId());

            if (entity.getParentComment() != null) {
                preparedStatement.setInt(j++, entity.getParentComment().getId());
            } else {
                preparedStatement.setObject(j++, null);
            }

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                Integer id = resultSet.getInt(1);
                entity.setId(id);
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with saving comment", e);
        }
    }

    @Override
    public void update(Comment entity) {
        jdbcTemplate.update(SQL_UPDATE_COMMENT_BY_ID, entity.getBody(), entity.getId());
    }

    @Override
    public void delete(Comment entity) {
        jdbcTemplate.update(SQL_DELETE_COMMENT_BY_ID, entity.getId());
    }

    /**
     * WARNING - Returns RAW comment, with dummy Author & ParentComment (that have only id attribute), with empty childComments list
     * @param id id of comment to search for
     * @return Optional
     */
    @Override
    public Optional<Comment> findById(int id) {
        List<Comment> commentList = jdbcTemplate.query(SQL_FIND_COMMENT_BY_ID, commentRowMapper, id);

        if (commentList.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(commentList.get(0));
        }
    }

    @Override
    public List<Comment> findAll() {
        throw new UnsupportedOperationException("Sorry i haven't implemented this method yet");
    }

    /**
     * WARNING: it returns all comments without 1) filling childComments list and rearranging returned list, 2) dummy Article & Author
     * @param articleId id of article to find its comments
     * @return list
     */
    @Override
    public List<Comment> findAllArticleComments(int articleId) {
        return jdbcTemplate.query(SQL_FIND_ALL_COMMENTS_BY_ARTICLE_ID, commentRowMapper, articleId);
    }
}
