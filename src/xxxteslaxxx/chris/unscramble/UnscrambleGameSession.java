package xxxteslaxxx.chris.unscramble;

import java.util.Random;

import org.bukkit.entity.Player;
import xxxteslaxxx.chris.unscramble.custom_objects.ClaimPrize;
import xxxteslaxxx.chris.unscramble.custom_objects.MyException;
import xxxteslaxxx.chris.unscramble.custom_objects.Word;

public class UnscrambleGameSession implements Runnable
{
	public Word		word;
	public String	currentHint;
	public int		timePassed			= 0;
	public int		hintsGiven			= 0;
	public int		currentChat			= 0;
	public int		currentHintInterval	= 0;
	
	public boolean	running;
	
	private Thread	thread;
	
	public UnscrambleGameSession(Word word)
	{
		this.word = word;
		this.word.getScrambledWord();
		this.currentHint = word.getWord().replaceAll("[a-zA-Z0-9]", "*");
		
		UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3New Game! Unscramble this: §c§o" + word.getScrambledWord());
		
		if (word.hasPrize())
		{
			if (Vars.display_prize)
			{
				if (this.word.isPrizeMoney())
				{
					if (word.getCalculatedAmount() == 1)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3The prize for winning is " + word.getCalculatedAmount() + " " + Vars.eco.currencyNameSingular());
					}
					else if (word.getCalculatedAmount() > 1)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3The prize for winning is " + word.getCalculatedAmount() + " " + Vars.eco.currencyNamePlural());
					}
				}
				else if (this.word.isPrizeItem())
				{
					UnscrambleHelperMethods.broadcastMessage(
							"§a[Unscramble] §3The prize for winning is " + word.getCalculatedAmount() + " " + word.getPrizeItem().getType().name().toLowerCase());
				}
			}
		}
		
		if(word.getCategory() != "")
		{
			UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3The category for the word is: §c" + word.getCategory());
		}
		
		if(word.getCalculatedGameTime() > 0)
		{
			UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3Starting the timer at §c" + word.getCalculatedGameTime() + " §3seconds!");
		}
		
		running = true;
		
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public void giveHint() throws MyException
	{
		
		if (hintsGiven >= Vars.max_hints && Vars.max_hints != -1)
		{
			throw new MyException("You cannot give anymore hints. Change this setting in the config file.");
		}
		else
		{
			char[] starWord = this.currentHint.toCharArray();
			Random rand = new Random();
			
			if (this.currentHint.contains("*"))
			{
				int index;
				do
				{
					index = rand.nextInt(this.word.getWord().length());
				}
				while ((starWord[index] != '*') || (starWord[index] == ' '));
				starWord[index] = this.word.getWord().charAt(index);
				this.currentHint = String.valueOf(starWord);
			}
			UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3Hint!... §c" + this.currentHint + "§3, for scrambled word §c" + word.getScrambledWord());
			hintsGiven += 1;
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
			
			timePassed += 1;
			
			if(running == false)
				break;
			
			if (word.getCalculatedHintTime() != 0 && timePassed != 0)
			{
				if (timePassed % word.getCalculatedHintTime() == 0)
				{
					try
					{
						giveHint();
					}
					catch (MyException e)
					{
						
					}
				}
			}
			
			if (word.getCalculatedGameTime() != 0)
			{
				if (timePassed == word.getCalculatedGameTime())
				{
					UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 Oh! Sorry, you didnt get the word in time!");
					if (Vars.display_answer_on_failed_games)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3The answer was... §c" + word.getWord());
					}
					running = false;
				}
				else
				{
					int t = word.getCalculatedGameTime() - timePassed;
					
					if (t == 180)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 3 Minutes Left");
					}
					else if (t == 150)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 2 Minutes 30 Seconds Left");
					}
					else if (t == 120)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 2 Minutes Left");
					}
					else if (t == 90)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 1 Minute 30 Seconds Left");
					}
					else if (t == 60)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 1 Minute Left");
					}
					else if (t == 45)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 45 Seconds Left");
					}
					else if (t == 30)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 30 Seconds Left");
					}
					else if (t == 20)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 20 Seconds Left");
					}
					else if (t == 15)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 15 Seconds Left");
					}
					else if (t == 10)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 10 Seconds Left");
					}
					else if (t == 5)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 5 Seconds Left");
					}
					else if (t == 3)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 3 Seconds Left");
					}
					else if (t == 2)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 2 Seconds Left");
					}
					else if (t == 1)
					{
						UnscrambleHelperMethods.broadcastMessage("§a[Unscramble]§3 1 Second Left");
					}
				}
				
			}
		}
		
		Vars.session = null;
		return;
	}
	
	public void playerChat(Player p, String msg)
	{
		if(running == false)
		{
			return;
		}
		
		if (currentChat == 10)
		{
			UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3Again, the word was...§c " + word.getScrambledWord());
			currentChat = 0;
		}
		else
		{
			currentChat += 1;
		}
		
		if (msg.equalsIgnoreCase(word.getWord()))
		{
			running = false;
			
			if (word.hasPrize())
			{
				Vars.prizes.add(new ClaimPrize(p, word));
				new UnscrambleWaitThread(p, "§a[Unscramble] §3Congratulations §6" + p.getName() + "§3! Word was §c" + word.getWord(),
						"§a[Unscramble] §3Use §c/us claim §3to claim your prize!");
			}
			else
			{
				new UnscrambleWaitThread(p, "§a[Unscramble] §3Congratulations §6" + p.getName() + "§3! Word was §c" + word.getWord(), "");
			}
		}
	}
	
	public void cancelGame()
	{
		running = false;
		
		UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3An Admin has canceled the game!");
		if (Vars.display_answer_on_failed_games)
		{
			UnscrambleHelperMethods.broadcastMessage("§a[Unscramble] §3The answer was... §c" + word.getWord());
		}
	}
}