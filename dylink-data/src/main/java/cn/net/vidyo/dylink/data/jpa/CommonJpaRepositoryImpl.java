package cn.net.vidyo.dylink.data.jpa;

import cn.net.vidyo.dylink.data.domain.Condition;
import cn.net.vidyo.dylink.data.jpa.sql.QueryWhere;
import cn.net.vidyo.dylink.data.jpa.support.DefaultEntityEventCallback;
import cn.net.vidyo.dylink.util.ObjectUtil;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

public class CommonJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements EntityEventCallback, CommonJpaRepository<T, ID> {
    static int BATCH_SIZE=10000;
    private final EntityManager entityManager;
    JpaEntityInformation<T, ?> entityInformation;
    Class<T> entityClass;
    EntityEventCallback defaultEntityEventCallback;


    public CommonJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
        this.entityClass = entityInformation.getJavaType();
        defaultEntityEventCallback=new DefaultEntityEventCallback();
    }

    //<editor-fold desc="Extent">
    //<editor-fold desc="update">
    @Override
    public int updateStatusById(ID id, Object value) {
        return updateColumnById(id, "status", value);
    }

    @Override
    public int updateHiddenById(ID id, Object value) {
        return updateColumnById(id, "hidden", value);
    }

    @Override
    public int updateColumnById(ID id, String fieldName, Object value) {
        return updateColumn(fieldName,value,new QueryWhere().addIdWhere(id));
    }

    public int increaseColumnValueById(ID id, String fieldName, int delta) {
        return increaseColumn(fieldName,delta,"id=?",id);
    }

    //</editor-fold>
    //<editor-fold desc="delete">
    @Override
    public int deleteByIds(Iterable<ID> ids) {
        return deleteByWhere(new QueryWhere().addInWhere("id",ids));
    }

    public int deleteByIds(ID ... ids) {
        return deleteByWhere(new QueryWhere().addInWhere("id",ids));
    }

    @Override
    public void deleteAll() {
        deleteByWhere(new QueryWhere());
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        List<ID> ids = new ArrayList<>();
        for (T entity : entities) {
            ids.add(getIdByEntity(entity));
        }
        deleteByWhere(new QueryWhere().addInWhere("id",ids));
    }

    //</editor-fold>
    //<editor-fold desc="Query">
    //<editor-fold desc="get entity">
    @Override
    public T getById(ID id) {
        Optional<T> optional = findById(id);
        if (!optional.isPresent()) {
            return null;
        }
        T t = optional.get();
        invokeEvent(t,Event.PostLoad);
        return t;
    }

    public ID getIdByEntity(T t) {
        return (ID) ObjectUtil.getFieldValueByFieldName(t, "id");
    }

    @Override
    public T getByEntityId(T t) {
        ID id = getIdByEntity(t);
        return getById(id);
    }




    //</editor-fold>
    //<editor-fold desc="get column">
    @Override
    public Object getColumnById(ID id, String fieldName) {
        return getColumn(fieldName,new QueryWhere().addIdWhere(id));
    }
    public String getStringColumnById(ID id, String fieldName) {
        Object value = getColumnById(id, fieldName);
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    @Override
    public String getNameById(ID id) {
        return getStringColumnById(id,"name");
    }
    public String getIdKeyById(ID id) {
        return getStringColumnById(id,"idkey");
    }
    public String getCodeById(ID id) {
        return getStringColumnById(id,"code");
    }

    //</editor-fold>
    //<editor-fold desc="find list">
    @Override
    public List<T> findByIds(List<ID> ids) {
        return this.findAllById(ids);
    }

    @Override
    public List<T> findByIds(ID[] ids) {
        List list = Arrays.asList(ids);
        return findByIds(list);
    }
    public Page<T> pageAll(int pageNumber,int pageSize){
        return pageAll(PageRequest.of(pageNumber,pageSize));
    }
    public Page<T> pageAll(){
        return pageAll(1,100000);
    }
    public Page<T> pageAll(Pageable pageable){
        return pageQuery(pageable,new QueryWhere());
    }
    public List<T> findAll(){
        return query(new QueryWhere());
    }
    //</editor-fold>
    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="Base">
    //<editor-fold desc="update">
    @Transactional
    public int increaseColumn(String columName,int delta,String where,Object... params){
        StringBuilder sql= new StringBuilder();
        sql.append(columName);
        sql.append("=");
        sql.append(columName);
        sql.append("+ ?");
        String sql1 = buildUpdateSql(sql.toString(), where);
        List list = new ArrayList();
        list.add(delta);
        for (Object param : params) {
            list.add(param);
        }
        return executeSql(sql1,list.toArray());
    }
    @Transactional
    public int increaseColumn(String columName,int delta,QueryWhere where){
        return increaseColumn(columName, delta, where.getWhere(),where.getParams().toArray());
    }
    @Transactional
    public int updateColumn(String columName,Object value,String where,Object... params){
        return updateColumn(columName,value,new QueryWhere(where, params));
    }
    @Transactional
    public int updateColumn(String columName,Object value,QueryWhere where){
        Map<String,Object> columnNameValues=new HashMap<>();
        columnNameValues.put(columName,value);
        return updateColumns(columnNameValues,where);
    }
    @Transactional
    public int updateColumns(Map<String,Object> columnNameValues,String where,Object... params){
        return updateColumns(columnNameValues,new QueryWhere(where, params));
    }
    @Transactional
    public int updateColumns(Map<String,Object> columnNameValues,QueryWhere where){
        String sql = buildUpdateSql(columnNameValues, where.getWhere());
        return executeSql(sql,where.getParams().toArray());
    }
    @Transactional
    public <S extends T> Iterable<S> batchUpdate(Iterable<S> var1) {
        Iterator<S> iterator = var1.iterator();
        int index = 0;
        while (iterator.hasNext()){
            S entity = iterator.next();
            invokeEvent(entity,Event.PreUpdate);
            entityManager.merge(entity);
            invokeEvent(entity,Event.PostUpdate);
            index++;
            if (index % BATCH_SIZE == 0){
                entityManager.flush();
                entityManager.clear();
            }
        }
        if (index % BATCH_SIZE != 0){
            entityManager.flush();
            entityManager.clear();
        }
        return var1;
    }
    //</editor-fold>
    //<editor-fold desc="insert">
    @Transactional
    public <S extends T> S save(S entity) {
        if (this.entityInformation.isNew(entity)) {
            invokeEvent(entity,Event.PrePersist);
            this.entityManager.persist(entity);
            invokeEvent(entity,Event.PostPersist);
            return entity;
        } else {
            invokeEvent(entity,Event.PreUpdate);
            S merge = this.entityManager.merge(entity);
            invokeEvent(merge,Event.PostUpdate);
            return merge;
        }
    }
    @Transactional
    public <S extends T> S saveAndFlush(S entity) {
        S result = this.save(entity);
        this.flush();
        return result;
    }
    @Transactional
    public <S extends T> Iterable<S> batchSave(Iterable<S> var1) {
        Iterator<S> iterator = var1.iterator();
        int index = 0;
        while (iterator.hasNext()){
            S entity = iterator.next();
            invokeEvent(entity,Event.PrePersist);
            entityManager.persist(entity);
            invokeEvent(entity,Event.PostPersist);
            index++;
            if (index % BATCH_SIZE == 0){
                entityManager.flush();
                entityManager.clear();
            }
        }
        if (index % BATCH_SIZE != 0){
            entityManager.flush();
            entityManager.clear();
        }
        return var1;
    }
    //</editor-fold>
    //<editor-fold desc="delete">
    @Transactional
    public int deleteByWhere(String where,Object... params) {
        String sql=buildDeleteSql(where);
        return executeSql(sql,params);
    }
    @Transactional
    public int deleteByWhere(QueryWhere where) {
        String sql=buildDeleteSql(where.getWhere());
        return executeSql(sql,where.getParams().toArray());
    }

    //</editor-fold>
    //<editor-fold desc="query">

    //<editor-fold desc="query object">
    public Object getColumn(String columnName,QueryWhere where) {
        if(where.getSelect().length()==1){
            where.setSelect(columnName);
        }
        Map<String, Object> map = getMap(where);
        if(map==null) return null;
        if(map.containsKey(columnName)){
            return map.get(columnName);
        }
        return null;
    }
    public T getModel(QueryWhere where) {
        String sql = buildQuerySql(where);
        return getBySql(sql,where.getParams().toArray());
    }

    public Map<String, Object> getMap(QueryWhere where) {
        String sql = buildQuerySql(where);
        return getMapBySql(sql,where.getParams().toArray());
    }

    //</editor-fold>
    //<editor-fold desc="query object list">
    public List<T> query(QueryWhere where) {
        String sql = buildQuerySql(where);
        return queryBySql(sql,where.getParams().toArray());
    }

    public List<Map<String, Object>> queryMap(QueryWhere where) {
        String sql = buildQuerySql(where);
        return queryMapBySql(sql,where.getParams().toArray());
    }

    //</editor-fold>
    //<editor-fold desc="query object page">
    public Page<T> pageQuery(Pageable pageable, String where, Object... params) {
        return pageSelectQuery(pageable, "*", where, params);
    }
    public Page<T> pageQuery(Pageable pageable, QueryWhere where){
        String sql = buildQuerySql(where.getSelect(), where.getWhere());
        return pageBySql(pageable,sql,where.getParams().toArray());
    }
    public Page<T> pageSelectQuery(Pageable pageable, String select, String where, Object... params) {
        String sql = buildQuerySql(select, where);
        return pageBySql(pageable,sql,params);
    }

    public Page<Map<String, Object>> pageQueryMap(Pageable pageable, String where, Object... params) {
        return pageQueryMap(pageable, new QueryWhere(where,params));
    }
    public Page<Map<String, Object>> pageQueryMap(Pageable pageable, QueryWhere where) {
        String sql = buildQuerySql(where.getSelect(), where.getWhere());
        return pageQueryMap(pageable,sql,where.getParams().toArray());
    }
    public Page<Map<String, Object>> pageSelectQueryMap(Pageable pageable,String select, String where, Object... params) {
        String sql = buildQuerySql(select, where);
        return pageMapBySql(pageable,sql,params);
    }

    public Page<T> pageQuery(int pageNumber, int pageSize, String where, Object... params) {
        return pageSelectQuery(pageNumber,pageSize,"*",where,params);
    }
    public Page<T> pageQuery(int pageNumber, int pageSize,QueryWhere where) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        return pageSelectQuery(pageable,where.getSelect(),where.getWhere(),where.getParams().toArray());
    }
    public Page<T> pageSelectQuery(int pageNumber, int pageSize,String select, String where, Object... params) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        return pageSelectQuery(pageable,select,where,params);
    }
    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="query condition">

    //<editor-fold desc="query object">
    public Object getColumn(String columnName, Condition condition, ConditionCompose iConditionCompose) {
        QueryWhere queryWhere = iConditionCompose.buildWhere(condition);
        return getColumn(columnName,queryWhere);
    }
    public T getModel(Condition condition, ConditionCompose iConditionCompose) {
        QueryWhere queryWhere = iConditionCompose.buildWhere(condition);
        return getModel(queryWhere);
    }

    public Map<String, Object> getMap(Condition condition, ConditionCompose iConditionCompose) {
        QueryWhere queryWhere = iConditionCompose.buildWhere(condition);
        return getMap(queryWhere);
    }

    //</editor-fold>
    //<editor-fold desc="query object list">
    public List<T> query(Condition condition, ConditionCompose iConditionCompose) {
        QueryWhere queryWhere = iConditionCompose.buildWhere(condition);
        return query(queryWhere);
    }

    public List<Map<String, Object>> queryMap(Condition condition, ConditionCompose iConditionCompose) {
        QueryWhere queryWhere = iConditionCompose.buildWhere(condition);
        return queryMap(queryWhere);
    }

    //</editor-fold>
    //<editor-fold desc="query object page">
    public Page<T> pageQuery(Pageable pageable, Condition condition, ConditionCompose iConditionCompose) {
        QueryWhere queryWhere = iConditionCompose.buildWhere(condition);
        return pageQuery(pageable, queryWhere);
    }
    public Page<T> pageQuery(int pageNumber, int pageSize, Condition condition, ConditionCompose iConditionCompose) {
        QueryWhere queryWhere = iConditionCompose.buildWhere(condition);
        return pageQuery(pageNumber,pageSize,queryWhere);
    }
    public Page<Map<String, Object>> pageQueryMap(Pageable pageable, Condition condition, ConditionCompose iConditionCompose) {
        QueryWhere queryWhere = iConditionCompose.buildWhere(condition);
        return pageQueryMap(pageable, queryWhere);
    }
    public Page<Map<String, Object>> pageQueryMap(int pageNumber, int pageSize, Condition condition, ConditionCompose iConditionCompose) {
        Pageable pageable=PageRequest.of(pageNumber,pageSize);
        QueryWhere queryWhere = iConditionCompose.buildWhere(condition);
        return pageQueryMap(pageable,queryWhere);
    }

    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="sql">
    @Transactional(readOnly = false)
    @Override
    public int truncateParmeryKey() {
        StringBuilder sql = new StringBuilder();
        sql.append("TRUNCATE TABLE ");
        sql.append(getTableName());
        Query query = entityManager.createNativeQuery(sql.toString());
        return query.executeUpdate();
    }

    @Transactional(readOnly = false)
    public int dropTable() {
        StringBuilder sql = new StringBuilder();
        sql.append("DROP TABLE ");
        sql.append(getTableName());
        Query query = entityManager.createNativeQuery(sql.toString());
        return query.executeUpdate();
    }

    public int executeSql(String sql,Object ... params){
        Query query = entityManager.createNativeQuery(sql);
        if (params != null) {
            for (int index = 0; index < params.length; index++) {
                query.setParameter(index, params[index]);
            }
        }
        return query.executeUpdate();
    }
    public T getBySql(String sql,Object ... params) {
        List<T> list = queryBySql(sql, params);
        if(list==null || list.size()==0) return null;
        return list.get(0);
    }

    public Map<String, Object> getMapBySql(String sql,Object ... params) {
        List<Map<String, Object>> maps = queryMapBySql(sql, params);
        if(maps==null || maps.size()==0) return null;
        return maps.get(0);
    }

    public List<T> queryBySql(String sql,Object ... params)
    {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null) {
            for (int index = 0; index < params.length; index++) {
                query.setParameter(index, params[index]);
            }
        }
        List<T> resultList = query.getResultList();
        return resultList;
    }

    public List<Map<String, Object>> queryMapBySql(String sql,Object ... params) {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null) {
            for (int index = 0; index < params.length; index++) {
                query.setParameter(index, params[index]);
            }
        }
        List<T> resultList = query.getResultList();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = query.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    public Page<T> pageBySql(Pageable pageable, String sql,Object ... params)
    {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null) {
            for (int index = 0; index < params.length; index++) {
                query.setParameter(index, params[index]);
            }
        }
        List<T> resultList = query.getResultList();
        int totalRows = resultList.size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        Page<T> result = new PageImpl<T>(query.getResultList(), pageable, totalRows);
        return result;
    }

    public Page<Map<String, Object>> pageMapBySql(Pageable pageable,String sql,Object ... params) {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null) {
            for (int index = 0; index < params.length; index++) {
                query.setParameter(index, params[index]);
            }
        }
        List<T> resultList = query.getResultList();
        int totalRows = resultList.size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = query.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        Page<Map<String, Object>> result = new PageImpl<Map<String, Object>>(list, pageable, totalRows);
        return result;
    }
    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="method">
    String buildQuerySql(QueryWhere where){
        return buildQuerySql(where.getSelect(),where.getWhere());
    }
    String buildQuerySql(String select, String where) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(select);
        sql.append(" FROM ");
        sql.append(getTableName());
        if (where != null && !where.isEmpty()) {
            sql.append(" WHERE ");
            sql.append(where);
        }
        return sql.toString();
    }
    String buildDeleteSql(String where) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE ");
        sql.append(" FROM ");
        sql.append(getTableName());
        if (where != null && !where.isEmpty()) {
            sql.append(" WHERE ");
            sql.append(where);
        }
        return sql.toString();
    }
    String buildUpdateSql(Map<String,Object> columnNameValues, String where) {
        StringBuilder kvs = new StringBuilder();
        for (String key : columnNameValues.keySet()) {
            if(kvs.length()>0){
                kvs.append(",");
            }
            kvs.append(key);
            kvs.append(" =? ");
        }
        return buildUpdateSql(kvs.toString(),where);
    }
    String buildUpdateSql(String setString, String where) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(" FROM ");
        sql.append(getTableName());
        if(setString.length()>0){
            sql.append(" SET ");
            sql.append(setString);
        }
        if (where != null && !where.isEmpty()) {
            sql.append(" WHERE ");
            sql.append(where);
        }
        return sql.toString();
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }
    //</editor-fold>

    protected String getTableName() {
        String name = "";
        Table tableAnnotation = (Table) entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            name = tableAnnotation.name();
        }
        if (name != null && !name.isEmpty()) {
            return name;
        }
        Entity entityAnnotation = (Entity) entityClass.getAnnotation(Entity.class);
        if (entityAnnotation != null) {
            name = entityAnnotation.name();
        }
        if (name != null && !name.isEmpty()) {
            return name;
        }
        return entityClass.getSimpleName();
    }

    //<editor-fold desc="base sql">

//    @Override
//    public boolean support(String modelType) {
//        System.out.println(modelType + "###" + entityInformation.getEntityName());
//        return entityInformation.getEntityName().equals(modelType);
//    }
//
//    @Override
//    public int executeUpdate(String sql, Object... values) {
//        Query query = entityManager.createQuery(sql);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i + 1, values[i]);
//            }
//        }
//        return query.executeUpdate();
//    }
//
//    @Override
//    public int executeUpdate(String sql, Map<String, Object> params) {
//        Query query = entityManager.createQuery(sql);
//        for (String name : params.keySet()) {
//            query.setParameter(name, params.get(name));
//        }
//        return query.executeUpdate();
//    }
//
//    @Override
//    public int executeUpdate(String sql, List<Object> values) {
//        Query query = entityManager.createQuery(sql);
//        for (int i = 0; i < values.size(); i++) {
//            query.setParameter(i + 1, values.get(i));
//        }
//        return query.executeUpdate();
//    }
//
//    @Override
//    public int executeBySQL(String sql) {
//        return entityManager.createNativeQuery(sql).executeUpdate();
//    }
//
//    @Override
//    public int executeBySQL(String sql, Object... values) {
//        Query query = entityManager.createNativeQuery(sql);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i + 1, values[i]);
//            }
//        }
//        return query.executeUpdate();
//    }
//
//    @Override
//    public List<T> findAll(String sql, Object... values) {
//        Query query = entityManager.createQuery(sql);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i + 1, values[i]);
//            }
//        }
//        return query.getResultList();
//    }
//
//
//    @Override
//    public List<T> findAll(String sql, Map<String, Object> params) {
//        Query query = entityManager.createQuery(sql);
//        for (String name : params.keySet()) {
//            query.setParameter(name, params.get(name));
//        }
//        return query.getResultList();
//    }
//
//
//    @Override
//    public List<T> findAll(Sort sort) {
//        // TODO 这是系统自动生成描述，请在此补完后续代码
//        return super.findAll(sort);
//    }
//
//    @Override
//    public Page<T> findAll(Pageable pageable) {
//        // TODO 这是系统自动生成描述，请在此补完后续代码
//        return super.findAll(pageable);
//    }
//
//    @Override
//    public List<T> findAll(List<Condition> conditions) {
//        // TODO Auto-generated method stub
//        return findAll(SqlHelper.where(conditions));
//    }


    //</editor-fold>
    //<editor-fold desc="Events">
    @Override
    public void batchInvokeEvent(Collection targets, Event event) {
        defaultEntityEventCallback.batchInvokeEvent(targets, event);
    }

    @Override
    public void invokeEvent(Object target, Event event) {
        defaultEntityEventCallback.invokeEvent(target, event);
    }
    //</editor-fold>

}
