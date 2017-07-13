package com.mcthewizard.tokens;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class TokensCommand implements CommandExecutor {
	
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(!(src instanceof Player)) {
			src.sendMessage(Text.of(TextColors.RED, "Only useable ingame!"));
			return CommandResult.success();

		}
		Player player = (Player)src;
		player.sendMessage(Text.of(TextColors.GOLD, "=====", " ", player.getName(), "'s", " ", "Tokens ====="));
		return CommandResult.success();
		
		
	}
}
