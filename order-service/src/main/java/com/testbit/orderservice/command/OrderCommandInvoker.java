package com.testbit.orderservice.command;

public class OrderCommandInvoker {
    public void executeCommand(OrderCommand command) {
        command.execute();
    }
}
