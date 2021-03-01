package cn.net.vidyo.dylink.data.jpa;

import cn.net.vidyo.dylink.data.domain.Condition;
import cn.net.vidyo.dylink.data.jpa.sql.QueryWhere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.*;

@NoRepositoryBean
public interface CommonJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    //<editor-fold desc="Extent">
    //<editor-fold desc="update">

//    <S extends T> S insert(S entity);
//    <S extends T> S update(S entity);
    int updateStatusById(ID id, Object value);


    int updateHiddenById(ID id, Object value);


    int updateColumnById(ID id, String fieldName, Object value);

    int increaseColumnValueById(ID id, String fieldName, int delta);

    //</editor-fold>
    //<editor-fold desc="delete">

    int deleteByIds(Iterable<ID> ids);

    int deleteByIds(ID ... ids);

    //</editor-fold>
    //<editor-fold desc="Query">


    //<editor-fold desc="get entity">

    T getById(ID id);

    ID getIdByEntity(T t);

    T getByEntityId(T t);

    int truncateParmeryKey(Class entityClass);

    int dropTable(Class entityClass);


    //</editor-fold>
    //<editor-fold desc="get column">

    Object getColumnById(ID id, String fieldName);
    String getStringColumnById(ID id, String fieldName) ;


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
    int increaseColumn(String columName,int delta,String where,Object... params);
    int increaseColumn(String columName,int delta,QueryWhere where);
    int updateColumn(String columName,Object value,String where,Object... params);
    int updateColumn(String columName,Object value,QueryWhere where);
    int updateColumns(Map<String,Object> columnNameValues,String where,Object... params);
    int updateColumns(Map<String,Object> columnNameValues,QueryWhere where);
    <S extends T> Iterable<S> batchUpdate(Iterable<S> var1);
    //</editor-fold>
    //<editor-fold desc="insert">
    <S extends T> S save(S entity);
    <S extends T> S saveAndFlush(S entity);
    <S extends T> Iterable<S> batchSave(Iterable<S> var1);
    //</editor-fold>
    //<editor-fold desc="delete">
    int deleteByWhere(String where,Object... params);
    int deleteByWhere(QueryWhere where);
    //</editor-fold>
    //<editor-fold desc="query">

    //<editor-fold desc="query object">
    Object getColumn(String columnName, String where, Object... params);
    Object getColumn(String columnName,QueryWhere where);
    T getModel( String where, Object... params);
    T getModel(QueryWhere where);
    <C> C getColumn(Class<C> cClass,String columnName, String where, Object... params);
    <C> C getColumn(Class<C> cClass, QueryWhere where);
    Map getMap(String where, Object... params);
    Map getMap(QueryWhere where);

    //</editor-fold>
    //<editor-fold desc="query object list">
    List<T> query(String where, Object... params);
    List<T> query(QueryWhere where);
    <C> List<C> queryColumn(Class<C> cClass,String select,String where, Object... params);
    <C> List<C> queryColumn(Class<C> cClass,QueryWhere where);
    List<Map> queryMap(String where, Object... params);
    List<Map> queryMap(QueryWhere where);

    //</editor-fold>
    //<editor-fold desc="query object page">
    Page<T> pageQuery(Pageable pageable, String where, Object... params) ;
    Page<T> pageQuery(Pageable pageable, QueryWhere where);
    Page<T> pageSelectQuery(Pageable pageable, String select, String where, Object... params);

    Page<Map> pageQueryMap(Pageable pageable, String where, Object... params) ;
    Page<Map> pageQueryMap(Pageable pageable, QueryWhere where) ;
    Page<Map> pageSelectQueryMap(Pageable pageable,String select, String where, Object... params) ;

    <C>  Page<C> pageQueryColumn(Class<C> cClass, Pageable pageable,String select, String where, Object... params);
    <C>  Page<C> pageQueryColumn(Class<C> cClass, Pageable pageable, QueryWhere where);
    <C>  Page<C> pageSelectQueryColumn(Class<C> cClass, Pageable pageable, String select, String where, Object... params);

    Page<T> pageQuery(int pageNumber, int pageSize, String where, Object... params);
    Page<T> pageQuery(int pageNumber, int pageSize,QueryWhere where);
    Page<T> pageSelectQuery(int pageNumber, int pageSize,String select, String where, Object... params);
    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="query condition">

    //<editor-fold desc="query object">
    Object getColumn(String columnName, Condition condition, ConditionCompose iConditionCompose);
    T getModel(Condition condition, ConditionCompose iConditionCompose);

    <C> C getColumn(Class<C> cClass, String columnName, Condition condition, ConditionCompose iConditionCompose);
    Map getMap(Condition condition, ConditionCompose iConditionCompose);

    //</editor-fold>
    //<editor-fold desc="query object list">
    List<T> query(Condition condition, ConditionCompose iConditionCompose);
    <C> List<C> queryColumn(Class<C> cClass, Condition condition, ConditionCompose iConditionCompose);
    List<Map> queryMap(Condition condition, ConditionCompose iConditionCompose);

    //</editor-fold>
    //<editor-fold desc="query object page">
    Page<T> pageQuery(Pageable pageable, Condition condition, ConditionCompose iConditionCompose);
    Page<T> pageQuery(int pageNumber, int pageSize, Condition condition, ConditionCompose iConditionCompose);
    <C> Page<C> pageQueryColumn(Class<C> cClass, Pageable pageable, Condition condition, ConditionCompose iConditionCompose);
    <C> Page<C> pageQueryColumn(Class<C> cClass,int pageNumber, int pageSize, Condition condition, ConditionCompose iConditionCompose);
    Page<Map> pageQueryMap(Pageable pageable, Condition condition, ConditionCompose iConditionCompose);
    Page<Map> pageQueryMap(int pageNumber, int pageSize, Condition condition, ConditionCompose iConditionCompose) ;

    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="sql">



//    T getBySql(String sql,Object ... params);
//
//    <C> C getObjectBySql(Class<C> cClass, String sql, Object... params);
//
//    Map getMapBySql(String sql,Object ... params);
//
//    List<T> queryBySql(String sql,Object ... params);
//    <C> List<C> queryObjectBySql(Class<C> cClass, String sql, Object... params);
//    List<Map> queryMapBySql(String sql,Object ... params);
//
//    Page<T> pageBySql(Pageable pageable, String sql,Object ... params);
//    <C> Page<C> pageObjectBySql(Class<C> cClass,Pageable pageable, String sql, Object... params);
//    Page<Map> pageMapBySql(Pageable pageable,String sql,Object ... params);
    //</editor-fold>
    //</editor-fold>

    /**
     * 获取数据类型
     *
     * @return
     */
    Class<T> getEntityClass();

    /**
     * 执行ql语句
     *
     * @param qlString 基于jpa标准的jpql语句
     * @param values   jpql中的?参数值,单个参数值或者多个参数值
     * @return 返回执行后受影响的数据个数
     */
    int executeUpdate(String qlString, Object... values);

    <E> List<E> executeValueQueryBySql(
            Class<E> resultClass,
            String sql,
            Object ... params);

    <E> Page<E> executePageEntityQueryBySql(
            Pageable page,
            Class<E> resultClass,
            String sql,
            Object... params);

    List<Map> executeMapQueryBySql(
            String sql,
            Object... params);

    Page<Map> executePageMapQueryBySql(
            Pageable page,
            String sql,
            Object... params);
//
//    /**
//     * 执行ql语句
//     *
//     * @param qlString 基于jpa标准的jpql语句
//     * @param params   key表示jpql中参数变量名，value表示该参数变量值
//     * @return 返回执行后受影响的数据个数
//     */
//    int executeUpdate(String qlString, Map params);
//
//    /**
//     * 执行ql语句，可以是更新或者删除操作
//     *
//     * @param qlString 基于jpa标准的jpql语句
//     * @param values   jpql中的?参数值
//     * @return 返回执行后受影响的数据个数
//     * @throws Exception
//     */
//    int executeUpdate(String qlString, List<Object> values);
//
//    /**
//     * 执行原生SQL语句，可以是更新或者删除操作
//     *
//     * @param sql 标准的sql语句
//     * @return 返回执行后受影响的数据个数
//     * @throws Exception
//     */
//    int executeBySQL(String sql);
//
//    int executeBySQL(String sql, Object... values);
//
//    /**
//     * jpql查询语句
//     *
//     * @param qlString 基于jpa标准的jpql语句
//     * @param values   jpql中的?参数值,单个参数值或者多个参数值
//     * @return 返回查询的数据集合
//     */
//    List<T> findAll(String qlString, Object... values);
//
//    List<T> findAll(String qlString, Map params);
//
//    List<T> findAll(@Nullable List<Condition> conditions);
//
//    boolean support(String modelType);
    //<editor-fold desc="Description">
}
