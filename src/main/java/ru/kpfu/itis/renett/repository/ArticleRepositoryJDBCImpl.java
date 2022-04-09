package ru.kpfu.itis.renett.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.renett.exceptions.DataBaseException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class ArticleRepositoryJDBCImpl implements ArticleRepository {
    //language=sql
    private static final String SQL_SELECT_ALL = "SELECT * FROM article ORDER BY id";
    //language=sql
    private static final String SQL_FIND_ALL_BY_AUTHOR_ID = "SELECT * FROM article WHERE author_id = ? ORDER BY id;";
    //language=sql
    private static final String SQL_INSERT_ARTICLE = "INSERT INTO article(title, body, author_id, thumbnail_path) VALUES (?, ?, ?, ?);";
    //language=sql
    private static final String SQL_FIND_BY_ID = "SELECT * FROM article WHERE id = ?";
    //language=sql
    private static final String SQL_UPDATE_BY_ID = "UPDATE article set title = ?, body = ?, thumbnail_path = ? WHERE id=?";
    //language=sql
    private static final String SQL_UPDATE_WITHOUT_THUMBNAIL = "UPDATE article set title = ?, body = ? WHERE id=?";
    //language=sql
    private static final String SQL_DELETE_BY_ID = "DELETE FROM article WHERE id = ?;";
    //language=sql
    private static final String SQL_INSERT_ARTICLE_TAG = "INSERT INTO article_tag(article_id, tag_id) VALUES (?, ?);";
    //language=sql
    private static final String SQL_INSERT_LIKES = "INSERT INTO like_article VALUES (?, ?);";
    //language=sql
    private static final String SQL_REMOVE_LIKE = "DELETE FROM like_article WHERE user_id = ? AND article_id = ?";
    //language=sql
    private static final String SQL_COUNT_LIKES_FOR_ARTICLE = "SELECT COUNT(user_id) FROM like_article WHERE article_id = ?;";
    //language=sql
    private static final String SQL_UPDATE_VIEWS_BY_ARTICLE_ID = "UPDATE article set view_count = ? WHERE id=?";
    //language=sql
    private static final String SQL_FIND_ALL_BY_TAG_ID = "SELECT * FROM\n" +
            "    article_tag LEFT JOIN article a on a.id = article_tag.article_id\n" +
            "WHERE article_tag.tag_id = ? ORDER by a.id;";
    //language=sql
    private static final String SQL_FIND_ALL_ARTICLES_LIKED_BY_USER = "SELECT * FROM\n" +
            "    like_article LEFT JOIN article a on a.id = like_article.article_id\n" +
            "WHERE like_article.user_id = ? ORDER BY a.id;";

    //article columns
    private static final String id = "id";
    private static final String title = "title";
    private static final String body = "body";
    private static final String authorId = "author_id";
    private static final String publishedAt = "published_at";
    private static final String thumbnailPath = "thumbnail_path";
    private static final String viewCount = "view_count";

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public ArticleRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Article> getArticleFromRow = (row, rowNumber) -> {
        try {
            return Article.builder()
                    .id(row.getInt(id))
                    .title(row.getString(title))
                    .body(row.getString(body))
                    .author(new User(row.getInt(authorId)))
                    .publishedAt(row.getTimestamp(publishedAt))
                    .thumbnailPath(row.getString(thumbnailPath))
                    .viewAmount(row.getLong(viewCount))
                    .commentList(new ArrayList<>())
                    .tagList(new ArrayList<>())
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    };

    @Override
    public void save(Article article) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS)) {
            int j = 1;
            preparedStatement.setString(j++, article.getTitle());
            preparedStatement.setString(j++, article.getBody());
            preparedStatement.setInt(j++, article.getAuthor().getId());
            preparedStatement.setString(j++, article.getThumbnailPath());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                Integer id = resultSet.getInt(1);
                article.setId(id);
            }
            saveArticlesTags(article);
        } catch (SQLException e) {
            throw new DataBaseException("Problem with saving article", e);
        }
    }

    @Override
    public void update(Article article) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID, article.getTitle(), article.getBody(), article.getThumbnailPath(), article.getId());
        saveArticlesTags(article);
    }

    @Override
    public void updateWithoutThumbnail(Article article) {
        jdbcTemplate.update(SQL_UPDATE_WITHOUT_THUMBNAIL, article.getTitle(), article.getBody(), article.getId());
    }

    @Override
    public void delete(Article entity) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, entity.getId());
    }

    @Override
    public List<Article> findAllByOwnerId(int ownerId) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_AUTHOR_ID, getArticleFromRow, ownerId);
    }

    @Override
    public void updateLikesAmount(int userId, int articleId) {
        jdbcTemplate.update(SQL_INSERT_LIKES, userId, articleId);
    }

    @Override
    public void removeLikeFromArticle(int userId, int articleId) {
        jdbcTemplate.update(SQL_REMOVE_LIKE, userId, articleId);
    }

    @Override
    public int getLikesAmount(int articleId) {
        return jdbcTemplate.query(SQL_COUNT_LIKES_FOR_ARTICLE,
                (row, rowNum) -> row.getInt("count"),
                articleId).get(0);
    }

    @Override
    public void updateViewCount(int articleId, Long viewCount) {
        jdbcTemplate.update(SQL_UPDATE_VIEWS_BY_ARTICLE_ID, viewCount, articleId);
    }

    @Override
    public List<Article> findAllByTagId(int tagId) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_TAG_ID, getArticleFromRow, tagId);
    }

    @Override
    public List<Article> findAllLikedArticles(int userId) {
        return jdbcTemplate.query(SQL_FIND_ALL_ARTICLES_LIKED_BY_USER, getArticleFromRow, userId);
    }

    private void saveArticlesTags(Article article) {
        if (article.getTagList() != null) {
            for (Tag tag : article.getTagList()) {
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ARTICLE_TAG)) {

                    int j = 1;
                    preparedStatement.setInt(j++, article.getId());
                    preparedStatement.setInt(j++, tag.getId());

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new DataBaseException("Problem with saving article's tags", e);
                }
            }
        }
    }

    @Override
    public Optional<Article> findById(int id) {
        List<Article> articleList = jdbcTemplate.query(SQL_FIND_BY_ID, getArticleFromRow, id);
        if (articleList.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(articleList.get(0));
        }
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, getArticleFromRow);
    }
}
