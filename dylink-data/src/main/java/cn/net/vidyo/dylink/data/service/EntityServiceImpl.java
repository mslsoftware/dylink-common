package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.common.CommonEnumCodes;
import cn.net.vidyo.dylink.common.Result;
import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import cn.net.vidyo.dylink.util.ObjectUtil;

import java.io.Serializable;
import java.util.List;

public abstract class EntityServiceImpl<DAO extends CommonJpaRepository<T, ID>, T, ID extends Serializable> implements EntityService<DAO, T, ID> {


    protected abstract DAO getEntityDao();


    @Deprecated
    public List<T> findByCondition(T t,Object... params){
        return null;
    }
    @Deprecated
    public List<T> pageByCondition(T t,Object... params){
        return null;
    }
    @Deprecated
    public List<ID> findIdsByCondition(T t,Object... params){
        return null;
    }
    @Deprecated
    public List<ID> pageIdsByCondition(T t,Object... params){
        return null;
    }

    public T getById(ID id) {
        return getEntityDao().getById(id);
    }

    public int truncateParmeryKey(){
        return getEntityDao().truncateParmeryKey();
    }
    public void deleteAll(){
        getEntityDao().deleteAll();
    }

    public String getNameById(ID id) {
        return getEntityDao().getNameById(id);
    }

    public List<T> findByIds(List<ID> ids) {
        return getEntityDao().findByIds(ids);
    }

    public List<T> findByIds(ID[] ids) {
        return getEntityDao().findByIds(ids);
    }

    //long hiddenByIdIn(boolean hidden,Iterable<ID> ids);
    public long deleteByIdIn(Iterable<ID> ids) {
        return getEntityDao().deleteByIdIn(ids);
    }

    public Result add(T t) {
        T save = getEntityDao().save(t);
        if (save == null) {
            return Result.fail(CommonEnumCodes.DB_ACCESS_EXCEPTION);
        }
        return Result.ok(save);
    }

    public Result update(T entity) {
        T model = getEntityDao().getByEntityId(entity);
        if (model == null) {
            return Result.fail(CommonEnumCodes.NOT_EXIST);
        }
        model = ObjectUtil.copyPropertiesByNotDefaultValue(entity, model);
        model = convertEntityOfUpdate(entity, model);
        T save = getEntityDao().save(model);
        if (save == null) {
            return Result.fail(CommonEnumCodes.DB_ACCESS_EXCEPTION);
        }
        return Result.ok(save);
    }

    protected T convertEntityOfUpdate(T source, T target) {
        return target;
    }

    public Result save(T t) {
        T entity = getEntityDao().getByEntityId(t);
        if (entity == null) {
            return add(t);
        }
        return update(t);
    }

    public Result deleteById(ID id) {
        getEntityDao().deleteById(id);
        return Result.ok();
    }


}
