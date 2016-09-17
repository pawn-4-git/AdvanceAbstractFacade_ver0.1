package facade;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import parameter.BaseNamedParameter;

/**
 *　継承元になるクラス
 * @author pawn4git
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
    
    private String seq;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public AbstractFacade(Class<T> entityClass,String seq_name) {
        this.entityClass = entityClass;
        this.seq=seq_name;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    public void flush() {
        getEntityManager().flush();
    }
    
    
    public long sequence(String sequenceName) throws Exception{
        StringBuilder sb=new StringBuilder();
        sb.append("select nextval('").append(sequenceName).append("'::regclass)");
        Query q=getEntityManager().createNativeQuery(sb.toString());
        Object obj=q.getSingleResult();
        return (long)obj;
        
    }
    
    public long sequence() throws Exception{
        StringBuilder sb=new StringBuilder();
        sb.append("select nextval('").append(getSeq()).append("'::regclass)");
        Query q=getEntityManager().createNativeQuery(sb.toString());
        Object obj=q.getSingleResult();
        return (long)obj;
        
    }
    
    /**
     * nameで指定したNamedQuesyを実行する
     * その際にparameterNameで指定したパラメータにvaluesを代入する
     * @param name
     * @param parameterName
     * @param value
     * @return 
     */
    
    public List<T> findByName(String name,String parameterName,Object value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            if(parameterName!=null){
                q.setParameter(parameterName, value);
            }
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * nameで指定したNamedQuesyを実行する
     * その際にparameterNameで指定したパラメータにvaluesを代入する
     * 結果は１件のみかえす　検索結果が０件の場合はNullを返す
     * @param name
     * @param parameterName
     * @param value
     * @return 
     */
    public T findByNameSingle(String name,String parameterName,Object value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            if(parameterName!=null){
                q.setParameter(parameterName, value);
            }
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return entityClass.cast(q.getResultList().get(0));
        }
        catch(Exception e){
            return null;
        }
    }
    
    /**
     * nameで指定したNamedQueryを実行する
     * valueで指定したマップのキー名の変数に対応する値を代入する
     * @param name
     * @param value
     * @return 
     */
    public List<T> findByName(String name,Map<String,Object> value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());

            value.keySet().forEach(key->q.setParameter(key, value.get(key)));
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * nameで指定したNamedQueryを実行する
     * valueで指定したマップのキー名の変数に対応する値を代入する
     * 検索結果は１件のみ返す　０件の場合はnulを返す
     * @param name
     * @param value
     * @return 
     */
    public T findByNameSingle(String name,Map<String,Object> value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            value.keySet().stream().forEach((key) -> {
                q.setParameter(key, value.get(key));
            });
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return entityClass.cast(q.getResultList().get(0));
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * nameで指定したNamedQueryを実行する
     * valueで指定したリストのキー名の変数に対応する値を代入する
     * @param name
     * @param value
     * @return 
     */
    public List<T> findByName(String name,List<BaseNamedParameter> value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            value.forEach(v->q.setParameter(v.getKey(),v.getValue()));
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * nameで指定したNamedQueryを実行する
     * 変数は指定できません
     * @param name
     * @return 
     */
    public List<T> findByName(String name){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * nameで指定したNamedQueryを実行する
     * 変数は指定できません
     * 検索結果は１件のみ返します　０件の場合はnullを返します
     * @param name
     * @return 
     */
    public T findByNameSingle(String name){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return entityClass.cast(q.getResultList().get(0));
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * nameで指定したNamedQueryを実行する
     * valueで指定したリストのキー名の変数に対応する値を代入する
     * 検索結果は１件のみ返す　０件の場合はnullを返す
     * @param name
     * @param value
     * @return 
     */
    public T findByNameSingle(String name,List<BaseNamedParameter> value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            value.forEach(v->q.setParameter(v.getKey(),v.getValue()));
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return entityClass.cast(q.getResultList().get(0));
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * 指定したNamedQueryをロックを取得して実行する
     * @param name
     * @param value
     * @return 
     */
     public List<T> findByName(String name,BaseNamedParameter value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            q.setParameter(value.getKey(), value.getValue());
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * 指定したNamedQueryをロック無しで実行する
     * @param name
     * @param parameterNmae
     * @param value
     * @return 
     */
    public List<T> findByNameNotLock(String name,String parameterNmae,Object value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            if(parameterNmae!=null){
                q.setParameter(parameterNmae, value);
            }
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * 1件のみの検索結果を取得　ロックは取得しません
     * @param name
     * @param parameterNmae
     * @param value
     * @return 
     */
    public T findByNameSingleNotLock(String name,String parameterNmae,Object value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            if(parameterNmae!=null){
                q.setParameter(parameterNmae, value);
            }
            
            return entityClass.cast(q.getResultList().get(0));
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * 指定したNamedQueryに複数パラメータを設定して実行する　ロックは取得しません
     * @param name
     * @param value
     * @return 
     */
    public List<T> findByNameNotLock(String name,Map<String,Object> value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
//            for(String key:value.keySet()){
//                q.setParameter(key, value);
//            }
            value.keySet().forEach(v->q.setParameter(v,value.get(v)));
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * 指定したNamedQueryを実行する　結果は先頭１件を返します　ロックは取得しません
     * @param name
     * @param value
     * @return 
     */
    public T findByNameSingleNotLock(String name,Map<String,Object> value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
//            for(String key:value.keySet()){
//                q.setParameter(key, value);
//            }
            value.keySet().forEach(v->q.setParameter(v,value.get(v)));
            
            return entityClass.cast(q.getResultList().get(0));
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * 指定したNamedQueryを実行する　検索結果は複数あり　ロックは取得しません
     * @param name
     * @param value
     * @return 
     */
    public List<T> findByNameNotLock(String name,List<BaseNamedParameter> value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            value.forEach(v->q.setParameter(v.getKey(),v.getValue()));
            
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
    
    /**
     * 指定したNamedQueryを実行する　パラメータは指定できません
     * ロックは取得しません
     * @param name
     * @return 
     */
    public List<T> findByNameNotLock(String name){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * 指定したNamedQueryを実行する　検索結果は先頭１件のみ　ロックは取得しません　パラメータは指定できません
     * @param name
     * @return 
     */
    public T findByNameSingleNotLock(String name){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            
            return entityClass.cast(q.getResultList().get(0));
        }
        catch(Exception e){
            return null;
        }
    }
    
    /**
     * 指定したNameQueryを実行　１件のみ取得します　パラメータは複数指定が可能です
     * @param name
     * @param value
     * @return 
     */
    public T findByNameSingleNotLock(String name,List<BaseNamedParameter> value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
//              for(BaseNamedParameter key:value){
//                q.setParameter(key.getKey(), key.getValue());
//            }
            value.forEach(v->q.setParameter(v.getKey(),v.getValue()));
            return entityClass.cast(q.getResultList().get(0));
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * 指定したNameQueryを実行します　ロックは取得しません　パラメータは１つのみ指定可能です
     * @param name
     * @param value
     * @return 
     */
     public List<T> findByNameNotLock(String name,BaseNamedParameter value){
        try{
            Query q=getEntityManager().createNamedQuery(name, entityClass.getClass());
            q.setParameter(value.getKey(), value.getValue());
            
            return q.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
 
    
    /**
     * @return the seq
     */
    public String getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(String seq) {
        this.seq = seq;
    }
     
     
}
