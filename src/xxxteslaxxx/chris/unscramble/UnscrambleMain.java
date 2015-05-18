package xxxteslaxxx.chris.unscramble;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import xxxteslaxxx.chris.unscramble.commands.CommandHandler;

public class UnscrambleMain extends JavaPlugin
{
	public void onEnable()
	{
		new Vars(this);
		
		if (!setupPermissions())
		{
			Vars.log.log(Level.SEVERE, "[Unscramble] No Permission found! Disabling plugin!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		if (!setupEconomy())
		{
			Vars.log.log(Level.SEVERE, "[Unscramble] No Economy found! Disabling plugin!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		if (!setupEssentials())
		{
			Vars.log.log(Level.SEVERE, "[WorldClaim] Essentials plugin not found!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		try
		{
			firstRun();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		loadYamls();
		
		getServer().getPluginManager().registerEvents(new UnscrambleListener(), this);
		
		CommandHandler commandHandler = new CommandHandler();
		getCommand("Unscramble").setExecutor(commandHandler);
		getCommand("us").setExecutor(commandHandler);
		
		Vars.auto = new UnscrambleAutomationThread();
		
		Vars.log.log(Level.INFO, "[Unscramble] Version " + Vars.versionNumber);
		Vars.log.log(Level.INFO, "[Unscramble] Started successfully.");
	}
	
	public void onDisable()
	{
		Vars.log.log(Level.INFO, "[Unscramble] Disabling plugin.");
	}
	
	private void firstRun() throws Exception
	{
		if (!Vars.configFile.exists())
		{
			Vars.configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), Vars.configFile);
			
			copy(getResource("words.txt"), new File(getDataFolder(), "words.txt"));
			copy(getResource("words_animal.txt"), new File(getDataFolder(), "words_animal.txt"));
			copy(getResource("words_body.txt"), new File(getDataFolder(), "words_body.txt"));
			copy(getResource("words_body_organs.txt"), new File(getDataFolder(), "words_body_organs.txt"));
			copy(getResource("words_colors.txt"), new File(getDataFolder(), "words_colors.txt"));
			copy(getResource("words_countries.txt"), new File(getDataFolder(), "words_countries.txt"));
			copy(getResource("words_dinosaur.txt"), new File(getDataFolder(), "words_dinosaur.txt"));
			copy(getResource("words_dogs.txt"), new File(getDataFolder(), "words_dogs.txt"));
			copy(getResource("words_food.txt"), new File(getDataFolder(), "words_food.txt"));
			copy(getResource("words_food_bread.txt"), new File(getDataFolder(), "words_food_bread.txt"));
			copy(getResource("words_food_drinks.txt"), new File(getDataFolder(), "words_food_drinks.txt"));
			copy(getResource("words_food_fruit.txt"), new File(getDataFolder(), "words_food_fruit.txt"));
			copy(getResource("words_food_meat.txt"), new File(getDataFolder(), "words_food_meat.txt"));
			copy(getResource("words_food_vegetable.txt"), new File(getDataFolder(), "words_food_vegetable.txt"));
			copy(getResource("words_insects.txt"), new File(getDataFolder(), "words_insects.txt"));
			copy(getResource("words_instruments.txt"), new File(getDataFolder(), "words_instruments.txt"));
			copy(getResource("words_jobs.txt"), new File(getDataFolder(), "words_jobs.txt"));
			copy(getResource("words_mail.txt"), new File(getDataFolder(), "words_mail.txt"));
			copy(getResource("words_olympicsports.txt"), new File(getDataFolder(), "words_olympicsports.txt"));
			copy(getResource("words_parties.txt"), new File(getDataFolder(), "words_parties.txt"));
		}
	}
	
	private void copy(InputStream in, File file)
	{
		try
		{
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
			{
				
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadYamls()
	{
		try
		{
			Vars.config.load(Vars.configFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Vars.importVariables();
	}
	
	public void saveYamls()
	{
		try
		{
			Vars.config.save(Vars.configFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null)
		{
			Vars.perms = permissionProvider.getProvider();
		}
		return (Vars.perms != null);
	}
	
	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
		{
			Vars.eco = economyProvider.getProvider();
		}
		
		return (Vars.eco != null);
	}
	
	private boolean setupEssentials()
	{
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Essentials");
		if (plugin != null && plugin.isEnabled() && (plugin instanceof Essentials))
		{
			Vars.essentials = (Essentials) plugin;
			return true;
		}
		else
		{
			return false;
		}
		
	}
}