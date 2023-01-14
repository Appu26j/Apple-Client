package apple26j;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import apple26j.interfaces.MinecraftInterface;
import net.minecraft.src.Config;

public class UpdateCheckThread extends Thread implements MinecraftInterface
{
	// The update status
	private CheckStatus checkStatus = mc.gameSettings.checkForUpdates ? CheckStatus.CHECKING : CheckStatus.UP_TO_DATE;
	
	@Override
	public void run()
	{
		super.run();
		
		if (mc.gameSettings.checkForUpdates)
		{
			try
			{
				// Checks for updates
				HttpsURLConnection httpsURLConnection = ((HttpsURLConnection) (new URL("https://pastebin.com/raw/G6Vd208t")).openConnection());
				httpsURLConnection.setDoInput(true);
				httpsURLConnection.setDoOutput(false);
				httpsURLConnection.connect();
				double version = Double.parseDouble(Config.readInputStream(httpsURLConnection.getInputStream()).replaceAll("\n", ""));		
				httpsURLConnection.disconnect();	
				this.checkStatus = Apple.CLIENT_VERSION < version ? CheckStatus.AVAILABLE : CheckStatus.UP_TO_DATE;
			}
			
			catch (Exception e)
			{
				;
			}
		}
	}
	
	public CheckStatus getCheckStatus()
	{
		return this.checkStatus;
	}
	
	public static enum CheckStatus
	{
		CHECKING, UP_TO_DATE, AVAILABLE;
	}
}
