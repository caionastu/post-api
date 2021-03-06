package com.caionastu.postapi.user.repository;

import com.caionastu.postapi.commons.util.SpecificationUtils;
import com.caionastu.postapi.user.application.request.UserFilterRequest;
import com.caionastu.postapi.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.springframework.data.jpa.domain.Specification.where;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSpecification {

    public static Specification<User> from(UserFilterRequest filter) {
        return where(likeName(filter.getName()))
                .and(equalEmail(filter.getEmail()))
                .and(isActive(filter.isActive()));
    }

    private static Specification<User> likeName(String name) {
        if (Strings.isBlank(name)) {
            return null;
        }

        return (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), SpecificationUtils.likeFormat(name));
    }

    private static Specification<User> equalEmail(String email) {
        if (Strings.isBlank(email)) {
            return null;
        }

        return (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }

    private static Specification<User> isActive(boolean active) {
        return (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("active"), active);
    }
}
