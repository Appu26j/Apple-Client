package apple26j.fontrenderer;

import static apple26j.utils.RenderUtil.drawImage;

import java.awt.Color;
import java.util.Locale;

import apple26j.interfaces.MinecraftInterface;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class FixedFontRenderer implements MinecraftInterface
{
	private static final ResourceLocation font = new ResourceLocation("textures/font/ascii.png");
	public static final int[] colorCodes = new int[]{0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215, 0, 42, 10752, 10794, 2752512, 2752554, 2763264, 2763306, 1381653, 1381695, 1392405, 1392447, 4134165, 4134207, 4144917, 4144959};
	
	public static void drawStringWithShadow(String text, float x, float y, float size, int color)
	{
		drawString(text, x + 1, y + 1, size, new Color(color, true).darker().darker().darker().darker().getRGB());
		drawString(text, x, y, size, color);
	}
	
	public static void drawString(String text, float x, float y, float size, int color)
	{
		float red = (float) (color >> 16 & 255) / 255;
        float green = (float) (color >> 8 & 255) / 255;
        float blue = (float) (color & 255) / 255;
        float alpha = (float) (color >> 24 & 255) / 255;
        GlStateManager.color(red, green, blue, alpha);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.alphaFunc(516, 0.1F);
		float offset = 0;
		
		for (int i = 0; i < text.length(); i++)
		{
			int characterIndex = text.charAt(i);
			
			if (characterIndex == '§' && i + 1 < text.length())
    		{
            	int l = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
            	
            	if (l < 16)
            	{
            		if (l < 0 || l > 15)
                    {
                        l = 15;
                    }
            		
                    int i1 = FixedFontRenderer.colorCodes[l];
                    red = (float) (i1 >> 16) / 255;
                    green = (float) (i1 >> 8 & 255) / 255;
                    blue = (float) (i1 & 255) / 255;
                    GlStateManager.color(red, green, blue, alpha);
            	}
            	
            	++i;
    		}
			
			else
			{
				drawImage(font, x + offset, y, characterIndex % 16 * 8, characterIndex / 16 * 8, 8, 8, 128, 128, size, size);
				offset += (characterIndex == 'l' || characterIndex == ' ' || characterIndex == '\'' ? 3 : characterIndex == 'f' || characterIndex == 'k' || characterIndex == '*' || characterIndex == '(' || characterIndex == ')' || characterIndex == '>' || characterIndex == '<' || characterIndex == '{' || characterIndex == '}' || characterIndex == '\"' ? 5 : characterIndex == 'i' || characterIndex == '!' || characterIndex == '.' || characterIndex == ',' || characterIndex == '|' || characterIndex == ':' || characterIndex == ';' ? 2 : characterIndex == 't' || characterIndex == 'I' || characterIndex == '[' || characterIndex == ']' ? 4 : characterIndex == '@' || characterIndex == '~' ? 7 : 6) * (size / 8);
			}
		}
		
		GlStateManager.disableBlend();
		GlStateManager.disableAlpha();
	}
	
	public static float getStringWidth(String text, float size)
	{
		float offset = 0;
		
		for (int i = 0; i < text.length(); i++)
		{
			int characterIndex = text.charAt(i);
			
			if (characterIndex == '§' && i + 1 < text.length())
    		{
            	++i;
    		}
			
			else
			{
				offset += (characterIndex == 'l' || characterIndex == ' ' || characterIndex == '\'' ? 3 : characterIndex == 'f' || characterIndex == 'k' || characterIndex == '*' || characterIndex == '(' || characterIndex == ')' || characterIndex == '>' || characterIndex == '<' || characterIndex == '{' || characterIndex == '}' ? 5 : characterIndex == 'i' || characterIndex == '!' || characterIndex == '.' || characterIndex == ',' || characterIndex == '|' || characterIndex == ':' || characterIndex == ';' ? 2 : characterIndex == 't' || characterIndex == 'I' || characterIndex == '[' || characterIndex == ']' ? 4 : characterIndex == '@' || characterIndex == '~' ? 7 : 6) * (size / 8);
			}
		}
		
		return offset;
	}
}
