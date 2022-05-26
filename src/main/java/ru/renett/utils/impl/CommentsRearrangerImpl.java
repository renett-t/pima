package ru.renett.utils.impl;

import org.springframework.stereotype.Service;
import ru.renett.models.Comment;
import ru.renett.utils.CommentsRearranger;

import java.util.List;

@Service
public class CommentsRearrangerImpl implements CommentsRearranger {
    @Override
    public List<Comment> rearrangeCommentsList(List<Comment> commentList) {
        for (int i = commentList.size() - 1; i >= 0; i--) {                                                                 // нужно идти с конца, так как id вложенных комментариев точно больше родительских
            Comment child = commentList.get(i);
            if (child.getParentComment() != null) {
                commentList.remove(i);
                for (int k = 0; k < commentList.size(); k++) {
                    if (commentList.get(k).getId().equals(child.getParentComment().getId())) {
                        if (!commentList.get(k).getChildComments().contains(child))
                            commentList.get(k).getChildComments().add(child);
                        break;
                    }
                }
            }
        }

        // todo: add comment list sorting - id comparator

        return commentList;
    }
}
