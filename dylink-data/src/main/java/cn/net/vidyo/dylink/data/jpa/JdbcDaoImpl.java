package cn.net.vidyo.dylink.data.jpa;

import cn.net.vidyo.dylink.data.domain.IModel;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JdbcDaoImpl<ID extends Serializable, ENTITY extends IModel<ID>>
        implements
        JdbcDao<ID, ENTITY> {
    //<editor-fold desc="EntityManager">
    private Class<ENTITY> entityClass;
    private EntityManager entityManager;
    private SimpleJpaRepository<ENTITY, ID> simpleJpaRepository;

    /**
     * 注入EntityManager，同时实例化SimpleJpaRepository
     *
     * @param entityManager
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[1];

        this.simpleJpaRepository = new SimpleJpaRepository<ENTITY, ID>(entityClass, entityManager);

        this.entityManager = entityManager;
    }
    /**
     * 获取EntityManager，操作jpa api的入口
     *
     * @return
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * 给子类提供simpleJpaRepository实例，用来操作spring data jpa常用的接口
     *
     * @return
     */
    public SimpleJpaRepository<ENTITY, ID> getSimpleJpaRepository() {
        return simpleJpaRepository;
    }
    //</editor-fold>

    /****************************************************************/

//    public int exectureNotQuerySql(String sql,Object... params){
//        simpleJpaRepository.findOne()
//    }

}
