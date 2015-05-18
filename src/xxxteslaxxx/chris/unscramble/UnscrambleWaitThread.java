package xxxteslaxxx.chris.unscramble;

import org.bukkit.entity.Player;

public class UnscrambleWaitThread implements Runnable
{
	Player			p;
	private Thread	thread;
	String			congrats;
	String			claim;
	
	public UnscrambleWaitThread(Player p, String congrats, String claim)
	{
		this.p = p;
		this.congrats = congrats;
		this.claim = claim;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public void run()
	{
		try
		{
			Thread.sleep(300L);
		}
		catch (Throwable localThrowable)
		{
		}
		
		UnscrambleHelperMethods.broadcastMessage(this.congrats);
		if (this.claim != "")
		{
			this.p.sendMessage(this.claim);
		}
	}
}