package xxxteslaxxx.chris.unscramble;

public class UnscrambleAutomationThread implements Runnable
{
	private Thread			thread;
	
	public static boolean	running;
	public int				passedTime;
	public int				initialOffset;
	
	UnscrambleAutomationThread()
	{
		thread = new Thread(this);
		running = false;
		passedTime = 0;
		initialOffset = Vars.initial_offset;
		
		if (Vars.automated_games)
		{
			running = true;
			
			thread.start();
		}
	}
	
	public void run()
	{
		while (running)
		{
			try
			{
				Thread.sleep(1000L);
			}
			catch (Throwable t)
			{
				running = false;
			}
			
			if (initialOffset != 0)
			{
				initialOffset--;
			}
			else
			{
				passedTime += 1;
				
				if (passedTime == Vars.between_unscramble_games)
				{
					if (Vars.plugin.getServer().getOnlinePlayers().length >= Vars.min_number_users_unscramble && Vars.session == null)
					{
						Vars.session = new UnscrambleGameSession(UnscrambleHelperMethods.getRandomWord());
					}
					
					passedTime = 0;
				}
			}
		}
	}
}
