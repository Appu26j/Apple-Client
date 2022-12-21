package apple26j;

import apple26j.Discord;
import apple26j.DiscordEventHandlers;
import apple26j.DiscordRichPresence;

public class DiscordRPC
{
	static
	{
		Discord.TRY_TO_AVOID_NATIVE_ERRORS = false;
	}
	
	public static void init()
	{
		try
		{
			Discord.Initialize("1042779450892357652", new DiscordEventHandlers(), 1, null);
			updatePresence("Launching Apple Client");
		}
		
		catch(Throwable t)
		{
			;
		}
	}
	
	public static void updatePresence(String text)
	{
		try
		{
			DiscordRichPresence discordRichPresence = new DiscordRichPresence();
			discordRichPresence.State = text;
			discordRichPresence.LargeImageKey = "apple";
			discordRichPresence.LargeImageText = "Apple Client";
			Discord.UpdatePresence(discordRichPresence);
		}
		
		catch(Throwable t)
		{
			;
		}
	}
	
	public static void shutdown()
	{
		try
		{
			Discord.UpdatePresence(null);
			Discord.Shutdown();
		}
		
		catch(Throwable t)
		{
			;
		}
	}
}
