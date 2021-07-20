package com.hrAssistantBot.telegramBot.properties;

import lombok.Getter;
import lombok.Setter;


public class BotConfiguration {
	public static final String CONFIG_FILE_NAME = "/telegramBot.yml";

	static final String BOT_KEY = "telegramBot";
	static final String BOT_USERNAME = "username";
	static final String BOT_TOKEN = "token";
	static final String PROJECT_DIRECTORY = "projectDirectory";


	@Getter
	@Setter
	private static String botUsername;
	@Getter
	@Setter
	private static String botToken;
	@Getter
	@Setter
	private static String projectDirectory;
}
