package apple26j.utils;

public class UpdateUtil
{
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
		
		System.exit(0);
	}
}
