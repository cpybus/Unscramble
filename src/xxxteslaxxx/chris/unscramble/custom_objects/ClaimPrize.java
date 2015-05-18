package xxxteslaxxx.chris.unscramble.custom_objects;

import org.bukkit.entity.Player;

import xxxteslaxxx.chris.unscramble.Vars;

public class ClaimPrize
{
	public Player p;
	public Word w;
	
	public ClaimPrize(Player p, Word w)
	{
		this.p = p;
		this.w = w;
	}
	
	public void awardToPlayer(Player ply)
	{
		if(w.hasPrize())
		{
			if (w.isPrizeItem())
			{
				ply.getInventory().addItem(w.getPrizeItem());
			}
			else if(w.isPrizeMoney())
			{
				Vars.eco.depositPlayer(Vars.plugin.getServer().getOfflinePlayer(ply.getUniqueId()), w.getCalculatedAmount());
			}
		}
	}
}
