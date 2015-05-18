package xxxteslaxxx.chris.unscramble.commands;

import org.bukkit.entity.Player;

import xxxteslaxxx.chris.unscramble.Vars;

public class Command_Mute
{

	public static void mute(Player ply)
	{
		if(Vars.mutedPlayers.contains(ply.getUniqueId()))
		{
			ply.sendMessage("§a[Unscramble] §3You will now receive game messages.");
			Vars.mutedPlayers.remove(ply.getUniqueId());
		}
		else
		{
			ply.sendMessage("§a[Unscramble] §3You have muted the unscramble game.");
			Vars.mutedPlayers.add(ply.getUniqueId());
		}
	}
	
}
