package com.gabil.tracker.issue;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public final class IssueSpecifications {
    private IssueSpecifications() {}

    public static Specification<Issue> build(final IssueFilter f) {
        return (root, query, cb) -> {
            List<Predicate> ps = new ArrayList<>();

            if (f.getProjectId() != null) {
                ps.add(cb.equal(root.get("project").get("id"), f.getProjectId()));
            }

            if (f.getStatus() != null) {
                ps.add(cb.equal(root.get("status"), f.getStatus()));
            }

            if (f.getQ() != null && !f.getQ().isBlank()) {
                String like = "%" + f.getQ().toLowerCase() + "%";
                ps.add(cb.or(
                    cb.like(cb.lower(root.get("title")), like),
                    cb.like(cb.lower(root.get("description")), like)
                ));
            }

            if (f.getCreatedAfter() != null) {
                ps.add(cb.greaterThanOrEqualTo(root.get("createdAt"), f.getCreatedAfter()));
            }

            if (f.getCreatedBefore() != null) {
                ps.add(cb.lessThanOrEqualTo(root.get("createdAt"), f.getCreatedBefore()));
            }

            return ps.isEmpty()
                ? cb.conjunction()            
                : cb.and(ps.toArray(new Predicate[0]));
        };
    }
}
