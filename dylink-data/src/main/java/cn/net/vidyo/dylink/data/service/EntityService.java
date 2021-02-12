package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.common.CommonEnumCodes;
import cn.net.vidyo.dylink.common.Result;
import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import cn.net.vidyo.dylink.util.ObjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface EntityService<DAO extends CommonJpaRepository<T, ID>, T, ID extends Serializable> {
    T getById(ID id);

    void deleteAll();
    int truncateParmeryKey();

    String getNameById(ID id);

    List<T> findByIds(List<ID> ids);

    List<T> findByIds(ID[] ids);

    //long hiddenByIdIn(boolean hidden,Iterable<ID> ids);
    long deleteByIdIn(Iterable<ID> ids);

    T save(T t);

    void deleteById(ID id);

    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    Page<T> findAll(int pageNumber,int pageSize);


//    Result add1(T t) ;
//
//    Result update1(T entity) ;
//
//    Result save1(T t);
//
//    Result deleteById1(ID id);
}
