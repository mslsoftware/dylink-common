package cn.net.vidyo.dylink.data.jpa;

//import com.ecs.interaction.util.ListUtil;


import cn.net.vidyo.dylink.common.CommonEnumCodes;
import cn.net.vidyo.dylink.common.Result;
import cn.net.vidyo.dylink.data.domain.Condition;
import cn.net.vidyo.dylink.util.ListUtil;
import cn.net.vidyo.dylink.util.MapUtil;
import cn.net.vidyo.dylink.util.ObjectUtil;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class CommonJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CommonJpaRepository<T, ID> {

    private final EntityManager entityManager;
    JpaEntityInformation<T, ?> entityInformation;
    Class<T> entityClass;


    public CommonJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
        this.entityClass = entityInformation.getJavaType();
    }


    //<editor-fold desc="update">
    @Override
    public T updateStatusById(ID id, Object value) {
        return updateColumnById(id, "status", value);
    }

    @Override
    public T updateHiddenById(ID id, Object value) {
        return updateColumnById(id, "hidden", value);
    }

    @Override
    public T updateColumnById(ID id, String fieldName, Object value) {
        T t = getById(id);
        if (t == null) return null;
        final BeanWrapper wrapper = new BeanWrapperImpl(t);
        wrapper.setPropertyValue(fieldName, value);
        return save(t);
    }

    public T increaseColumnValueById(ID id, String fieldName, Object delta) {
        T t = getById(id);
        if (t == null) return null;
        ObjectUtil.increaseColumnValue(t, fieldName, delta);
        return save(t);
    }

    //</editor-fold>
    //<editor-fold desc="delete">
    @Override
    public long deleteByIdIn(Iterable<ID> ids) {
        // Find all domains
        List<T> domains = findAllById(ids);
        // Delete in batch
        deleteInBatch(domains);

        // Return the size of domain deleted
        return domains.size();
    }

    //</editor-fold>
    //<editor-fold desc="Query">
    List<T> findByWhere(String where,Object... params){
        StringBuilder sql=new StringBuilder();
        sql.append(" FROM ");
        sql.append(getTableName());
        if(where!=null && !where.isEmpty()){
            sql.append(" WHERE ");
            sql.append(where);
        }
        Query query = entityManager.createQuery(sql.toString());// 这里做个更正
        if(params!=null){
            for(int index=1;index<=params.length;index++){
                //query.setParameter(name ,keyword); //根据属性
                query.setParameter(index,params[index-1]); //根据下标
            }
        }
        //查询
        return query.getResultList();
    }
//    List<ID> findIdsByWhere(String where,Object... params){
//        StringBuilder sql=new StringBuilder();
//        sql.append(" FROM ");
//        sql.append(getTableName());
//        if(where!=null && !where.isEmpty()){
//            sql.append(" WHERE ");
//            sql.append(where);
//        }
//        Query query = entityManager.createQuery(sql.toString());// 这里做个更正
//        if(params!=null){
//            for(int index=1;index<=params.length;index++){
//                //query.setParameter(name ,keyword); //根据属性
//                query.setParameter(index,params[index-1]); //根据下标
//            }
//        }
//        //查询
//        return query. .getResultList();
//    }
//    List<T> pageByWhere(Pageable pageable, String where, Object... params){
//        StringBuilder sql=new StringBuilder();
//        sql.append(" FROM ");
//        sql.append(getTableName());
//        if(where!=null && !where.isEmpty()){
//            sql.append(" WHERE ");
//            sql.append(where);
//        }
//        Query query = entityManager.createQuery(sql.toString());// 这里做个更正
//        if(params!=null){
//            for(int index=1;index<=params.length;index++){
//                //query.setParameter(name ,keyword); //根据属性
//                query.setParameter(index,params[index-1]); //根据下标
//            }
//        }
//        return (Page)(isUnpaged(pageable) ? new PageImpl(query.getResultList()) : this.readPage(query, this.getDomainClass(), pageable, spec));
//
//        //查询
//        return query.getResultList();
//    }


    //<editor-fold desc="get entity">
    @Override
    public T getById(ID id) {
        Optional<T> optional = findById(id);
        if (!optional.isPresent()) {
            return null;
        }
        return optional.get();
    }

    protected ID getIdByEntity(T t) {
        return (ID) ObjectUtil.getFieldValueByFieldName(t, "id");
    }

    @Override
    public T getByEntityId(T t) {
        ID id = getIdByEntity(t);
        return getById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public int truncateParmeryKey(){
        StringBuilder sql=new StringBuilder();
        sql.append("TRUNCATE TABLE ");
        sql.append(getTableName());
        Query query = entityManager.createNativeQuery(sql.toString());
        return query.executeUpdate();
    }


    //</editor-fold>
    //<editor-fold desc="get column">
    @Override
    public Object getColumnById(ID id, String fieldName) {
        T t = getById(id);
        return ObjectUtil.getFieldValueByFieldName(t, fieldName);
    }

    @Override
    public String getNameById(ID id) {
        Object value = getColumnById(id, "name");
        if (value == null) {
            return "";
        }
        return value.toString();
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

    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="sql">
    @Override
    public List<Map> queryBySql(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.getResultList();
        return list;
    }

    @Override
    public List<?> queryBySql(String sql, Class<?> klass) {
        List<Map> list = queryBySql(sql);
        if (list.isEmpty()) {
            return null;
        }
        List result = ListUtil.newArrayList();
        for (Map map : list) {
            try {
                Object bean = MapUtil.mapToBean(map, klass);
                result.add(bean);
            } catch (Exception e) {
            }
        }
        return result;
    }

    @Override
    public T get(String sql) {
        List<T> list = entityManager.createNativeQuery(sql, entityClass).getResultList();
        return list.get(0);
    }

    @Override
    public int execute(String sql) {
        return entityManager.createNativeQuery(sql).executeUpdate();
    }


    @Override
    public List<T> query(String sql) {
        return entityManager.createNativeQuery(sql, entityClass).getResultList();
    }

    @Override
    public Object getBySql(String sql, Class<?> klass) {
        List list = queryBySql(sql, klass);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    //</editor-fold>


    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }


//    @Override
//    public Result addModel(T t) {
//        T save = save(t);
//        if (save == null) {
//            return Result.fail(CommonEnumCodes.DB_ACCESS_EXCEPTION);
//        }
//        return Result.ok(save);
//    }
//
//    @Override
//    public Result updateModel(T t) {
//        T entity = getByEntityId(t);
//        if (entity == null) {
//            return Result.fail(CommonEnumCodes.NOT_EXIST);
//        }
//        T save = save(entity);
//        if (save == null) {
//            return Result.fail(CommonEnumCodes.DB_ACCESS_EXCEPTION);
//        }
//        return Result.ok(save);
//    }
//
//    @Override
//    public Result saveModel(T t) {
//        T entity = getByEntityId(t);
//        if (entity == null) {
//            return addModel(t);
//        }
//        return updateModel(t);
//    }
//
//    @Override
//    public Result deleteModelById(ID id) {
//        T entity = getById(id);
//        if (entity == null) {
//            return Result.fail(CommonEnumCodes.NOT_EXIST);
//        }
//        deleteById(id);
//        return Result.ok();
//    }
//    @Override
//    public long hiddenByIdIn(boolean hidden,Iterable<ID> ids) {
//        // Find all domains
//        List<T> domains = findAllById(ids);
//        for (T domain : domains) {
//
//        }
//        // Delete in batch
//        deleteInBatch(domains);
//
//        // Return the size of domain deleted
//        return domains.size();
//    }


//    @Override
//    public List<T> findList(String whereClause, int pageIndex, int recordPerPage, Object... paramVarArgs) {
//
//        try {
//            String jql = "select p from " + this.getGenericClass().getSimpleName() + " p ";
//            if (StringUtils.isNotBlank(whereClause)) {
//                if (whereClause.toLowerCase().trim().startsWith("order ")) {
//                    jql = jql + " " + whereClause;
//                } else {
//                    jql = jql + " where " + whereClause;
//                }
//
//            }
//            TypedQuery<T> typedQuery = entityManager.createQuery(jql, this.getGenericClass());
//            if (paramVarArgs != null) {
//                for (int i = 0; i < paramVarArgs.length; i++) {
//                    typedQuery.setParameter(i + 1, paramVarArgs[i]);
//                }
//            }
//            if (recordPerPage > 0) {
//                if (pageIndex <= 0)
//                    pageIndex = 1;
//                typedQuery.setFirstResult((pageIndex - 1) * recordPerPage);
//                typedQuery.setMaxResults(recordPerPage);
//            }
//            List<T> results = typedQuery.getResultList();
//            return results;
//        } catch (Exception ex) {
//            throw new CatchRuntimeException("find List error", ex);
//        }
//
//    }


//    @Override
//    public <CONDITION extends ICondition> List<T> findAllByCondition(CONDITION condition) {
//        return findAll(this.getWhereClause(condition));
//    }
//
//    @Override
//    public <CONDITION extends ICondition> Page<T> pageFindAllByCondition(CONDITION condition, Integer page, Integer size) {
//        Sort sort=Sort.by(Sort.Direction.ASC,"id");
//        Pageable pageable= PageRequest.of(page-1,size,sort);
//        return  findAll(this.getWhereClause(condition),pageable);
//    }
//
//
//    public <CONDITION extends ICondition> Specification<T> getWhereClause(final CONDITION condition) {
//        return new Specification<T>() {
//            @Override
//            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                try {
//                    return conditionToPredicate(condition,root,query,cb);
//                } catch (NotSupportedException e) {
//                    return cb.conjunction();
//                }
//            }
//        };
//    }
//
//    protected <CONDITION extends ICondition> Predicate conditionToPredicate(CONDITION condition,Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) throws NotSupportedException {
//        throw new NotSupportedException();
//    }
//
//    private static final class ByIdsSpecification<T> implements Specification<T> {
//        private static final long serialVersionUID = 1L;
//        private final JpaEntityInformation<T, ?> entityInformation;
//        @Nullable
//        ParameterExpression<Iterable> parameter;
//
//        ByIdsSpecification(JpaEntityInformation<T, ?> entityInformation) {
//            this.entityInformation = entityInformation;
//        }
//
//        @Override
//        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//            Path<?> path = root.get(this.entityInformation.getIdAttribute());
//            this.parameter = cb.parameter(Iterable.class);
//            return path.in(this.parameter);
//        }
//    }
protected String getTableName(){
    String name="";
    Table tableAnnotation = (Table) entityClass.getAnnotation(Table.class);
    if(tableAnnotation!=null){
        name= tableAnnotation.name();
    }
    if(name!=null && !name.isEmpty()){
        return name;
    }
    Entity entityAnnotation = (Entity) entityClass.getAnnotation(Entity.class);
    if(entityAnnotation !=null){
        name=entityAnnotation.name();
    }
    if(name!=null && !name.isEmpty()){
        return name;
    }
    return entityClass.getSimpleName();
}
    //<editor-fold desc="Description">
    @Override
    public List<Object[]> listBySQL(String sql) {
        return entityManager.createNativeQuery(sql).getResultList();
    }

    public List<Map<String, Object>> getData(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if(params!=null)
            for (String name : params.keySet()) {
                query.setParameter(name, params.get(name));
            }
        return query.getResultList();
    }

    @Override
    public void save(Object... entities) {
        if (null != entities) {
            for (Object entity : entities) {
                entityManager.persist(entity);
            }
        }
    }

    @Override
    public void update(Object... entities) {
        if (null != entities) {
            for (Object entity : entities) {
                entityManager.merge(entity);
            }
        }
    }

    public <E> void delete(Class<T> entityClass, Object entityid) {
        delete(entityClass, new Object[] { entityid });
    }

    @Override
    public <E> void delete(Class<T> entityClass, Object[] entityids) {
        for (Object id : entityids) {
            entityManager.remove(entityManager.getReference(entityClass, id));
        }
    }

    public <E> T find(Class<T> entityClass, Object entityId) {
        return entityManager.find(entityClass, entityId);
    }

    public T findOne(ID id) {
        Optional<T> entityById=findById(id);
        if(entityById.isPresent())
            return entityById.get();
        return null;
    }

    @Override
    public boolean support(String modelType) {
        System.out.println(modelType+"###"+entityInformation.getEntityName());
        return entityInformation.getEntityName().equals(modelType);
    }

    @Override
    public int executeUpdate(String sql, Object... values) {
        Query query = entityManager.createQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i + 1, values[i]);
            }
        }
        return query.executeUpdate();
    }

    @Override
    public int executeUpdate(String sql, Map<String, Object> params) {
        Query query = entityManager.createQuery(sql);
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        return query.executeUpdate();
    }

    @Override
    public int executeUpdate(String sql, List<Object> values) {
        Query query = entityManager.createQuery(sql);
        for (int i = 0; i < values.size(); i++) {
            query.setParameter(i + 1, values.get(i));
        }
        return query.executeUpdate();
    }

    @Override
    public int executeBySQL(String sql) {
        return entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    public int executeBySQL(String sql,Object... values) {
        Query query = entityManager.createNativeQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i + 1, values[i]);
            }
        }
        return query.executeUpdate();
    }

    @Override
    public List<T> findAll(String sql, Object... values) {
        Query query = entityManager.createQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i + 1, values[i]);
            }
        }
        return query.getResultList();
    }


    @Override
    public List<T> findAll(String sql, Map<String, Object> params) {
        Query query = entityManager.createQuery(sql);
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        return query.getResultList();
    }


    @Override
    public List<T> findAll(Sort sort) {
        // TODO 这是系统自动生成描述，请在此补完后续代码
        return super.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        // TODO 这是系统自动生成描述，请在此补完后续代码
        return super.findAll(pageable);
    }

    @Override
    public List<T> findAll(List<Condition> conditions) {
        // TODO Auto-generated method stub
        return findAll(SqlHelper.where(conditions));
    }
    //</editor-fold>



}
