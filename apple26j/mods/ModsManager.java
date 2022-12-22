package apple26j.mods;

import java.util.ArrayList;
import java.util.stream.Collectors;

import apple26j.mods.general.*;
import apple26j.mods.hud.Animations;

public class ModsManager
{
	private ArrayList<Mod> mods = new ArrayList<>();
	
	public ModsManager()
	{
		this.mods.add(new ToggleSprint());
		this.mods.add(new Animations());
		this.mods.add(new FullBright());
		this.enableDefaultMods();
	}
	
	public void enableDefaultMods()
	{
		this.getMod("Toggle Sprint").setEnabled(true);
		this.getMod("1.7 Animations").setEnabled(true);
	}
	
	public ArrayList<Mod> getMods(Category category)
	{
		return category.equals(Category.ALL) ? this.mods : this.mods.stream().filter(mod -> mod.getCategory().equals(category)).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public Mod getMod(String name)
	{
		return this.mods.stream().filter(mod -> mod.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
}
