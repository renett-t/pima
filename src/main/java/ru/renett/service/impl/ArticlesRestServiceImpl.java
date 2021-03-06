package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.CommentDto;
import ru.renett.dto.rest.AddArticleDto;
import ru.renett.dto.rest.ArticlesPage;
import ru.renett.dto.rest.CommentsPage;
import ru.renett.dto.rest.UpdateArticleDto;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.exceptions.InvalidArticlesRequestException;
import ru.renett.models.Article;
import ru.renett.models.Tag;
import ru.renett.models.User;
import ru.renett.repository.ArticlesRepository;
import ru.renett.repository.CommentsRepository;
import ru.renett.repository.TagsRepository;
import ru.renett.repository.UsersRepository;
import ru.renett.service.article.ArticlesRestService;
import ru.renett.utils.CommentsRearranger;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticlesRestServiceImpl implements ArticlesRestService {
    private final ArticlesRepository articlesRepository;
    private final CommentsRearranger commentsRearranger;
    private final CommentsRepository commentsRepository;
    private final UsersRepository usersRepository;
    private final TagsRepository tagsRepository;

    @Override
    public ArticlesPage getArticles(int page, int limit) throws InvalidArticlesRequestException, EntityNotFoundException {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<Article> articlePage = articlesRepository.findAll(pageRequest);

        ArticlesPage result = ArticlesPage.builder()
                .articles(ArticleDto.from(articlePage.getContent()))
                .page(articlePage.getNumber())
                .limit(articlePage.getSize())
                .totalPages(articlePage.getTotalPages())
                .totalItems(articlePage.getTotalElements())
                .build();
        if (articlePage.isEmpty()) {
            if (articlePage.getTotalElements() > limit || articlePage.getTotalPages() < ++page) {
                result.setMessage("Exceeded limits. Not enough required elements found.");
                throw new InvalidArticlesRequestException(result);
            } else {
                result.setMessage("No required elements found.");
                throw new EntityNotFoundException("Articles not found");
            }
        }
        result.setMessage("Ok =)");

        return result;
    }

    @Override
    public CommentsPage getCommentsByArticleId(Long id) throws EntityNotFoundException {
        Article article = articlesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article with id = " + id + " not found."));
        List<CommentDto> list = CommentDto.from(
                commentsRearranger.rearrangeCommentsList(
                        commentsRepository.findCommentsByArticleIdRecursive(article.getId())));

        return CommentsPage.builder()
                .comments(list)
                .totalItems(article.getCommentAmount())
                .message("")
                .build();
    }


    @Transactional
    @Override
    public ArticleDto addNewArticle(AddArticleDto addArticleDto) {
        User author = usersRepository.findById(addArticleDto.getAuthorId())
                .orElseThrow(() ->
                        new EntityNotFoundException("User with id = " + addArticleDto.getAuthorId() + " not found. Unable to create new Article"));

        Set<Tag> tags = getTagsFromStrings(addArticleDto.getTags());

        Article newArt = Article.builder()
                .title(addArticleDto.getTitle())
                .author(author)
                .body(addArticleDto.getBody())
                .publishedAt(new Date())
                .thumbnailPath(addArticleDto.getImage())
                .likes(new HashSet<>())
                .commentList(new ArrayList<>())
                .viewAmount(0L)
                .tags(tags)
                .build();

        return ArticleDto.from(articlesRepository.save(newArt));
    }

    @Transactional
    @Override
    public ArticleDto updateArticle(Long id, UpdateArticleDto updateArticleDto) {
        Article article = articlesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article with id = " + id + " not found."));

        article.setTitle(updateArticleDto.getTitle());
        article.setBody(updateArticleDto.getBody());
        article.setThumbnailPath(updateArticleDto.getImage());
        article.setTags(getTagsFromStrings(updateArticleDto.getTags()));

        articlesRepository.save(article);

        return ArticleDto.from(article);
    }

    private Set<Tag> getTagsFromStrings(List<String> tags) {
        Set<Tag> tagsSet = new HashSet<>();
        for (String tag : tags) {
            Optional<Tag> possibleTag = tagsRepository.findTagByTitle(tag);
            if (possibleTag.isPresent()) {
                tagsSet.add(possibleTag.get());
            } else {
                Tag newTag = tagsRepository.save(Tag.builder()
                        .title(tag)
                        .build());
                tagsSet.add(newTag);
            }
        }
        return tagsSet;
    }

    @Override
    public void deleteArticleById(Long id) {
        Article article = articlesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article with id = " + id + " not found."));

        articlesRepository.delete(article);
    }

    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = articlesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article with id = " + id + " not found."));
        ArticleDto dto = ArticleDto.from(article);
        dto.setComments(CommentDto.from(commentsRearranger.rearrangeCommentsList(article.getCommentList())));
        return dto;
    }
}
