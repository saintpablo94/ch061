package springbook.user.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {
	PlatformTransactionManager transactionManger;
	
	public void setTransactionManger(PlatformTransactionManager transactionManger) {
		this.transactionManger = transactionManger;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		TransactionStatus status =
				this.transactionManger.getTransaction(
						new DefaultTransactionDefinition());
		try {
			Object ret = invocation.proceed();
			this.transactionManger.commit(status);
			return ret;
		} catch (RuntimeException e) {
			this.transactionManger.rollback(status);
			throw e;
		}
	}
}
