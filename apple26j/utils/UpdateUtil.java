package apple26j.utils;

import apple26j.interfaces.MinecraftInterface;

public class UpdateUtil implements MinecraftInterface
{
	// Updates Apple Client by launching updater.jar when minecraft closes
	public static void update()
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				ProcessBuilder processBuilder = new ProcessBuilder(new String[]{"java", "-jar", "updater.jar"});
				
				try
				{
					processBuilder.start();
				}
				
				catch (Exception e)
				{
					;
				}
			}
		});
		
		mc.shutdown();
	}
}
