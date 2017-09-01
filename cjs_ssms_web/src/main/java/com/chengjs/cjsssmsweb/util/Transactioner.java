package com.chengjs.cjsssmsweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Transactioner: 封装pring-mvc transaction，当然用注释也行
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/1
 */
public class Transactioner {

  private static final Logger log = LoggerFactory.getLogger(Transactioner.class);

  private TransactionStatus status = null;

  private DataSourceTransactionManager transactionManager;

  /**
   * 初始化事务对象并开启事务
   * @param transactionManager
   */
  public Transactioner(DataSourceTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
    start(transactionManager);
  }

  /**
   * 开启事物
   * @param transactionManager
   */
  public void start(DataSourceTransactionManager transactionManager) {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    /*PROPAGATION_REQUIRES_NEW:  事物隔离级别，开启新事务*/
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    status =  transactionManager.getTransaction(def);
  }

  /**
   * 提交事务
   * @param commitOrRollback true-commit, false-rollback
   */
  public void end(boolean commitOrRollback) {
    if (null == status) {
      log.warn("事务未开启无法提交");
      return;
    }
    if (commitOrRollback) {
      transactionManager.commit(status);
    } else {
      transactionManager.rollback(status);
    }
  }


}
