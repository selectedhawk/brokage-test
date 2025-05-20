package com.ing.brokagetest.handler;

import com.ing.brokagetest.entity.BaseEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class HandlerHelper {

    public static <ENTITY extends BaseEntity> Example<ENTITY> getExample(ENTITY entity) {
        return Example.of(entity, ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    public static <ENTITY extends BaseEntity> Predicate getExamplePredicate(ENTITY entity, Root<ENTITY> root, CriteriaBuilder builder) {
        if (entity == null) return builder.conjunction();
        Example<ENTITY> example = getExample(entity);
        Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, builder, example);
        return predicate != null ? predicate : builder.conjunction();
    }

    public static <ENTITY extends BaseEntity> Specification<ENTITY> getEntitySpecification(ENTITY entity) {
        return (root, query, builder) -> getExamplePredicate(entity, root, builder);
    }

    public static <ENTITY extends BaseEntity> Specification<ENTITY> getDateSpecification(Long startDate, Long endDate) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (startDate != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("date"), startDate));
                if (endDate != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("date"), endDate)) ;
                }
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
