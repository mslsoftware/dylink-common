package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import cn.net.vidyo.dylink.data.domain.Condition;
import cn.net.vidyo.dylink.data.jpa.ConditionCompose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EntityService<DAO extends CommonJpaRepository<T, ID>, T, ID extends Serializable>  {


    DAO getRepositoryDao();
    //<editor-fold desc="Extent">
    //<editor-fold desc="update">

    int updateStatusById(ID id, Object value);


    int updateHiddenById(ID id, Object value);

    //</editor-fold>
    //<editor-fold desc="delete">

    int deleteByIds(Iterable<ID> ids);

    int deleteByIds(ID ... ids);
    int deleteById(ID id);

    //</editor-fold>
    //<editor-fold desc="Query">


    //<editor-fold desc="get entity">

    T getById(ID id);

    ID getIdByEntity(T t);

    T getByEntityId(T t);

    int truncateParmeryKey();

    int dropTable();


    //</editor-fold>
    //<editor-fold desc="get column">
    String getNameById(ID id);
    String getIdKeyById(ID id);
    String getCodeById(ID id);

    //</editor-fold>
    //<editor-fold desc="find list">

    List<T> findByIds(List<ID> ids);


    List<T> findByIds(ID[] ids);

    //</editor-fold>
    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="Base">
    //<editor-fold desc="update">
    <S extends T> Iterable<S> batchUpdate(Iterable<S> var1);
    //</editor-fold>
    //<editor-fold desc="insert">
    <S extends T> S save(S entity);
    <S extends T> S saveAndFlush(S entity);
    <S extends T> Iterable<S> batchSave(Iterable<S> var1);
    //</editor-fold>
    //<editor-fold desc="delete">
    void deleteAll();
    void deleteAll(Iterable<? extends T> var1);
    //</editor-fold>
    //<editor-fold desc="query">

    //<editor-fold desc="query object">
    //</editor-fold>
    //<editor-fold desc="query object list">
    //</editor-fold>
    //<editor-fold desc="query object page">
    //</editor-fold>
    //</editor-fold>


    //</editor-fold>
}
