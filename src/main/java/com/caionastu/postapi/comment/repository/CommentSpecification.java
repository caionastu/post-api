package com.caionastu.postapi.comment.repository;

import com.caionastu.postapi.comment.application.request.CommentFilterRequest;
import com.caionastu.postapi.comment.domain.Comment;
import com.caionastu.postapi.commons.util.SpecificationUtils;
import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentSpecification {

    public static Specification<Comment> from(CommentFilterRequest filter){
        return where(likeText(filter.getText()))
                .and(equalUserId(filter.getUserId()))
                .and(equalPostId(filter.getPostId()));
    }

    private static Specification<Comment> likeText(String text) {
        if (Strings.isBlank(text)) {
            return null;
        }

        return (Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.like(root.get("text"), SpecificationUtils.likeFormat(text));
    }

    private static Specification<Comment> equalUserId(UUID userId) {
        if (Objects.isNull(userId)) {
            return null;
        }

        return (Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
        {
            Join<User, UUID> joinUser = root.join("user");
            return criteriaBuilder.equal(joinUser.get("id"), userId);
        };
    }

    private static Specification<Comment> equalPostId(UUID postId) {
        if (Objects.isNull(postId)) {
            return null;
        }

        return (Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
        {
            Join<User, UUID> joinUser = root.join("post");
            return criteriaBuilder.equal(joinUser.get("id"), postId);
        };
    }
}
