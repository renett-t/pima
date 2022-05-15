package ru.renett.utils;

import ru.renett.models.Comment;

import java.util.List;

public interface CommentsRearranger {
    List<Comment> rearrangeCommentsList(List<Comment> commentList);
}
