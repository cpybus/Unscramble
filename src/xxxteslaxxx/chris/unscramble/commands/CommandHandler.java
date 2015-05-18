package xxxteslaxxx.chris.unscramble.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xxxteslaxxx.chris.unscramble.UnscrambleHelperMethods;
import xxxteslaxxx.chris.unscramble.Vars;

public class CommandHandler implements CommandExecutor
{
	private static String	noPerms				= "§a[Unscramble] §4You do not have permission for that command.";
	private static String	invalidCmd			= "§a[Unscramble] §4That was not a valid command.";
	private static String	consoleInvalidCmd	= "[Unscramble] That was not a valid command.";
	
	public boolean onCommand(CommandSender p, Command cmd, String idk, String[] args)
	{
		if ((p instanceof Player))
		{
			Player ply = (Player) p;
			
			if (args.length == 0)
			{
				if (Vars.perms.has(ply, "unscramble.welcome"))
					Command_Welcome.welcome(ply);
				else
					ply.sendMessage(noPerms);
			}
			else if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("help"))
				{
					if (Vars.perms.has(ply, "unscramble.help"))
						Command_Help.help(ply);
					else
						ply.sendMessage(noPerms);
				}
				else if (args[0].equalsIgnoreCase("reload"))
				{
					if (Vars.perms.has(ply, "unscramble.reload"))
						Command_Reload.reload(ply);
					else
						ply.sendMessage(noPerms);
				}
				else if (args[0].equalsIgnoreCase("hint"))
				{
					if (Vars.perms.has(ply, "unscramble.hint"))
						Command_Hint.hint(ply);
					else
						ply.sendMessage(noPerms);
				}
				else if (args[0].equalsIgnoreCase("cancel"))
				{
					if (Vars.perms.has(ply, "unscramble.cancel"))
						Command_Cancel.cancel(ply);
					else
						ply.sendMessage(noPerms);
				}
				else if (args[0].equalsIgnoreCase("claim"))
				{
					if (Vars.perms.has(ply, "unscramble.claim"))
						Command_Claim.claim(ply);
					else
					{
						ply.sendMessage(noPerms);
					}
				}
				else if(args[0].equalsIgnoreCase("mute"))
				{
					if(Vars.perms.has(ply, "unscramble.mute"))
						Command_Mute.mute(ply);
					else
					{
						ply.sendMessage(noPerms);
					}
				}
				else
					ply.sendMessage(invalidCmd);
			}
			else if (args.length >= 2)
			{
				if (args[0].equalsIgnoreCase("newgame"))
				{
					if (Vars.perms.has(ply, "unscramble.newgame"))
						Command_NewGame.newgame(ply, UnscrambleHelperMethods.seperateArgs(args));
					else
						ply.sendMessage(noPerms);
				}
				else
					ply.sendMessage(invalidCmd);
			}
			else
			{
				ply.sendMessage(invalidCmd);
			}
			return true;
		}
		
		if (args.length == 0)
		{
			Command_Welcome.welcomeConsole(p);
		}
		else if (args.length == 1)
		{
			if (args[0].equalsIgnoreCase("help"))
			{
				Command_Help.help(p);
			}
			else if (args[0].equalsIgnoreCase("reload"))
			{
				Command_Reload.reload(p);
			}
			else if (args[0].equalsIgnoreCase("hint"))
			{
				Command_Hint.hint(p);
			}
			else if (args[0].equalsIgnoreCase("cancel"))
			{
				Command_Cancel.cancel(p);
			}
			else
				p.sendMessage(consoleInvalidCmd);
		}
		else if (args.length >= 2)
		{
			if (args[0].equalsIgnoreCase("newgame"))
			{
				Command_NewGame.newgame(p, UnscrambleHelperMethods.seperateArgs(args));
			}
			else
				p.sendMessage(consoleInvalidCmd);
		}
		else
		{
			p.sendMessage(consoleInvalidCmd);
		}
		return true;
	}
}