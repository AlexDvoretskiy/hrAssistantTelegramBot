package com.hrAssistantBot.telegramBot.handlers;


import com.hrAssistantBot.telegramBot.commands.Command;
import persistence.services.CandidateService;
import persistence.services.VacancyService;


public class CommandHandlerFactory {
    private static volatile ServiceCommandHandler serviceHandlerInstance;
    private static volatile OperationCommandHandler operationHandlerInstance;

    public static CommandHandler getHandler(Command command) {
        switch (command) {
            case START:
            case HELP:
                return getServiceHandlerInstance();
            default:
                return getOperationHandlerInstance();
        }
    }

    public static ServiceCommandHandler getServiceHandlerInstance() {
        ServiceCommandHandler localInstance = serviceHandlerInstance;
        if (localInstance == null) {
            synchronized (ServiceCommandHandler.class) {
                localInstance = serviceHandlerInstance;
                if (localInstance == null) {
                    serviceHandlerInstance = localInstance = new ServiceCommandHandler(VacancyService.getInstance(), CandidateService.getInstance());
                }
            }
        }
        return localInstance;
    }

    public static OperationCommandHandler getOperationHandlerInstance() {
        OperationCommandHandler localInstance = operationHandlerInstance;
        if (localInstance == null) {
            synchronized (OperationCommandHandler.class) {
                localInstance = operationHandlerInstance;
                if (localInstance == null) {
                    operationHandlerInstance = localInstance = new OperationCommandHandler(VacancyService.getInstance(), CandidateService.getInstance());
                }
            }
        }
        return localInstance;
    }
}
