package apple26j.config;

import java.io.*;
import java.nio.file.Files;

import apple26j.Apple;
import apple26j.mods.*;
import apple26j.settings.Setting;

public class Config
{
	private static final File config = new File("config.txt");
	
	private Config()
	{
		;
	}
	
	public static void saveMods()
	{
		if (!config.exists())
		{
			createConfig();
		}
		
		new Thread(() ->
		{
			try (PrintWriter printWriter = new PrintWriter(new FileWriter(config)))
			{
				for (Mod mod : Apple.CLIENT.getModsManager().getMods(Category.ALL))
				{
					printWriter.write(mod.getName() + " " + (mod.getName().toLowerCase().endsWith("s") ? "are enabled" : "is enabled") + ": " + mod.isEnabled() + "\n\n");
					
					for (Setting setting : Apple.CLIENT.getSettingsManager().getSettings(mod))
					{
						if (setting.getTypeOfSetting().equals("Check Box"))
						{
							printWriter.write(mod.getName() + (mod.getName().toLowerCase().endsWith("s") ? "'" : "'s") + " " + setting.getName() + " is enabled: " + setting.getCheckBoxValue() + "\n\n");
						}
						
						if (setting.getTypeOfSetting().equals("Mode"))
						{
							printWriter.write(mod.getName() + (mod.getName().toLowerCase().endsWith("s") ? "'" : "'s") + " " + setting.getName() + ": " + setting.getModeValue() + "\n\n");
						}
						
						if (setting.getTypeOfSetting().equals("Slider"))
						{
							printWriter.write(mod.getName() + (mod.getName().toLowerCase().endsWith("s") ? "'" : "'s") + " " + setting.getName() + ": " + setting.getSliderValue() + "\n\n");
						}
					}
				}
			}
			
			catch (Exception e)
			{
				;
			}
		}).start();
	}
	
	public static void loadMods()
	{
		if (!config.exists())
		{
			createConfig();
		}
		
		new Thread(() ->
		{
			try (BufferedReader bufferedReader = Files.newBufferedReader(config.toPath()))
			{
				String line;
				
				while ((line = bufferedReader.readLine()) != null)
				{
					if (line.isEmpty())
					{
						continue;
					}
					
					if (!line.contains("'"))
					{
						Mod modification = null;
						
						for (Mod mod : Apple.CLIENT.getModsManager().getMods(Category.ALL))
						{
							if (line.contains(mod.getName()))
							{
								modification = mod;
								break;
							}
						}
						
						if (modification != null)
						{
							modification.setEnabled(line.contains("true"));
						}
					}
				}
			}
			
			catch (Exception e)
			{
				;
			}
		}).start();
	}
	
	private static void createConfig()
	{
		try
		{
			config.createNewFile();
		}
		
		catch (Exception e)
		{
			;
		}
	}
}
