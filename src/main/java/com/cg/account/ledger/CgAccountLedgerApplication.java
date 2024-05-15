package com.cg.account.ledger;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cg.account.ledger.command.interceptor.AccountInterceptor;
import com.cg.account.ledger.command.interceptor.PostingInterceptor;
import com.cg.account.ledger.exception.AccountServiceEventErrorHandler;

@SpringBootApplication
public class CgAccountLedgerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CgAccountLedgerApplication.class, args);
	}
	
	/**
	 * Register the AccountInterceptor and PostingInterceptor
	 * 
	 * @param context
	 * @param commandBus
	 */
	@Autowired
	public void registerAccountInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(AccountInterceptor.class));
		commandBus.registerDispatchInterceptor(context.getBean(PostingInterceptor.class));
	}

	@Autowired
	public void configureEventProcessingConfigurer(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("account-ledger",
				configuration -> new AccountServiceEventErrorHandler());
	}

}
