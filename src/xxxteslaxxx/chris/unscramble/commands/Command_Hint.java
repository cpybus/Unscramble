package xxxteslaxxx.chris.unscramble.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xxxteslaxxx.chris.unscramble.Vars;
import xxxteslaxxx.chris.unscramble.custom_objects.MyException;

public class Command_Hint
{
	public static void hint(Player p)
	{
		if (Vars.session == null)
		{
			p.sendMessage("§a[Unscramble] §4There is no game in progress.");
			return;
		}
		
		try
		{
			Vars.session.giveHint();
		}
		catch (MyException e)
		{
			p.sendMessage("§a[Unscramble] §4" + e.getString());
		}
		
	}
	
	public static void hint(CommandSender p)
	{
		if (Vars.session == null)
		{
			p.sendMessage("[Unscramble] There is no game in progress.");
			return;
		}
		
		try
		{
			Vars.session.giveHint();
		}
		catch (MyException e)
		{
			p.sendMessage("[Unscramble] " + e.getString());
		}
	}
}