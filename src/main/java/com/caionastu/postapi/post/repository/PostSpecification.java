package com.caionastu.postapi.post.repository;

import com.caionastu.postapi.commons.util.SpecificationUtils;
import com.caionastu.postapi.post.application.request.PostFilterRequest;
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
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSpecification {

    public static Specification<Post> from(UUID userId, PostFilterRequest filter) {
        return where(equalUserId(userId))
                .and(likeTitle(filter.getTitle()))
                .and(likeBody(filter.getBody()))
                .and(dateBetween(filter.getStart(), filter.getEnd()));
    }

    private static Specification<Post> dateBetween(ZonedDateTime start, ZonedDateTime end) {
        if (Objects.isNull(start)) {
            return null;
        }

        if (Objects.isNull(end)) {
            end = ZonedDateTime.now();
        }

        final ZonedDateTime finalEnd = end;
        return (Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.between(root.get("date"), start, finalEnd);
    }

    private static Specification<Post> likeTitle(String title) {
        if (Strings.isBlank(title)) {
            return null;
        }

        return (Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), SpecificationUtils.likeFormat(title));
    }

    private static Specification<Post> likeBody(String body) {
        if (Strings.isBlank(body)) {
            return null;
        }

        return (Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.like(root.get("body"), SpecificationUtils.likeFormat(body));
    }

    private static Specification<Post> equalUserId(UUID userId) {
        if (Objects.isNull(userId)) {
            return null;
        }

        return (Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
        {
            Join<User, UUID> joinUser = root.join("user");
            return criteriaBuilder.equal(joinUser.get("id"), userId);
        };
    }
}
