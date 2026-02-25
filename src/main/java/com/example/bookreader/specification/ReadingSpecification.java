package com.example.bookreader.specification;

import com.example.bookreader.entity.Reading;
import com.example.bookreader.entity.User;
import com.example.bookreader.enums.ReadingStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class ReadingSpecification {

    public static Specification<Reading> hasOwner(User owner) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user"), owner));
    }

    public static Specification<Reading> isVisibleFor(User viewer, User owner) {
        return (root, query, criteriaBuilder) -> {
            if (viewer.getId().equals(owner.getId())) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.isFalse(root.get("privateReading"));
        };
    }

    public static Specification<Reading> hasStatus(ReadingStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Reading> bookHasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.join("book").get("name")), "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Reading> bookHasAuthor(String author) {
        return (root, query, criteriaBuilder) -> {
            if (author == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.join("book").get("author")), "%" + author.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Reading> bookHasGenres(List<UUID> genreId) {
        return (root, query, criteriaBuilder) -> {
            if (genreId == null || genreId.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            var genreJoin = root.join("book").join("genres");
            query.distinct(true);

            return genreJoin.get("id").in(genreId);
        };
    }
}
