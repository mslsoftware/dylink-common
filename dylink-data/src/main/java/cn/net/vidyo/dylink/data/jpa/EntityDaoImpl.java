package cn.net.vidyo.dylink.data.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

public class EntityDaoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements EntityDao<T, ID> {
    //<editor-fold desc="prpperty">
    private final EntityManager entityManager;
    JpaEntityInformation<T, ?> entityInformation;
    Class<T> entityClass;
    //</editor-fold>
    public EntityDaoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
        this.entityClass = entityInformation.getJavaType();
    }

    //<editor-fold desc="insert">
    public T insert(T t){
        return save(t);
    }
    //</editor-fold>
    //<editor-fold desc="update">
    //<editor-fold desc="update model">
    public T udpate(T t,String where, Object... params){
        return save(t);
    }
    //</editor-fold>
    //<editor-fold desc="update column">

    /**
     * 更新字段
     * @param t 对象
     * @param exclude 是否排除
     * @param columns 字段名称列表
     * @return
     */
    public int udpateColumns(T t,boolean exclude,String[] columns,String where, Object... params){
        return 0;
    }

    /**
     * 更新一个字段
     * @param columnName 字段名称
     * @param value 字段值
     * @return
     */
    public int udpateColumn(String columnName,Object value,String where, Object... params){
        return 0;
    }

    /**
     * 递增 递减字段
     * @param columnName 字段名称
     * @param delta  增量/减量
     * @return
     */
    public int incrementalColumn(String columnName,Object delta,String where, Object... params){
        return 0;
    }

    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="delete">
    public int deleteByWhere(String where, Object... params){
        return 0;
    }
    //</editor-fold>
    //<editor-fold desc="query">
    //<editor-fold desc="get column">
    public <C> C getColumnValueByWhere(String columnName,Class<C> cClass,String where, Object... params){
        return null;
    }
    //</editor-fold>
    //<editor-fold desc="get model">
    public T getModelByWhere(String where, Object... params){
        return null;
    }

    //</editor-fold>
    //<editor-fold desc="query column">
    public <C> List<C> queryColumnValueByWhere(String columnName, Class<C> cClass, String where, Object... params){
        return null;
    }

    //</editor-fold>
    //<editor-fold desc="query model">
    public List<T> queryModelByWhere(String where, Object... params){
        return null;
    }

    //</editor-fold>
    //<editor-fold desc="page query column">
    public <C> Page<C> pageColumnValueByWhere(Pageable pageable, String columnName, Class<C> cClass, String where, Object... params){
        return null;
    }

    //</editor-fold>
    //<editor-fold desc="page query model">
    public Page<T> pageModelByWhere(Pageable pageable, String where, Object... params){
        return null;
    }

    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="method">
    String buildSql(String select, String where){
        StringBuilder sql=new StringBuilder();
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
    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    public String getTableName() {
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
    //</editor-fold>
}
