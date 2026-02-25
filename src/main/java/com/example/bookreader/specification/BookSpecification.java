package com.example.bookreader.specification;

import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class BookSpecification {
    public static Specification<Book> bookHasName(String name) {
        return ((root, query, criteriaBuilder) -> {
            if(name == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        });
    }

    public static Specification<Book> bookHasAuthor(String author) {
        return ((root, query, criteriaBuilder) -> {
            if(author == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%");
        });
    }

    public static Specification<Book> bookHasGenres(List<UUID> genreIds) {
        return ((root, query, criteriaBuilder) -> {
            if(genreIds == null || genreIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            var genreJoin = root.join("genres");
            query.distinct(true);

            return genreJoin.get("id").in(genreIds);
        });
    }

    public static Specification<Book> bookVisibleTo(User user) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.isFalse(root.get("isPrivate")),
                        criteriaBuilder.equal(root.get("user"),user)
        );
    }
}
