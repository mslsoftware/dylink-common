package cn.net.vidyo.dylink.data.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public interface EntityDao <T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    //<editor-fold desc="insert">
    T insert(T t);

    //</editor-fold>
    //<editor-fold desc="update">
    //<editor-fold desc="update model">
    T udpate(T t,String where, Object... params);
    //</editor-fold>
    //<editor-fold desc="update column">

    /**
     * 更新字段
     * @param t 对象
     * @param exclude 是否排除
     * @param columns 字段名称列表
     * @return
     */
    int udpateColumns(T t,boolean exclude,String[] columns,String where, Object... params);

    /**
     * 更新一个字段
     * @param columnName 字段名称
     * @param value 字段值
     * @return
     */
    int udpateColumn(String columnName,Object value,String where, Object... params);

    /**
     * 递增 递减字段
     * @param columnName 字段名称
     * @param delta  增量/减量
     * @return
     */
    int incrementalColumn(String columnName,Object delta,String where, Object... params);

    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="delete">
    int deleteByWhere(String where, Object... params);
    //</editor-fold>
    //<editor-fold desc="query">
    //<editor-fold desc="get column">
    <C> C getColumnValueByWhere(String columnName,Class<C> cClass,String where, Object... params);
    //</editor-fold>
    //<editor-fold desc="get model">
    T getModelByWhere(String where, Object... params);

    //</editor-fold>
    //<editor-fold desc="query column">
    <C> List<C> queryColumnValueByWhere(String columnName,Class<C> cClass, String where, Object... params);

    //</editor-fold>
    //<editor-fold desc="query model">
    List<T> queryModelByWhere(String where, Object... params);

    //</editor-fold>
    //<editor-fold desc="page query column">
    <C> Page<C> pageColumnValueByWhere(Pageable pageable,String columnName,Class<C> cClass, String where, Object... params);

    //</editor-fold>
    //<editor-fold desc="page query model">
    Page<T> pageModelByWhere(Pageable pageable, String where, Object... params);

    //</editor-fold>

    //</editor-fold>

    String getTableName();
    Class<T> getEntityClass();
}
