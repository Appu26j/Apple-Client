package apple26j.utils;

import java.io.File;

import apple26j.interfaces.MinecraftInterface;
import net.minecraft.client.main.Main;

public class UpdateUtil implements MinecraftInterface
{
	public static void update()
	{
		try
		{
			Runtime.getRuntime().exec(Main.concat(new String[]{"java", "-jar", "Updater.jar"}, Main.args));
			mc.shutdown();
		}
		
		catch (Exception e)
		{
			System.exit(-1);
		}
	}
}
