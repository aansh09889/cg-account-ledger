package com.cg.account.ledger.startup;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.cg.account.ledger.command.OpenAccountCommand;
import com.cg.account.ledger.constants.AccountStatus;

@Component
public class AccountCommandLineRunner implements CommandLineRunner {

    private final Environment environment;
    private final CommandGateway commandGateway;

    public AccountCommandLineRunner(Environment environment,CommandGateway commandGateway) {
        this.environment = environment;
        this.commandGateway = commandGateway;
    }

    @Override
    public void run(String... args) throws Exception {
        commandGateway.send(OpenAccountCommand.builder().accountId(UUID.randomUUID().toString())
                .accountStatus(AccountStatus.OPEN).build());
    }
}
