package xxxteslaxxx.chris.unscramble;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.entity.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xxxteslaxxx.chris.unscramble.custom_objects.Word;

public class UnscrambleHelperMethods
{
	public static String scrambleWord(String word)
	{
		char[] originalWord = word.toCharArray();
		char[] wordarray = word.toCharArray();
		ArrayList<Character> charList = new ArrayList<Character>();
		for (char c : wordarray)
		{
			if (c != ' ')
			{
				charList.add(c);
			}
			
		}
		Collections.shuffle(charList);
		
		for (int index = 0; index < originalWord.length; index++)
		{
			Character cha = originalWord[index];
			if (Character.isSpaceChar(cha))
			{
				charList.add(index, ' ');
			}
		}
		
		String returnword = "";
		
		for(char c : charList)
		{
			returnword = returnword + c;
		}
		
		return returnword;
	}
	
	public static boolean doesItHaveLetters(String word)
	{
		for (int index = 65; index < 91; index++)
		{
			if (word.contains(String.valueOf((char) index)))
			{
				return true;
			}
			
		}
		
		for (int index = 97; index < 122; index++)
		{
			if (word.contains(String.valueOf((char) index)))
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	public static Word getRandomWord()
	{
		return Vars.wordList.get(Vars.random.nextInt(Vars.wordList.size()));
	}
	
	public static ArrayList<String> seperateArgs(String[] args)
	{
		ArrayList<String> newArgs = new ArrayList<String>();
		
		for (int index = 1; index < args.length; index++)
		{
			newArgs.add(args[index]);
		}
		
		return newArgs;
	}
	
	public static String updateCheck() throws Exception
	{
		String pluginUrlString = "http://dev.bukkit.org/server-mods/unscramble/files.rss";
		String lastestVersion = "";
		try
		{
			URL url = new URL(pluginUrlString);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getElementsByTagName("item");
			Node firstNode = nodes.item(0);
			if (firstNode.getNodeType() == 1)
			{
				Element firstElement = (Element) firstNode;
				NodeList firstElementTagName = firstElement.getElementsByTagName("title");
				Element firstNameElement = (Element) firstElementTagName.item(0);
				NodeList firstNodes = firstNameElement.getChildNodes();
				lastestVersion = firstNodes.item(0).getNodeValue();
			}
		}
		catch (Exception localException)
		{
		}
		return lastestVersion;
	}
	
	public static void broadcastMessage(String s)
	{
		for(Player p : Vars.plugin.getServer().getOnlinePlayers())
		{
			if(!Vars.mutedPlayers.contains(p.getUniqueId()))
			{
				p.sendMessage(s);
			}
		}
	}
}