package xxxteslaxxx.chris.unscramble.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_Help
{
	public static void help(Player p)
	{
		p.sendMessage("§5=================§c [ Unscramble Help ] §5==================");
		p.sendMessage("§c/unscramble §e- States the general info.");
		p.sendMessage("§c/unscramble §7reload §e- Reloads the config.");
		p.sendMessage("§c/unscramble §7help §e- States the commands");
		p.sendMessage("§c/unscramble §7hint §e- Gives a hint on the current word.");
		p.sendMessage("§c/unscramble §7cancel §e- Cancels any game currently running.");
		p.sendMessage("§c/unscramble §7mute §e- Turns off all unscramble messages.");
		p.sendMessage("§c/unscramble §7newgame w:[word] p:<prize> a:<amount> t:<time> h:<hintInterval> c:<category> §e- starts a new game with the given details..");
		p.sendMessage("§cNote: Underscores (_) will be changed into spaces.");
		p.sendMessage("§5=====================================================");
	}
	
	public static void help(CommandSender p)
	{
		p.sendMessage("================= [ Unscramble Help ] ==================");
		p.sendMessage("/unscramble - States the general info.");
		p.sendMessage("/unscramble reload - Reloads the config.");
		p.sendMessage("/unscramble help - States the commands");
		p.sendMessage("/unscramble hint - Gives a hint on the current word.");
		p.sendMessage("/unscramble cancel - Cancels any game currently running.");
		p.sendMessage("/unscramble newgame [word] [prize] [amount] <time> - starts a new game with the given details..");
		p.sendMessage("Note: Underscores (_) will be changed into spaces.");
		p.sendMessage("=====================================================");
	}
}