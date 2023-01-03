package apple26j.settings;

import java.util.ArrayList;
import java.util.stream.Collectors;

import apple26j.mods.Mod;

public class SettingsManager
{
	private ArrayList<Setting> settings = new ArrayList<>();
	
	public void addSetting(Setting setting)
	{
		this.settings.add(setting);
	}
	
	public Setting getSetting(String name, Mod parentMod)
	{
		return this.settings.stream().filter(setting -> setting.getName().equalsIgnoreCase(name) && setting.getParentMod().equals(parentMod)).findFirst().orElse(null);
	}
	
	public ArrayList<Setting> getSettings(Mod parentMod)
	{
		return this.settings.stream().filter(setting -> setting.getParentMod().equals(parentMod)).collect(Collectors.toCollection(ArrayList::new));
	}
}
