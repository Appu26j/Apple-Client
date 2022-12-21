package apple26j.utils;

import apple26j.interfaces.MinecraftInterface;

public class UpdateUtil implements MinecraftInterface
{
	public static void update()
	{
		try
		{
			Runtime.getRuntime().exec("", null, null);
			mc.shutdown();
		}
		
		catch (Exception e)
		{
			System.exit(-1);
		}
	}
}
