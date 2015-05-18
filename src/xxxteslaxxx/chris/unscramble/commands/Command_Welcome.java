package xxxteslaxxx.chris.unscramble.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xxxteslaxxx.chris.unscramble.Vars;

public class Command_Welcome
{
	public static void welcome(Player p)
	{
		p.sendMessage("§5=====================================================");
		p.sendMessage("§a Welcome to §cUnscramble §aPlugin §9(Version " + Vars.versionNumber + ")");
		p.sendMessage("§a Designed & Programmed by §9Hotshot2162 and xxxTeslaxxx");
		p.sendMessage("§5=====================================================");
	}
	
	public static void welcomeConsole(CommandSender p)
	{
		p.sendMessage("=====================================================");
		p.sendMessage(" Welcome to Unscramble Plugin (Version " + Vars.versionNumber + ")");
		p.sendMessage(" Designed & Programmed by Hotshot2162 and xxxTeslaxx");
		p.sendMessage("=====================================================");
	}
}