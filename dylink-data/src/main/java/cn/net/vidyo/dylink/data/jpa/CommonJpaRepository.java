package cn.net.vidyo.dylink.data.jpa;

import cn.net.vidyo.dylink.common.Result;
import org.springframework.lang.Nullable;
import cn.net.vidyo.dylink.data.domain.Condition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface CommonJpaRepository<T,ID extends Serializable> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {
//    <CONDITION extends ICondition> List<T> findAllByCondition(CONDITION condition);
//    <CONDITION extends ICondition> Page<T> pageFindAllByCondition(CONDITION condition, Integer page, Integer size);

    T getById(ID id);

//    T getModel(String where);
    Object getColumnById(ID id, String fieldName);
    String getNameById(ID id);
    List<T> findByIds(List<ID> ids);
    List<T> findByIds(ID[] ids);
    //long hiddenByIdIn(boolean hidden,Iterable<ID> ids);
    long deleteByIdIn(Iterable<ID> ids);

    T getByEntityId(T t);

    T updateColumnById(ID id, String fieldName, Object value);
    T updateStatusById(ID id, Object value);
    T updateHiddenById(ID id, Object value);

    T increaseColumnValueById(ID id, String fieldName, Object delta);
//    Result addModel(T t);
//    Result updateModel(T t);
//    Result saveModel(T t);
//    Result deleteModelById(ID id);


    int truncateParmeryKey();
    /**
     * 根据原生sql语句查询数据列表
     * @param sql
     * @return
     */
    List<Map> queryBySql(String sql);

    /**
     * 根据原生sql查询数据，返回指定对象列表
     * @param sql
     * @param klass
     * @return
     */
    List<?> queryBySql(String sql, Class<?> klass);
    /**
     * 根据原生sql查询对象列表
     * @param sql
     * @return
     */
    List<T> query(String sql);

    /**
     * 根据原生sql查询数组对象
     * @param sql
     * @return
     */
    Object getBySql(String sql, Class<?> klass);

    /**
     * 根据原生sql查询对象
     * @param sql
     * @return
     */
    T get(String sql);


    /**
     * 执行sql
     * @param sql
     * @return
     */
    int execute(String sql);

    /**
     * 获取数据类型
     * @return
     */
    Class<T> getEntityClass();


    // 通过EntityManager来完成查询
    List<Object[]> listBySQL(String sql);
    public List<Map<String, Object>> getData(String sql, Map<String, Object> params);

    public void save(Object... entitys);

    /**
     * 更新实体
     *
     * @param entitys 实体id
     */
    public void update(Object... entitys);

    /**
     * 删除实体
     *
     * @param entityClass 实体类
     * @param entityid    实体id
     */
    public <E> void delete(Class<T> entityClass, Object entityid);

    /**
     * 删除实体
     *
     * @param entityClass 实体类
     * @param entityids   实体id数组
     */
    public <E> void delete(Class<T> entityClass, Object[] entityids);

    /**
     * 获取实体
     *
     * @param entityClass 实体类
     * @param entityId   实体id
     * @return
     */
    public <E> T find(Class<T> entityClass, Object entityId);

    public T findOne(ID id);


    /**
     * 执行ql语句
     * @param qlString 基于jpa标准的jpql语句
     * @param values jpql中的?参数值,单个参数值或者多个参数值
     * @return 返回执行后受影响的数据个数
     */
    int executeUpdate(String qlString, Object... values);

    /**
     * 执行ql语句
     * @param qlString 基于jpa标准的jpql语句
     * @param params key表示jpql中参数变量名，value表示该参数变量值
     * @return 返回执行后受影响的数据个数
     */
    int executeUpdate(String qlString, Map<String, Object> params);

    /**
     * 执行ql语句，可以是更新或者删除操作
     * @param qlString 基于jpa标准的jpql语句
     * @param values jpql中的?参数值
     * @return 返回执行后受影响的数据个数
     * @throws Exception
     */
    int executeUpdate(String qlString, List<Object> values);

    /**
     * 执行原生SQL语句，可以是更新或者删除操作
     * @param sql 标准的sql语句
     * @return 返回执行后受影响的数据个数
     * @throws Exception
     */
    int executeBySQL(String sql);

    int executeBySQL(String sql, Object... values);

    /**
     * jpql查询语句
     * @param qlString 基于jpa标准的jpql语句
     * @param values jpql中的?参数值,单个参数值或者多个参数值
     * @return 返回查询的数据集合
     */
    List<T> findAll(String qlString, Object... values);

    List<T> findAll(String qlString, Map<String, Object> params);

    List<T> findAll(@Nullable List<Condition> conditions);

    boolean support(String modelType);
    //<editor-fold desc="Description">
}
