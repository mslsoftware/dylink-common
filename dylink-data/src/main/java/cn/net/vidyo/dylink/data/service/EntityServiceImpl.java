package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class EntityServiceImpl<DAO extends CommonJpaRepository<T, ID>, T, ID extends Serializable> implements EntityService<DAO, T, ID> {


    protected abstract DAO getEntityDao();

    @Override
    public int updateStatusById(ID id, Object value) {
        return getEntityDao().updateStatusById(id,value);
    }

    @Override
    public int updateHiddenById(ID id, Object value) {
        return getEntityDao().updateHiddenById(id,value);
    }

    @Override
    public int deleteByIds(Iterable<ID> ids) {
        return getEntityDao().deleteByIds(ids);
    }

    @Override
    public int deleteByIds(ID... ids) {
        return getEntityDao().deleteByIds(ids);
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

    @Override
    public int truncateParmeryKey() {
        return getEntityDao().truncateParmeryKey();
    }

    @Override
    public int dropTable() {
        return getEntityDao().dropTable();
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

    @Override
    public <S extends T> Iterable<S> batchUpdate(Iterable<S> var1) {
        return getEntityDao().batchUpdate(var1);
    }

    @Override
    public <S extends T> S save(S entity) {
        return getEntityDao().save(entity);
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return getEntityDao().saveAndFlush(entity);
    }

    @Override
    public <S extends T> Iterable<S> batchSave(Iterable<S> var1) {
        return getEntityDao().batchSave(var1);
    }

}
