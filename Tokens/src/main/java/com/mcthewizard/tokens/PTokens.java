package com.mcthewizard.tokens;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "com.mcthewizard.tokens", name = "ptokens")
public class PTokens {

	@Inject
	Game game;
	
	@Inject
	Logger logger;
	
	// The configuration folder for this plugin
    @Inject
    @ConfigDir(sharedRoot = false)
    private File configDir;
    
    // The config manager for the mail storage file
    private ConfigurationLoader<CommentedConfigurationNode> tokensStorageConfigLoader;
    
    // The in-memory version of the mail storage file
    private CommentedConfigurationNode tokensStorageConfig;
    
    @Listener
    public void onPreInitialization(GamePreInitializationEvent event) {
    	File tokensStorageFile = new File(this.configDir, "tokens.conf");
    	this.tokensStorageConfigLoader = HoconConfigurationLoader.builder()
    			.setFile(tokensStorageFile).build();
    try {
    	if (!this.configDir.isDirectory()) {
    		this.configDir.mkdir();
    	}
    	if (!tokensStorageFile.isFile()) {
    		tokensStorageFile.createNewFile();
    	}
    	this.tokensStorageConfig = this.tokensStorageConfigLoader.load();
    	
    } catch (IOException e) {
    	this.logger.error("Unable to create or load tokens storage file!");
    	e.printStackTrace();
    	return;
    	
    }
    }
		
	@Listener
	public void onInitialization(GameInitializationEvent e) {

	// /tokens - Shows every token a player has or it should show
	HashMap<List<String>, CommandSpec> subCommands = new HashMap<>();
	
	subCommands.put(Arrays.asList("redeem"), CommandSpec.builder()
			.permission("tokens.redeem")
			.description(Text.of("Redeems Tokens"))
			.executor(new RedeemCommand())
			.build());
	
	CommandSpec tokenscom = CommandSpec.builder()
			.description(Text.of("Tokens Command"))
			.permission("tokens.tokens")
			.executor(new TokensCommand())
			.children(subCommands)
			.build();
	

this.game.getCommandManager().register(this, tokenscom, "tokens");
	
	
	}
	
	
}