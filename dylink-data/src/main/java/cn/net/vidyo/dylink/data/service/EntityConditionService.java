package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.data.domain.Condition;
import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import cn.net.vidyo.dylink.data.jpa.ConditionCompose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EntityConditionService<
        CONDITION extends Condition,
        DAO extends CommonJpaRepository<T, ID>,
        T, ID extends Serializable>
        extends EntityService<DAO,T,ID>, ConditionCompose<CONDITION> {

    //<editor-fold desc="query condition">

    //<editor-fold desc="query object">
    Object getColumn(String columnName, Condition condition);
    T getModel(Condition condition);

    Map<String, Object> getMap(Condition condition);

    //</editor-fold>
    //<editor-fold desc="query object list">
    List<T> query(Condition condition);

    List<Map<String, Object>> queryMap(Condition condition);

    //</editor-fold>
    //<editor-fold desc="query object page">
    Page<T> pageQuery(Pageable pageable, Condition condition);
    Page<T> pageQuery(int pageNumber, int pageSize, Condition condition);
    Page<Map<String, Object>> pageQueryMap(Pageable pageable, Condition condition);
    Page<Map<String, Object>> pageQueryMap(int pageNumber, int pageSize, Condition condition) ;

    //</editor-fold>
    //</editor-fold>
}
