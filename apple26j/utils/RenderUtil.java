package apple26j.utils;

import static org.lwjgl.opengl.GL11.*;

import apple26j.interfaces.MinecraftInterface;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderUtil implements MinecraftInterface
{
	private static ResourceLocation storedResourceLocation;
	
	public static void drawRect(float x, float y, float width, float height, int color)
	{
		if (x < width)
        {
            float f = x;
            x = width;
            width = f;
        }
		
        if (y < height)
        {
            float g = y;
            y = height;
            height = g;
        }
        
        float red = (float) (color >> 16 & 255) / 255;
        float green = (float) (color >> 8 & 255) / 255;
        float blue = (float) (color & 255) / 255;
        float alpha = (float) (color >> 24 & 255) / 255;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        worldrenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, height, 0).endVertex();
        worldrenderer.pos(width, height, 0).endVertex();
        worldrenderer.pos(width, y, 0).endVertex();
        worldrenderer.pos(x, y, 0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
	}
	
	public static void drawOutline(float x, float y, float width, float height, int color)
	{
		if (x < width)
        {
            float f = x;
            x = width;
            width = f;
        }
		
        if (y < height)
        {
            float g = y;
            y = height;
            height = g;
        }
        
        float red = (float) (color >> 16 & 255) / 255;
        float green = (float) (color >> 8 & 255) / 255;
        float blue = (float) (color & 255) / 255;
        float alpha = (float) (color >> 24 & 255) / 255;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        float previousLineWidth = glGetFloat(GL_LINE_WIDTH);
        glLineWidth(2);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        worldrenderer.begin(2, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, height, 0).endVertex();
        worldrenderer.pos(width, height, 0).endVertex();
        worldrenderer.pos(width, y, 0).endVertex();
        worldrenderer.pos(x, y, 0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        glLineWidth(previousLineWidth);
	}
	
	public static void drawImage(ResourceLocation image, float x, float y, float width, float height)
	{
		if (storedResourceLocation == null || !storedResourceLocation.equals(image))
		{
			storedResourceLocation = image;
		}
		
		mc.getTextureManager().bindTexture(storedResourceLocation);
		float f = 1 / width;
        float f1 = 1 / height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        worldrenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0).tex(0, height * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0).tex(width * f, height * f1).endVertex();
        worldrenderer.pos(x + width, y, 0).tex(width * f, 0).endVertex();
        worldrenderer.pos(x, y, 0).tex(0, 0).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
	}
	
	public static void drawImage(ResourceLocation image, float x, float y, float imageX, float imageY, float renderWidth, float renderHeight, float width, float height, float imageWidth, float imageHeight)
	{
		if (storedResourceLocation == null || !storedResourceLocation.equals(image))
		{
			storedResourceLocation = image;
		}
		
		mc.getTextureManager().bindTexture(storedResourceLocation);
		float f = 1 / width;
        float f1 = 1 / height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        worldrenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + imageHeight, 0).tex(imageX * f, (imageY + renderHeight) * f1).endVertex();
        worldrenderer.pos(x + imageWidth, y + imageHeight, 0).tex((imageX + renderWidth) * f, (imageY + renderHeight) * f1).endVertex();
        worldrenderer.pos(x + imageWidth, y, 0).tex((imageX + renderWidth) * f, imageY * f).endVertex();
        worldrenderer.pos(x, y, 0).tex(imageX * f, imageY * f1).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
	}
}
