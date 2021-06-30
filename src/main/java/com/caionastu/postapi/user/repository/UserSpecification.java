package com.caionastu.postapi.user.repository;

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

    private static final String WILDCARD = "%";

    public static Specification<User> filterRequest(UserFilterRequest filter) {
        return where(likeName(filter.getName())).and(equalEmail(filter.getEmail()));
    }

    private static Specification<User> likeName(String name) {
        if (Strings.isBlank(name)) {
            return null;
        }

        return (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), WILDCARD + name + WILDCARD);
    }

    private static Specification<User> equalEmail(String email) {
        if (Strings.isBlank(email)) {
            return null;
        }

        return (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }
}
