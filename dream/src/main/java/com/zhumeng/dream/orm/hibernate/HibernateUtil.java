package com.zhumeng.dream.orm.hibernate;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.zhumeng.dream.util.spring.SpringContextHolder;

/**
 * 
 *
 * 
 *
 * 
 * @version 1.0
 * 
 *
 * 
 * @author 
 * 
 *
 * 
 * @Create 2016年3月15日 下午2:32:23
 * 
 *
 * 
 * @History zhaozongzhan 2016年3月15日 创建 <br>
 * 
 *
 */

public class HibernateUtil {
    private static final Logger log = Logger.getLogger(HibernateUtil.class);

    private HibernateUtil() {
    }

    private static final SessionFactory sessionFactory;

    static {
        try {

            sessionFactory = SpringContextHolder.getBean(SessionFactory.class);
            System.out.println(">>>>>>>>>>>>>>sessionFactory :" + sessionFactory);
        }
        catch (Throwable ex) {
            log.fatal(ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();

    private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();

    public static Session currentSession() throws HibernateException {
        Session s = threadSession.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            System.out.println(">>>>>>>>>>>>>>sss :" + s);
            threadSession.set(s);
            if (log.isDebugEnabled()) {
                log.debug("---------openSession: ");
            }
        }
        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = threadSession.get();
        threadSession.set(null);
        if (s != null) {
            s.close();
            if (log.isDebugEnabled()) {
                log.debug("---------closeSession: ");
            }
        }
    }

    public static void beginTransaction() {
        Transaction tx = threadTransaction.get();
        try {
            if (tx == null) {
                tx = currentSession().beginTransaction();
                threadTransaction.set(tx);
                if (log.isDebugEnabled()) {
                    log.debug(">>>>>>>>beginTransaction");
                }
            }
        }
        catch (HibernateException ex) {
            log.warn(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public static void commitTransaction() {
        Transaction tx = threadTransaction.get();
        try {
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.commit();
                if (log.isDebugEnabled()) {
                    log.debug(">>>>>>>>commitTransaction");
                }
            }
            threadTransaction.set(null);
        }
        catch (HibernateException ex) {
            log.warn(ex.getMessage());
            rollbackTransaction();
            throw new RuntimeException(ex);
        }
    }

    public static void rollbackTransaction() {
        Transaction tx = threadTransaction.get();
        try {
            threadTransaction.set(null);
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.rollback();
                log.warn("!!!!!!!rollbackTransaction");
            }
        }
        catch (HibernateException ex) {
            log.warn(ex.getMessage());
            throw new RuntimeException(ex);
        }
        finally {
            try {
                closeSession();
            }
            catch (HibernateException ex) {
                log.warn(ex.getMessage());
            }
        }
    }

    /**
     * <p>
     * 在一个session的事务里执行对数据库的操作.最后提交事务,关闭session.抛出异常时回滚整个事务.
     * <p>
     * 一般用于在非http请求的环境下, 例如quartz线程,系统初始化的Listener
     * 
     * @param task 在hibernate事务中执行的任务
     * @return 返回Callable.call()的返回值, 抛出异常时返回null
     */
    public static <T> T hibernateTemplateMethod(Callable<T> task) {
        try {
            T obj = task.call();
            HibernateUtil.commitTransaction();
            return obj;
        }
        catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            log.warn(ex.getMessage(), ex);
            System.err.append("##########################支付回调处理积分，销量数据失败！事务回滚不####################################################"+ex.getMessage());
        }
        finally {
            HibernateUtil.closeSession();
        }

        return null;
    }

    public static void eq(String propertyName, String value, Criteria criteria) {
        if (value != null && value.length() > 0) {
            Criterion criterion = Restrictions.eq(propertyName, value);
            criteria.add(criterion);
        }
    }

    public static void eq(String propertyName, int value, Criteria criteria) {
        if (value > 0) {
            Criterion criterion = Restrictions.eq(propertyName, value);
            criteria.add(criterion);
        }
    }
    
    public static void eq(String propertyName, long value, Criteria criteria) {
        if (value > 0) {
            Criterion criterion = Restrictions.eq(propertyName, value);
            criteria.add(criterion);
        }
    }

    public static void in(String propertyName, String[] value, Criteria criteria) {
        if (value.length > 1) {
            Criterion criterion = Restrictions.in(propertyName, value);
            criteria.add(criterion);
        }
    }
    
    /** 权举型判断 */
    public static void eq(String propertyName, Enum value, Criteria criteria) {
        if (value != null) {
            Criterion criterion = Restrictions.eq(propertyName, value);
            criteria.add(criterion);
        }
    }
}
