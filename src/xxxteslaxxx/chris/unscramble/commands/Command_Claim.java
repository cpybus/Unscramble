package xxxteslaxxx.chris.unscramble.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import xxxteslaxxx.chris.unscramble.Vars;
import xxxteslaxxx.chris.unscramble.custom_objects.ClaimPrize;

public class Command_Claim
{
	public static void claim(Player ply)
	{
		boolean found = false;
		
		ArrayList<ClaimPrize> tempList = new ArrayList<ClaimPrize>();
		tempList.addAll(Vars.prizes);
		
		for (ClaimPrize prize : tempList)
		{
			if (prize.p == ply)
			{
				prize.awardToPlayer(ply);
				
				found = true;
				Vars.prizes.remove(prize);
			}
		}
		
		if (found)
		{
			ply.sendMessage("§a[Unscramble] §3Your prize(s) has/have been added to you inventory/account.");
		}
		else
		{
			ply.sendMessage("§a[Unscramble] §4No prizes found under your name.");
		}
	}
}