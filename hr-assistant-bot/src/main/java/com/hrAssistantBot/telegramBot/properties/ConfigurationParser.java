package com.hrAssistantBot.telegramBot.properties;


import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;


public class ConfigurationParser {

	public void parseConfigurations() {
		Yaml yaml = new Yaml();
		InputStream input = this.getClass().getResourceAsStream(BotConfiguration.CONFIG_FILE_NAME);

		Map config = yaml.load(input);
		Map botConfig = (Map) config.get(BotConfiguration.BOT_KEY);

		BotConfiguration.setBotUsername(String.valueOf(botConfig.get(BotConfiguration.BOT_USERNAME)));
		BotConfiguration.setBotToken(String.valueOf(botConfig.get(BotConfiguration.BOT_TOKEN)));

		BotConfiguration.setProjectDirectory(String.valueOf(config.get(BotConfiguration.PROJECT_DIRECTORY)));
	}
}
