package xxxteslaxxx.chris.unscramble;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import xxxteslaxxx.chris.unscramble.custom_objects.ClaimPrize;
import xxxteslaxxx.chris.unscramble.custom_objects.MyException;
import xxxteslaxxx.chris.unscramble.custom_objects.Word;
import bsh.Interpreter;

import com.earth2me.essentials.Essentials;

public class Vars
{
	public static UnscrambleMain		plugin;
	
	public static File					configFile;
	public static FileConfiguration		config;
	public static Permission			perms;
	public static Economy				eco;
	public static Logger				log;
	public static UnscrambleGameSession	session;
	
	public static String pluginName;
	public static String versionNumber;
	
	public static String pluginWithVersion;
	
	public static ArrayList<ClaimPrize>	prizes;
	
	public static ArrayList<Word>		wordList;
	
	public static Random				random;
	
	public static Interpreter			interpreter;
	
	public static ArrayList<UUID>		mutedPlayers;
	
	// Yaml Variables
	public static boolean				scramble_words_separately;
	public static int					max_hints;
	public static boolean				display_answer_on_failed_games;
	public static boolean				display_prize;
	public static boolean				check_for_updates;
	public static String				word_list;
	public static boolean				automated_games;
	public static int 					initial_offset;
	public static int					between_unscramble_games;
	public static int					min_number_users_unscramble;
	
	public static Essentials			essentials;
	
	public static UnscrambleAutomationThread auto;
	
	public Vars(UnscrambleMain us)
	{
		plugin = us;
		config = new YamlConfiguration();
		log = Logger.getLogger("Minecraft");
		
		configFile = new File(plugin.getDataFolder(), "config.yml");
		
		pluginName = plugin.getDescription().getName();
		versionNumber = plugin.getDescription().getVersion();
		
		pluginWithVersion = pluginName + " " + versionNumber;
		
		prizes = new ArrayList<ClaimPrize>();
		
		wordList = new ArrayList<Word>();
		
		random = new Random();
		
		interpreter = new Interpreter();
		
		mutedPlayers = new ArrayList<UUID>();
	}
	
	public static void importVariables()
	{
		scramble_words_separately = config.getBoolean("scramble_words_separately", true);
		
		max_hints = config.getInt("max_hints", -1);
		
		display_answer_on_failed_games = config.getBoolean("display_answer_on_failed_games", true);
		
		display_prize = config.getBoolean("display_prize", true);
		
		check_for_updates = config.getBoolean("check_for_updates", true);
		
		word_list = config.getString("word_list", "");
		
		automated_games = config.getBoolean("automated_games", true);
		
		initial_offset = config.getInt("initial_offset", 450);
		
		between_unscramble_games = config.getInt("between_unscramble_games", 900);
		
		min_number_users_unscramble = config.getInt("min_number_users_unscramble", 5);
		
		try
		{
			readWordsFile(word_list, "", "", "", "", "");
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, "[Unscramble] Unable to load word list!");
			return;
		}
		
		if (wordList.size() == 0)
		{
			log.log(Level.SEVERE, "[Unscramble] No words found in word list!");
		}
		else
		{
			log.log(Level.SEVERE, "[Unscramble] " + wordList.size() + " words found.");
		}
	}
	
	private static void readWordsFile(String wordsFile, String prize, String amountEquation, String category, String hintTimeEquation, String gameTimeEquation) throws IOException
	{
		Vars.log.log(Level.INFO, "[Unscramble] Reading file: " + wordsFile);
		File f = new File(plugin.getDataFolder(), wordsFile);
		Pattern pattern;
		Matcher matcher;
		
		if(!f.exists())
		{
			log.log(Level.SEVERE, "[Unscramble] Error reading file: " + wordsFile);
			return;
		}
		
		try (Scanner scanner = new Scanner(f))
		{
			while (scanner.hasNextLine())
			{
				String line = String.valueOf(scanner.nextLine());
				
				// Blank line
				pattern = Pattern.compile("^ *$");
				matcher = pattern.matcher(line);
				
				if (matcher.find()) // found a blank line
				{
					continue;
				}
				
				if (line.startsWith("# "))
				{
					continue;
				}
				
				String[] args = line.split("\t");
				
				// Include another list
				if (args[0].trim().equalsIgnoreCase("#include"))
				{
					String nextWordList = "";
					String nextPrize = "";
					String nextAmountEquation = "";
					String nextCategory = "";
					String nextHintTimeEquation = "";
					String nextGameTimeEquation = "";
					
					if (args[1].trim().contains(".txt"))
						nextWordList = args[1];
					else
						break;
					
					for (String s : args)
					{
						if (s.trim().startsWith("p:"))
							nextPrize = s.substring(2).trim();
						else if (s.trim().startsWith("a:"))
							nextAmountEquation = s.substring(2).trim();
						else if (s.trim().startsWith("c:"))
							nextCategory = s.substring(2).trim();
						else if (s.trim().startsWith("h:"))
							nextHintTimeEquation = s.substring(2).trim();
						else if (s.trim().startsWith("t:"))
							nextGameTimeEquation = s.substring(2).trim();
					}
					
					// If the prize is null, but the amount equation isnt, then set the amount equation to null
					// There shouldnt be an amount if there isnt even a prize
					if (nextPrize == "")
						nextAmountEquation = "";
					
					readWordsFile(nextWordList, nextPrize, nextAmountEquation, nextCategory, nextHintTimeEquation, nextGameTimeEquation);
				}
				// Else its a word
				else
				{
					String word = args[0].trim();
					String tempPrize = "";
					String tempAmountEquation = "";
					String tempCategory = "";
					String tempHintTimeEquation = "";
					String tempGameTimeEquation = "";
					
					if (word.startsWith("#"))
						continue;
					
					for (String s : args)
					{
						if (s.trim().startsWith("p:"))
							tempPrize = s.substring(2).trim();
						else if (s.trim().startsWith("a:"))
							tempAmountEquation = s.substring(2).trim();
						else if (s.trim().startsWith("c:"))
							tempCategory = s.substring(2).trim();
						else if (s.trim().startsWith("h:"))
							tempHintTimeEquation = s.substring(2).trim();
						else if (s.trim().startsWith("t:"))
							tempGameTimeEquation = s.substring(2).trim();
					}
					
					if (tempPrize == "")
						tempPrize = prize;
					
					if (tempAmountEquation == "")
						tempAmountEquation = amountEquation;
					
					if (tempCategory == "")
						tempCategory = category;
					
					if (tempHintTimeEquation == "")
						tempHintTimeEquation = hintTimeEquation;
					
					if (tempGameTimeEquation == "")
						tempGameTimeEquation = gameTimeEquation;
					
					// If the prize is null, but the amount equation isnt, then set the amount equation to null
					// There shouldnt be an amount if there isnt even a prize
					if (tempPrize == "")
						tempAmountEquation = "";
					
					Word w;
					
					try
					{
						w = new Word(word, tempPrize, tempAmountEquation, tempCategory, tempHintTimeEquation, tempGameTimeEquation);
					}
					catch (MyException t)
					{
						log.log(Level.SEVERE, "[Unscramble] The word " + word + " has mondifiers that are invalid. Please remove it from any lists.");
						continue;
					}
					
					wordList.add(w);
				}
			}
		}// end of try statement
	}
}