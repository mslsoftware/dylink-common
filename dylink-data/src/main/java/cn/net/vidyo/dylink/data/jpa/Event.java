package cn.net.vidyo.dylink.data.jpa;

/**
 * JPA持久化事件Annotation，调用的时间次序参考具体属性的doc

 * User: mason
 * Date: 2009-7-21
 * Time: 11:32:22
 */
public enum Event {

    PostLoad,

    PrePersist,
    PreUpdate,
    PreDelete,


    PostPersist,
    PostUpdate,
    PostDelete;
}
