package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public abstract class EntityServiceImpl<DAO extends CommonJpaRepository<T, ID>, T, ID extends Serializable> implements EntityService<DAO, T, ID> {

    public DAO getRepositoryDao(){
        return getEntityDao();
    }
    protected abstract DAO getEntityDao();

//    @Transactional(readOnly = false)
//    @Override
//    public T insert(T t) {
//        return getEntityDao().insert(t);
//    }
//    @Transactional(readOnly = false)
//    @Override
//    public T update(T t) {
//        return getEntityDao().update(t);
//    }

    @Transactional(readOnly = false)
    @Override
    public int updateStatusById(ID id, Object value) {
        return getEntityDao().updateStatusById(id,value);
    }

    @Transactional(readOnly = false)
    @Override
    public int updateHiddenById(ID id, Object value) {
        return getEntityDao().updateHiddenById(id,value);
    }

    @Transactional(readOnly = false)
    @Override
    public int deleteByIds(Iterable<ID> ids) {
        return getEntityDao().deleteByIds(ids);
    }

    @Transactional(readOnly = false)
    @Override
    public int deleteByIds(ID... ids) {
        return getEntityDao().deleteByIds(ids);
    }
    @Transactional(readOnly = false)
    @Override
    public int deleteById(ID id) {
        return getEntityDao().deleteByIds(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteAll() {
        getEntityDao().deleteAll();
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteAll(Iterable<? extends T> var1) {
        getEntityDao().deleteAll(var1);
    }


    @Override
    public T getById(ID id) {
        return getEntityDao().getById(id);
    }

    @Override
    public ID getIdByEntity(T t) {
        return getEntityDao().getIdByEntity(t);
    }

    @Override
    public T getByEntityId(T t) {
        return getEntityDao().getByEntityId(t);
    }

    @Transactional(readOnly = false)
    @Override
    public int truncateParmeryKey(Class... entityClasss) {
        if(entityClasss==null || entityClasss.length==0){
            return getEntityDao().truncateParmeryKey(getEntityDao().getEntityClass());
        }else {
            int count=0;
            for (Class classs : entityClasss) {
                int v=getEntityDao().truncateParmeryKey(classs);
                count=count+v;
            }
            return count;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public int dropTable(Class... entityClasss) {
        if(entityClasss==null || entityClasss.length==0){
            return getEntityDao().dropTable(getEntityDao().getEntityClass());
        }else {
            int count=0;
            for (Class classs : entityClasss) {
                int v=getEntityDao().dropTable(classs);
                count=count+v;
            }
            return count;
        }
    }

    @Override
    public String getNameById(ID id) {
        return getEntityDao().getNameById(id);
    }

    @Override
    public String getIdKeyById(ID id) {
        return getEntityDao().getIdKeyById(id);
    }

    @Override
    public String getCodeById(ID id) {
        return getEntityDao().getCodeById(id);
    }

    @Override
    public List<T> findByIds(List<ID> ids) {
        return getEntityDao().findByIds(ids);
    }

    @Override
    public List<T> findByIds(ID[] ids) {
        return getEntityDao().findByIds(ids);
    }

    @Transactional(readOnly = false)
    @Override
    public <S extends T> Iterable<S> batchUpdate(Iterable<S> var1) {
        return getEntityDao().batchUpdate(var1);
    }

    @Transactional(readOnly = false)
    @Override
    public <S extends T> S save(S entity) {
        return getEntityDao().save(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return getEntityDao().saveAndFlush(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public <S extends T> Iterable<S> batchSave(Iterable<S> var1) {
        return getEntityDao().batchSave(var1);
    }

}
