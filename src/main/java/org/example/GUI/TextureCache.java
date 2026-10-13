package org.example.GUI;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

/**
 * Uploads {@link BufferedImage}s as OpenGL textures so they can be drawn by ImGui
 * ({@code ImGui.image}/{@code imageButton} take a GL texture name as a {@code long}).
 * Textures are cached by an arbitrary key (spell class for icons, a fixed string for the
 * one-off dynamic images) so repeated lookups don't re-upload the same pixels every frame.
 */
public final class TextureCache{
    public record Texture(long id, int width, int height){}

    private static final Map<Object, Texture> cache = new HashMap<>();
    private static final BufferedImage FALLBACK = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    private TextureCache(){}

    /** Returns the cached texture for {@code key}, uploading {@code image} the first time it's seen. */
    public static Texture get(Object key, BufferedImage image){
        Texture texture = cache.get(key);
        if(texture != null){
            return texture;
        }
        texture = upload(image == null ? FALLBACK : image);
        cache.put(key, texture);
        return texture;
    }

    /** Re-uploads {@code image} under {@code key}, replacing whatever was cached before. Use for images that change (flowchart, cast state). */
    public static Texture refresh(Object key, BufferedImage image){
        Texture old = cache.remove(key);
        if(old != null){
            glDeleteTextures((int)old.id());
        }
        Texture texture = upload(image == null ? FALLBACK : image);
        cache.put(key, texture);
        return texture;
    }

    public static void clear(){
        for(Texture texture : cache.values()){
            glDeleteTextures((int)texture.id());
        }
        cache.clear();
    }

    private static Texture upload(BufferedImage image){
        int width = Math.max(image.getWidth(), 1);
        int height = Math.max(image.getHeight(), 1);
        ByteBuffer buffer = ByteBuffer.allocateDirect(width*height*4);

        for(int y=0; y < height; y++){
            for(int x=0; x < width; x++){
                int argb = image.getRGB(x, y);
                buffer.put((byte)((argb >> 16) & 0xFF));
                buffer.put((byte)((argb >> 8) & 0xFF));
                buffer.put((byte)(argb & 0xFF));
                buffer.put((byte)((argb >> 24) & 0xFF));
            }
        }
        buffer.flip();

        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return new Texture(id & 0xFFFFFFFFL, width, height);
    }
}
