package cn.net.vidyo.dylink.data.jpa.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueryWhere {
    String select="*";
    String where="";
    List<Object> params=new ArrayList<>();

    public QueryWhere() {
    }

    public QueryWhere(String where, Object... params) {
        this.where = where;
        setParams( params);
    }
    public QueryWhere addSelect(String select, String alias, SqlSelect pattern){
        StringBuilder sql = new StringBuilder();
        if(select.length()>1){
            sql.append(this.select);
            sql.append(",");
        }
        switch (pattern) {
            case TEAM:
                sql.append(select);
                break;
            case COUNT:
                sql.append(" COUNT( ").append(select).append(")");
                break;
            case SUM:
                sql.append(" SUM( ").append(select).append(")");
                break;
            case MIN:
                sql.append(" MIN( ").append(select).append(")");
                break;
            case MAX:
                sql.append(" MAX( ").append(select).append(")");
                break;
            case LEN:
                sql.append(" LEN( ").append(select).append(")");
                break;
            case AVG:
                sql.append(" AVG( ").append(select).append(")");
                break;
            case FIRST:
                sql.append(" FIRST( ").append(select).append(")");
                break;
            case LAST:
                sql.append(" LAST( ").append(select).append(")");
                break;
            case ALL:
                sql.append(" * ");
                break;
            default:
                throw new RuntimeException("pattern value is fail");
        }
        if(alias!=null && alias.length()>0){
            sql.append(" as ");
            sql.append(alias);
        }
        this.select=sql.toString();
        return this;
    }


    public void setParams(Object... params){
        if(params==null) return;
        for (Object param : params) {
            this.params.add(param);
        }
    }


    //<editor-fold desc="where">
    //<editor-fold desc="where default and">
    public QueryWhere addIdWhere(Object id){
        return addIdWhere(SqlLogic.And,id);
    }
    public QueryWhere addWhere(String columnName, Object value){
        return addWhere(SqlLogic.And,columnName,value);
    }
    public QueryWhere addThanWhere(String columnName, Object value, SqlThan pattern) {
        return addThanWhere(SqlLogic.And,columnName,value,pattern);
    }

    public QueryWhere addInWhere(String columnName, Collection values) {
        return addInWhere(SqlLogic.And,columnName,values);
    }

    public QueryWhere addInWhere(String columnName, Object... values) {
        return addInWhere(SqlLogic.And,columnName,values);
    }

    public QueryWhere addLikeWhere(String columnName, Object value, SqlLike pattern) {
        return addLikeWhere(SqlLogic.And,columnName,value,pattern);
    }

    //</editor-fold>
    //<editor-fold desc="where method">
    public QueryWhere addIdWhere(SqlLogic logic, Object id){
        return addWhere(logic,"id",id);
    }
    public QueryWhere addWhere(SqlLogic logic, String columnName, Object value){
        return linkWhere(logic,columnName+"=?",value);
    }
    public QueryWhere addThanWhere(SqlLogic logic, String columnName, Object value, SqlThan pattern) {
        StringBuilder sql;
        sql = new StringBuilder();
        sql.append(columnName);
        switch (pattern) {
            case THAN_GREATER:
                sql.append(" > ?");
                break;
            case THAN_LESS:
                sql.append(" < ?");
                break;
            case THAN_EQUAL:
                sql.append(" = ?");
                break;
            case THAN_NOT:
                sql.append(" <> ?");
                break;
            case THAN_GREATER_EQUAL:
                sql.append(" >= ?");
                break;
            case THAN_LESS_EQUAL:
                sql.append(" <= ?");
                break;
            default:
                throw new UnsupportedOperationException("pattern fail.");
        }
        return linkWhere(logic,sql.toString(),value);
    }

    public QueryWhere addInWhere(SqlLogic logic, String columnName, Collection values) {
        StringBuilder sql = new StringBuilder();
        for (Object value : values) {
            if(sql.length()>0){
                sql.append(",");
            }
            sql.append("?");
        }
        sql.indexOf(" IN (",0);
        sql.indexOf(columnName,0);
        sql.append(")");
        return linkWhere(logic, sql.toString(),values.toArray());
    }

    public QueryWhere addInWhere(SqlLogic logic, String columnName, Object... values) {
        StringBuilder sql = new StringBuilder();
        for (Object value : values) {
            if(sql.length()>0){
                sql.append(",");
            }
            sql.append("?");
        }
        sql.indexOf(" IN (",0);
        sql.indexOf(columnName,0);
        sql.append(")");
        return linkWhere(logic, sql.toString(),values);
    }

    public QueryWhere addLikeWhere(SqlLogic logic, String columnName, Object value, SqlLike pattern) {
        StringBuilder sql = new StringBuilder();
        sql.append(columnName);
        sql.append(" LIKE ? ");
        switch (pattern){
            case LIKE_PREFIX:
                value="%"+value;
                break;
            case LIKE_POSTFIX:
                value=value+"%";
                break;
            case LIKE_KEYWORD:
                value="%"+value+"%";
                break;
        }
        return linkWhere(logic, sql.toString(),value);
    }

    //</editor-fold>
    //<editor-fold desc="where base method">
    public QueryWhere linkWhere(SqlLogic logic, String where, Object... params){
        return linkWhere(logic,new QueryWhere(where,params));
    }
    public QueryWhere linkWhere(SqlLogic logic, QueryWhere where){
        if(where.where.length()==0){
            return this;
        }
        if(this.where.length()==0){
            this.where=where.getWhere();
            this.params=where.getParams();
            return this;
        }
        StringBuilder sql= new StringBuilder();
        sql.append(this.where);
        switch (logic){
            case And:
                sql.append(" AND ");
                break;
            case Or:
                sql.append(" OR ");
                break;
            case Not:
                sql.append(" NOT ");
                break;
        }
        sql.append(where.where);
        this.where=sql.toString();
        if(where.params.size()>0){
            for (Object param : where.params) {
                this.params.add(param);
            }
        }
        return this;
    }
    public QueryWhere combileWhere(SqlLogic logic, String where, Object... params){
        return combileWhere(logic,new QueryWhere(where,params));
    }
    public QueryWhere combileWhere(SqlLogic logic, QueryWhere where){
        if(where.where.length()==0){
            return this;
        }
        if(this.where.length()==0){
            this.where=where.getWhere();
            this.params=where.getParams();
            return this;
        }
        StringBuilder sql= new StringBuilder();
        sql.append(" ( ");
        sql.append(this.where);
        sql.append(" ) ");
        switch (logic){
            case And:
                sql.append(" AND (");
                break;
            case Or:
                sql.append(" OR (");
                break;
            case Not:
                sql.append(" NOT (");
                break;
        }
        sql.append(where.where);
        sql.append(" )");
        this.where=sql.toString();
        if(where.params.size()>0){
            for (Object param : where.params) {
                this.params.add(param);
            }
        }
        return this;
    }
    //</editor-fold>
    //</editor-fold>

    public void addParam(Object value){
        this.params.add(value);
    }
    public void addParam(Object... values){
        for (Object value : values) {
            addParam(value);
        }
    }
    public void addParam(Collection values){
        for (Object value : values) {
            addParam(value);
        }
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
