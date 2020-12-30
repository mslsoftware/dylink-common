package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.common.CommonEnumCodes;
import cn.net.vidyo.dylink.common.Result;
import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import cn.net.vidyo.dylink.util.ObjectUtil;

import java.io.Serializable;
import java.util.List;

public interface EntityService<DAO extends CommonJpaRepository<T, ID>, T, ID extends Serializable> {
    public T getById(ID id);

    void deleteAll();
    int truncateParmeryKey();

    public String getNameById(ID id);

    public List<T> findByIds(List<ID> ids);

    public List<T> findByIds(ID[] ids);

    //long hiddenByIdIn(boolean hidden,Iterable<ID> ids);
    public long deleteByIdIn(Iterable<ID> ids);

    public Result add(T t) ;

    public Result update(T entity) ;

    public Result save(T t);

    public Result deleteById(ID id);
}
