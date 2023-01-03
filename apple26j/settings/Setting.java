package apple26j.settings;

import java.util.ArrayList;

import apple26j.mods.Mod;

public class Setting
{
	private float minSliderValue, sliderValue, maxSliderValue, places;
	private String name, typeOfSetting, modeValue;
	private ArrayList<String> modes;
	private boolean checkBoxValue;
	private Mod parentMod;
	
	public Setting(String name, Mod parentMod, boolean checkBoxValue)
	{
		this.name = name;
		this.parentMod = parentMod;
		this.checkBoxValue = checkBoxValue;
		this.typeOfSetting = "Check Box";
	}
	
	public Setting(String name, Mod parentMod, String modeValue, String... modes)
	{
		this.name = name;
		this.parentMod = parentMod;
		this.modeValue = modeValue;
		this.modes = new ArrayList<>();
		
		for (String mode : modes)
		{
			this.modes.add(mode);
		}
		
		this.typeOfSetting = "Mode";
	}
	
	public Setting(String name, Mod parentMod, float minSliderValue, float sliderValue, float maxSliderValue, float places)
	{
		this.name = name;
		this.parentMod = parentMod;
		this.minSliderValue = minSliderValue;
		this.sliderValue = sliderValue;
		this.maxSliderValue = maxSliderValue;
		this.places = places;
		this.typeOfSetting = "Slider";
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Mod getParentMod()
	{
		return this.parentMod;
	}
	
	public boolean getCheckBoxValue()
	{
		return this.checkBoxValue;
	}
	
	public void setCheckBoxValue(boolean checkBoxValue)
	{
		this.checkBoxValue = checkBoxValue;
	}
	
	public String getModeValue()
	{
		return this.modeValue;
	}
	
	public void setModeValue(String modeValue)
	{
		this.modeValue = modeValue;
	}
	
	public ArrayList <String> getModes()
	{
		return this.modes;
	}
	
	public float getMinSliderValue()
	{
		return this.minSliderValue;
	}
	
	public float getSliderValue()
	{
		return this.sliderValue;
	}
	
	public void setSliderValue(float sliderValue)
	{
		this.sliderValue = sliderValue;
	}
	
	public float getMaxSliderValue()
	{
		return this.maxSliderValue;
	}
	
	public String getTypeOfSetting()
	{
		return this.typeOfSetting;
	}
}
