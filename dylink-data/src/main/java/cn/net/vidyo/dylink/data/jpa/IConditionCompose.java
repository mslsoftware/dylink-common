package cn.net.vidyo.dylink.data.jpa;

import cn.net.vidyo.dylink.data.jpa.sql.QueryWhere;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.NotSupportedException;

public interface IConditionCompose<CONDITION> {
    QueryWhere buildWhere(CONDITION condition);
}
