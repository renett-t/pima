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
                    if (commentList.get(k).getId().equals(child.getParentComment().getId())){
                        commentList.get(k).getChildComments().add(child);
                        break;
                    }
                }
            }
        }

        for (int j = 0; j < commentList.size(); j++) {
            List<Comment> listToReverse = commentList.get(j).getChildComments();
            for (int i = 0; i < listToReverse.size() / 2; i++) {
                Comment temp = listToReverse.get(i);
                listToReverse.set(i, listToReverse.get(listToReverse.size() - i - 1));
                listToReverse.set(listToReverse.size() - i - 1, temp);
            }

        }
        return commentList;
    }
}
