package xxxteslaxxx.chris.unscramble;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class UnscrambleListener implements Listener
{
	@EventHandler(priority = EventPriority.MONITOR)
	public void playerJoin(PlayerJoinEvent event)
	{
		Player p = event.getPlayer();
		
		if (!Vars.check_for_updates)
		{
			return;
		}
		
		if (p.isOp())
		{
			String lastestVersion = "";
			try
			{
				lastestVersion = UnscrambleHelperMethods.updateCheck();
			}
			catch (Throwable localThrowable)
			{
			}
			
			if (!lastestVersion.equalsIgnoreCase(Vars.pluginWithVersion))
			{
				p.sendMessage("§5=====================================================");
				p.sendMessage("§4 Warning!§f This isnt the lastest version of Unscramble!");
				p.sendMessage("§c " + lastestVersion + "§f Is out! (This is §c" + Vars.pluginWithVersion + "§f)");
				p.sendMessage("§5=====================================================");
			}
		}
	}
	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void playerChat(AsyncPlayerChatEvent event)
	{
		if(Vars.session == null)
		{
			return;
		}
		else
		{
			Vars.session.playerChat(event.getPlayer(), event.getMessage());
		}
	}
}