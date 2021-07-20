package com.hrAssistantBot.telegramBot.commands;


import org.apache.commons.lang3.StringUtils;

public class CommandParser {

	public static Command parseRequest(String request) {
		if (StringUtils.isEmpty(request))
			return Command.REQUEST;

		if (request.trim().equals(Command.START.getDescription())) {
			return Command.START;
		} else if (request.trim().equals(Command.HELP.getDescription())) {
			return Command.HELP;
		} else {
			Command.REQUEST.setDescription(request);
			return Command.REQUEST;
		}
	}
}
