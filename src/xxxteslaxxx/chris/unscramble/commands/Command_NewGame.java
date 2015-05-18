package xxxteslaxxx.chris.unscramble.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xxxteslaxxx.chris.unscramble.UnscrambleGameSession;
import xxxteslaxxx.chris.unscramble.UnscrambleHelperMethods;
import xxxteslaxxx.chris.unscramble.Vars;
import xxxteslaxxx.chris.unscramble.custom_objects.MyException;
import xxxteslaxxx.chris.unscramble.custom_objects.Word;

public class Command_NewGame
{
	public static void newgame(Player p, ArrayList<String> args)
	{
		if (Vars.session != null)
		{
			p.sendMessage("§a[Unscramble] §4There is already a game running.");
			p.sendMessage("§a[Unscramble] §4Use §c/us cancel §4to cancel the current game.");
			return;
		}
		
		Word w;
		try
		{
			w = parseArguments(args);
		}
		catch (MyException t)
		{
			p.sendMessage("§a[Unscramble] §4" + t.getString());
			return;
		}
		
		if (w.getPrize() != "")
		{
			if (w.isPrizeMoney())
			{
				if (!Vars.perms.has(p, "unscramble.newgame.prize.$") && !Vars.perms.has(p, "unscramble.newgame.prize.*") && !Vars.perms.has(p, "unscramble.newgame.*")
						&& !Vars.perms.has(p, "unscramble.*"))
				{
					p.sendMessage("§a[Unscramble] §4You do not have permission for that prize");
					return;
				}
			}
			else
			{
				if (!Vars.perms.has(p, "unscramble.newgame.prize." + w.getPrize()) && !Vars.perms.has(p, "unscramble.newgame.prize.*") && !Vars.perms.has(p, "unscramble.newgame.*")
						&& !Vars.perms.has(p, "unscramble.*"))
				{
					p.sendMessage("§a[Unscramble] §4You do not have permission for that prize");
					return;
				}
			}
		}
		
		if (w.getAmountEquation() != "")
		{
			if (!Vars.perms.has(p, "unscramble.newgame.amount") && !Vars.perms.has(p, "unscramble.newgame.*") && !Vars.perms.has(p, "unscramble.*"))
			{
				p.sendMessage("§a[Unscramble] §4You do not have permission for amount");
				return;
			}
		}
		
		if (w.getGameTimeEquation() != "")
		{
			if (!Vars.perms.has(p, "unscramble.newgame.time") && !Vars.perms.has(p, "unscramble.newgame.*") && !Vars.perms.has(p, "unscramble.*"))
			{
				p.sendMessage("§a[Unscramble] §4You do not have permission for timer");
				return;
			}
		}
		
		if (w.getHintTimeEquation() != "")
		{
			if (!Vars.perms.has(p, "unscramble.newgame.hintinterval") && !Vars.perms.has(p, "unscramble.newgame.*") && !Vars.perms.has(p, "unscramble.*"))
			{
				p.sendMessage("§a[Unscramble] §4You do not have permission for hint interval");
				return;
			}
		}
		
		if (w.getCategory() != "")
		{
			if (!Vars.perms.has(p, "unscramble.newgame.category") && !Vars.perms.has(p, "unscramble.newgame.*") && !Vars.perms.has(p, "unscramble.*"))
			{
				p.sendMessage("§a[Unscramble] §4You do not have permission for category");
				return;
			}
		}
		
		Vars.session = new UnscrambleGameSession(w);
		
		Vars.auto.passedTime = 0;
		
	}
	
	public static void newgame(CommandSender p, ArrayList<String> args)
	{
		if (Vars.session != null)
		{
			p.sendMessage("[Unscramble] There is already a game running.");
			p.sendMessage("[Unscramble] Use /us cancel to cancel the current game.");
			return;
		}
		
		Word w;
		try
		{
			w = parseArguments(args);
		}
		catch (MyException t)
		{
			p.sendMessage("[Unscramble] " + t.getString());
			return;
		}
		
		Vars.session = new UnscrambleGameSession(w);
		
		Vars.auto.passedTime = 0;
	}
	
	private static Word parseArguments(ArrayList<String> args) throws MyException
	{
		String word = "";
		
		String prize = "";
		String prizeAmount = "";
		String gameTimer = "";
		String hintTimer = "";
		String category = "";
		
		String prize2 = "";
		String prizeAmount2 = "";
		String gameTimer2 = "";
		String hintTimer2 = "";
		String category2 = "";
		
		for (String arg : args)
		{
			if ((arg.startsWith("w:")) || (arg.startsWith("W:")))
			{
				arg = arg.substring(2);
				
				if (arg.equalsIgnoreCase("random"))
				{
					Word w = UnscrambleHelperMethods.getRandomWord();
					arg = w.getWord();
					
					prize2 = w.getPrize();
					prizeAmount2 = w.getAmountEquation();
					gameTimer2 = w.getGameTimeEquation();
					hintTimer2 = w.getHintTimeEquation();
					category2 = w.getCategory();
				}
				
				word = arg.replaceAll("_", " ").replaceAll("[^ a-zA-Z0-9]", "");
			}
			else if ((arg.startsWith("p:")) || (arg.startsWith("P:")))
			{
				prize = arg.substring(2);
			}
			else if ((arg.startsWith("a:")) || (arg.startsWith("A:")))
			{
				prizeAmount = arg.substring(2);
			}
			else if ((arg.startsWith("t:")) || (arg.startsWith("T:")))
			{
				gameTimer = arg.substring(2);
			}
			else if ((arg.startsWith("h:")) || (arg.startsWith("H:")))
			{
				hintTimer = arg.substring(2);
			}
			else if ((arg.startsWith("c:")) || (arg.startsWith("C:")))
			{
				category = arg.substring(2);
			}
			else
			{
				throw new MyException(arg + " is not a valid argument");
			}
		}
		
		if (word == "")
		{
			throw new MyException("You did not include a w: aregument");
		}
		
		// If user didnt define a modifier, the predefined one (if any exist) will be put in.
		if (prize == "")
			prize = prize2;
		if (prizeAmount == "")
			prizeAmount = prizeAmount2;
		if (category == "")
			category = category2;
		if (hintTimer == "")
			hintTimer = hintTimer2;
		if (gameTimer == "")
			gameTimer = gameTimer2;
		
		Word w;
		
		try
		{
			w = new Word(word, prize, prizeAmount, category, hintTimer, gameTimer);
		}
		catch (MyException t)
		{
			throw t;
		}
		
		return w;
	}
}