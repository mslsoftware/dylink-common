package cn.net.vidyo.dylink.data.jpa.support;

import cn.net.vidyo.dylink.util.ValueUtil;
import org.hibernate.HibernateException;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实现数据库结果集映射到自定义的POJO类
 * 要注意的是这里的属性需要和数据库字段名称遵照驼峰命名方式
 *
 * @author lichuang
 */
public class ColumnToBean extends AliasedTupleSubsetResultTransformer {

    private static final Logger logger = LoggerFactory.getLogger(ColumnToBean.class);

    private static final String STR_UNDERLINE = "_";

    private static final String STR_EMPTY = "";

    private final Class resultClass;
    private boolean isInitialized;
    private String[] aliases;
    private Setter[] setters;

    public ColumnToBean(Class resultClass) {
        if (resultClass == null) {
            throw new IllegalArgumentException("resultClass cannot be null");
        }
        isInitialized = false;
        this.resultClass = resultClass;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Object result;
        try {
            if (!isInitialized) {
                initialize(aliases);
            } else {
                check(aliases);
            }
            result = resultClass.newInstance();
            for (int i = 0; i < aliases.length; i++) {
                if (setters[i] != null && tuple[i] != null) {
                    Method method = setters[i].getMethod();
                    if (method == null) {
                        throw new RuntimeException("Could not find DB Column:" + aliases[i] + " Setter Method!");
                    }
                    Class<?> param = method.getParameterTypes()[0];
                    Class<?> tupleClass = tuple[i].getClass();
                    //若目标参数类型和当前参数类型不匹配 ,尝试进行转换
                    if (!param.equals(tupleClass)) {
                        if(param.isEnum()){
                            Object[] enumConstants = param.getEnumConstants();
                            Object v=null;
                            if(Integer.class.isAssignableFrom(tupleClass)){
                                int iv=(int)tuple[i];
                                v=enumConstants[iv];
                            }else{
                                for (Object enumConstant : enumConstants) {
                                    Enum e=(Enum)enumConstant;
                                    if(e.name().equals(tuple[i].toString())){
                                        v=e;
                                        break;
                                    }
                                }
                            }
                            setters[i].set(result, v, null);
                        }else if (Number.class.isAssignableFrom(tupleClass)) {
                            Number num = (Number) tuple[i];
                            if (Long.class.equals(param) || long.class.equals(param)) {
                                setters[i].set(result, num.longValue(), null);
                            } else if (Integer.class.equals(param) || int.class.equals(param)) {
                                setters[i].set(result, num.intValue(), null);
                            } else if (Boolean.class.equals(param) || boolean.class.equals(param)) {
                                setters[i].set(result, num.intValue() == 1, null);
                            } else if (Float.class.equals(param) || float.class.equals(param)) {
                                setters[i].set(result, num.floatValue(), null);
                            } else if (Double.class.equals(param) || double.class.equals(param)) {
                                setters[i].set(result, num.doubleValue(), null);
                            } else if (Short.class.equals(param) || short.class.equals(param)) {
                                setters[i].set(result, num.shortValue(), null);
                            } else if (BigDecimal.class.equals(param)) {
                                setters[i].set(result, num, null);
                            } else {
                                throw new RuntimeException("Unsupported type conversion ：" + tuple[i].getClass());
                            }
                            //如果tuple为参数的子类,直接设置
                            //如java.util.Date; java.sql.Date;
                        } else if (param.isAssignableFrom(tupleClass)) {
                            setters[i].set(result, tuple[i], null);
                            //处理数据库类型定义为大字段Clob的数据，将其转换成字符串类型
                        } else if(Boolean.class.isAssignableFrom(tupleClass)) {
                            setters[i].set(result,ValueUtil.toBoolean(tuple[i]),null);
                        } else if(Character.class.isAssignableFrom(tupleClass)) {
                            setters[i].set(result,ValueUtil.toChar(tuple[i]),null);
                        } else if (tuple[i] instanceof Clob) {
                            Clob clob = (Clob) tuple[i];
                            setters[i].set(result, clobToString(clob), null);
                        } else {
                            throw new RuntimeException("Unsupported type conversion ：" + tuple[i].getClass());
                        }
                    } else {
                        setters[i].set(result, tuple[i], null);
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("不能实例化类：" + resultClass.getName(),e);
            throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
        }
        return result;
    }

    private void initialize(String[] aliases) {
        PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(
                PropertyAccessStrategyBasicImpl.INSTANCE,
                PropertyAccessStrategyFieldImpl.INSTANCE,
                PropertyAccessStrategyMapImpl.INSTANCE
        );
        this.aliases = new String[aliases.length];
        setters = new Setter[aliases.length];
        for (int i = 0; i < aliases.length; i++) {
            String alias = aliases[i];
            if (alias != null) {
                this.aliases[i] = alias;
                setters[i] = getSetterByColumnName(alias, propertyAccessStrategy);
            }
        }
        isInitialized = true;
    }

    /**
     * 根据数据库字段名在POJO查找JAVA属性名的Setter方法，参数就是数据库字段名
     */
    private Setter getSetterByColumnName(String alias, PropertyAccessStrategyChainedImpl propertyAccessStrategy) {
        // 取得POJO所有属性名
        List<Field> fields= getClassDeclaredFields(resultClass,true);
//        Field[] fields = resultClass.getDeclaredFields();
        if (fields == null || fields.size() == 0) {
            throw new RuntimeException("POJO [" + resultClass.getName() + "] does not contain any attributes.");
        }
        // 把字段名中所有的下杠去除
        String proName = alias.replaceAll(STR_UNDERLINE, STR_EMPTY).toLowerCase();
        String lowerAlias = alias.toLowerCase();
        for (Field field : fields) {
            // 如果不去掉下划线的字段名称和属性名对的上，或者去除下杠的字段名如果和属性名对得上，就取这个SETTER方法
            //DTO中的字段名称,变成小写
            String fieldName = field.getName().toLowerCase();
            boolean match = fieldName.equals(lowerAlias)
                    || fieldName.equals(proName);
            if (match) {
                return propertyAccessStrategy.buildPropertyAccess(resultClass, field.getName()).getSetter();
            }
        }
        //没找到对应的字段
        throw new RuntimeException("Could not find DB Column[" + alias + "] Java POJO property!");
    }

    List<Field> getClassDeclaredFields(Class cClass, boolean includeSuper){
        List<Field> list = new ArrayList<>();
        if(cClass==null){
            return list;
        }
        if(cClass==Object.class){
            return list;
        }
        Field[] fields = cClass.getDeclaredFields();
        for (Field field : fields) {
            list.add(field);
        }
        if(includeSuper) {
            List<Field> list1 = getClassDeclaredFields(cClass.getSuperclass(), includeSuper);
            list.addAll(list1);
        }
        return list;
    };
    /**
     * 将Clob类型数据转换成字符串
     */
    private static String clobToString(Clob clob) {
        String reString = "";
        Reader is = null;
        try {
            is = clob.getCharacterStream();
            // 得到流
            BufferedReader br = new BufferedReader(is);
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null) {
                //执行循环将字符串全部取出赋值给StringBuilder由StringBuilder转成STRING
                sb.append(s);
            }
            reString = sb.toString();
        } catch (SQLException | IOException e) {
            logger.error("Get Clob Content Failed", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("I/O Exception", e);
                }
            }
        }
        return reString;
    }

    private void check(String[] aliases) {
        if (!Arrays.equals(aliases, this.aliases)) {
            throw new IllegalStateException( "aliases are different from what is cached; aliases="
                    + Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColumnToBean that = (ColumnToBean) o;
        return resultClass.equals(that.resultClass) && Arrays.equals(aliases, that.aliases);
    }

    @Override
    public int hashCode() {
        int result = resultClass.hashCode();
        result = 31 * result + (aliases != null ? Arrays.hashCode(aliases) : 0);
        return result;
    }
}
