package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.renett.models.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
