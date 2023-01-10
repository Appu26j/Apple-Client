package apple26j.mods.hud;

import apple26j.interfaces.ModInterface;
import apple26j.mods.*;
import apple26j.settings.Setting;

@ModInterface(name = "1.7 Animations", description = "Allows you to change the visuals of the game to be like 1.7.10.", category = Category.HUD)
public class Animations extends Mod
{
	public Animations()
	{
		this.addSetting(new Setting("1.7 Item Position", this, true));
		this.addSetting(new Setting("1.7 Block Hit", this, true));
		this.addSetting(new Setting("1.7 Damage", this, true));
	}
}
