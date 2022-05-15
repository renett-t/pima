package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.renett.models.Like;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndArticleId(Long userId, Long articleId);
    List<Like> findAllByArticleId(Long articleId);
}
