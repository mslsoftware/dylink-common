package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.data.domain.Condition;
import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import cn.net.vidyo.dylink.data.jpa.sql.QueryWhere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class EntityConditionServiceImpl<
        CONDITION extends Condition,
        DAO extends CommonJpaRepository<T, ID>,
        T, ID extends Serializable>
        extends EntityServiceImpl<DAO,T,ID>
        implements EntityConditionService<CONDITION,DAO,T,ID> {

    @Override
    public Object getColumn(String columnName, Condition condition) {
        return getEntityDao().getColumn(columnName,condition,this);
    }

    @Override
    public T getModel(Condition condition) {
        return getEntityDao().getModel(condition,this);
    }

    @Override
    public Map<String, Object> getMap(Condition condition) {
        return getEntityDao().getMap(condition,this);
    }

    @Override
    public List<T> query(Condition condition) {
        return getEntityDao().query(condition,this);
    }

    @Override
    public List<Map<String, Object>> queryMap(Condition condition) {
        return getEntityDao().queryMap(condition,this);
    }

    @Override
    public Page<T> pageQuery(Pageable pageable, Condition condition) {
        return getEntityDao().pageQuery(pageable,condition,this);
    }

    @Override
    public Page<T> pageQuery(int pageNumber, int pageSize, Condition condition) {
        return getEntityDao().pageQuery(pageNumber,pageSize,condition,this);
    }

    @Override
    public Page<Map<String, Object>> pageQueryMap(Pageable pageable, Condition condition) {
        return getEntityDao().pageQueryMap(pageable,condition,this);
    }

    @Override
    public Page<Map<String, Object>> pageQueryMap(int pageNumber, int pageSize, Condition condition) {
        return getEntityDao().pageQueryMap(pageNumber,pageSize,condition,this);
    }
}
