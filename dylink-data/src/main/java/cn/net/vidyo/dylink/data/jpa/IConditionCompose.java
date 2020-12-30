package cn.net.vidyo.dylink.data.jpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.NotSupportedException;

public interface IConditionCompose<T,CONDITION> {
    Specification<T> getWhereClause(final CONDITION condition);
    <CONDITION extends ICondition> Predicate conditionToPredicate(ICondition condition, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) throws NotSupportedException;
}
