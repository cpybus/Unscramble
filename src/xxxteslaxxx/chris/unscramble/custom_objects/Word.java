package xxxteslaxxx.chris.unscramble.custom_objects;

import org.bukkit.inventory.ItemStack;

import xxxteslaxxx.chris.unscramble.UnscrambleHelperMethods;
import xxxteslaxxx.chris.unscramble.Vars;

public class Word
{
	private String		word;
	private String		prize;
	private String		amountEquation;
	private String		category;
	private String		hintTimeEquation;
	private String		gameTimeEquation;
	
	private String		scrambledWord;
	
	// Modifiers, however, as they are all optional, they could be null!
	private ItemStack	prizeItem;
	private int			calculatedAmount = 0;
	private int			calculatedHintTime = 0;
	private int			calculatedGameTime = 0;
	
	public Word(String word, String prize, String amountEquation, String category, String hintTimeEquation, String gameTimeEquation) throws MyException
	{
		this.word = word;
		this.prize = prize;
		this.category = category;
		this.amountEquation = amountEquation;
		this.hintTimeEquation = hintTimeEquation;
		this.gameTimeEquation = gameTimeEquation;
		
		if (!isValidPrize())
			throw new MyException("The prize given was not valid");
		
		if (!isValidAmountEquation())
			throw new MyException("The amount given was not valid");
		else if (prize == "" && amountEquation != "")
			throw new MyException("Amount found, but prize was not.");
		
		if (!isValidHintTimeEquation())
			throw new MyException("The hint time given was not valid");
		
		if (!isValidGameTimeEquation())
			throw new MyException("The game time given was not valid");
		
		if(isPrizeItem())
			prizeItem.setAmount(calculatedAmount);
		
	}
	
	public String getWord()
	{
		return word;
	}
	
	public String getPrize()
	{
		return prize;
	}
	
	public String getAmountEquation()
	{
		return amountEquation;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public String getHintTimeEquation()
	{
		return hintTimeEquation;
	}
	
	public String getGameTimeEquation()
	{
		return gameTimeEquation;
	}
	
	public boolean isPrizeMoney()
	{
		if (prize.trim().equalsIgnoreCase("$"))
			return true;
		
		return false;
	}
	
	public boolean isPrizeItem()
	{
		if (prize.trim().equalsIgnoreCase(""))
			return false;
		
		if (prize.trim().equalsIgnoreCase("$"))
			return false;
		
		return true;
	}
	
	public String toString()
	{
		// return String.format("%20s%15s%15s%15s", word, prize, amountEquation, category);
		return word;
	}
	
	public boolean isValidPrize()
	{
		if (prize.equals(""))
			return true;
		else if (prize.equalsIgnoreCase("$"))
			return true;
		else
		{
			try
			{
				prizeItem = Vars.essentials.getItemDb().get(prize);
			}
			catch (Exception e)
			{
				return false;
			}
			
			return true;
		}
	}
	
	public boolean isValidAmountEquation()
	{
		if (amountEquation.equals(""))
			return true;
		else
		{
			try
			{
				Vars.interpreter.eval("test = " + amountEquation.replaceAll("x", (""+word.length())));
				if (UnscrambleHelperMethods.doesItHaveLetters(Vars.interpreter.get("test").toString()))
				{
					return false;
				}
				else
				{
					calculatedAmount = (int) Double.parseDouble(Vars.interpreter.get("test").toString());
				}
			}
			catch (Throwable t)
			{
				return false;
			}
			return true;
		}
	}
	
	public boolean isValidHintTimeEquation()
	{
		if (hintTimeEquation.equals(""))
			return true;
		else
		{
			try
			{
				Vars.interpreter.eval("test = " + hintTimeEquation.replaceAll("x", (""+word.length())));
				if (UnscrambleHelperMethods.doesItHaveLetters(Vars.interpreter.get("test").toString()))
				{
					return false;
				}
				else
				{
					calculatedHintTime = (int) Double.parseDouble(Vars.interpreter.get("test").toString());
				}
			}
			catch (Throwable t)
			{
				return false;
			}
			return true;
		}
	}
	
	public boolean isValidGameTimeEquation()
	{
		if (gameTimeEquation.equals(""))
			return true;
		else
		{
			try
			{
				Vars.interpreter.eval("test = " + gameTimeEquation.replaceAll("x", (""+word.length())));
				if (UnscrambleHelperMethods.doesItHaveLetters(Vars.interpreter.get("test").toString()))
				{
					return false;
				}
				else
				{
					calculatedGameTime = (int) Double.parseDouble(Vars.interpreter.get("test").toString());
				}
			}
			catch (Throwable t)
			{
				return false;
			}
			return true;
		}
	}
	
	private void scrambleWord()
	{
		String scra = "";
		if (Vars.scramble_words_separately)
		{
			String[] words = word.split(" ");
			scra = "";
			for (String str : words)
			{
				str = UnscrambleHelperMethods.scrambleWord(str);
				scra = scra + str + " ";
			}
			scra = scra.trim();
		}
		else
		{
			scra = UnscrambleHelperMethods.scrambleWord(word);
		}
		
		scrambledWord = scra;
	}
	
	public String getScrambledWord()
	{
		if (scrambledWord == null || scrambledWord == "")
		{
			scrambleWord();
		}
		
		return scrambledWord;
	}
	
	public boolean hasPrize()
	{
		if (prize.equalsIgnoreCase("$") || prizeItem != null)
		{
			return true;
		}
		
		return false;
	}
	
	public ItemStack getPrizeItem()
	{
		return prizeItem;
	}
	
	public int getCalculatedAmount()
	{
		return calculatedAmount;
	}
	
	public int getCalculatedHintTime()
	{
		return calculatedHintTime;
	}
	
	public int getCalculatedGameTime()
	{
		return calculatedGameTime;
	}
	
}
