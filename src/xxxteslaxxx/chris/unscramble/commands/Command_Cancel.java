package xxxteslaxxx.chris.unscramble.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xxxteslaxxx.chris.unscramble.Vars;

public class Command_Cancel
{
	public static void cancel(Player p)
	{
		if (Vars.session == null)
		{
			p.sendMessage("§a[Unscramble] §4There is no game in progress.");
			return;
		}
		
		Vars.session.cancelGame();
	}
	
	public static void cancel(CommandSender p)
	{
		if (Vars.session == null)
		{
			p.sendMessage("§a[Unscramble] §4There is no game in progress.");
			return;
		}
		
		Vars.session.cancelGame();
	}
}