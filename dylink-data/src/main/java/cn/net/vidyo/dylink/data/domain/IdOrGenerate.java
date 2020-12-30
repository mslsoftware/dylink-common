package cn.net.vidyo.dylink.data.domain;


import cn.net.vidyo.dylink.data.domain.IModel;
import cn.net.vidyo.dylink.data.domain.impl.IntModel;
import cn.net.vidyo.dylink.data.domain.impl.LongModel;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import org.springframework.beans.BeanWrapperImpl;

import java.io.Serializable;

public class IdOrGenerate extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sessionContractImplementor, Object obj) {
        if (obj == null){
            throw new HibernateException(new NullPointerException()) ;
        }
        if(IntModel.class.isAssignableFrom(obj.getClass()) || LongModel.class.isAssignableFrom(obj.getClass())){
            IModel model=(IModel)obj;
            if(model.isIdModified()){
                return model.getId();
            }
            return  super.generate(sessionContractImplementor, obj) ;
        }
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(obj);
        Object idValue = beanWrapper.getPropertyValue("id");
        if(idValue==null || idValue.toString()=="" || idValue.toString()=="0"){
            return  super.generate(sessionContractImplementor, obj) ;
        } else {
            return (Serializable)idValue;
        }
    }
}
