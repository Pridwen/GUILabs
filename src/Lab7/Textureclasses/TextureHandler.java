package Lab7.Textureclasses;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import java.io.IOException;

public class TextureHandler {

	private final int NO_TEXTURES = 1;
	private int texture[] = new int[NO_TEXTURES];
	TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];
	
	private GL gl;
	private GLU GLU;
	
	

	public TextureHandler(GL gl, GLU GLU, String path, boolean mipmapped) {
		this.gl = gl;
		this.GLU = GLU;

		// Generate a name (id) for the texture.
		this.gl.glGenTextures(1, texture, 0);
		// Bind (select) the texture.
		this.gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

		// Read the texture from the image.
		try {
			tex[0] = TextureReader.readTexture(path);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		// Define the filters used when the texture is scaled.
		this.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_LINEAR);
		this.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_LINEAR);
		
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);

		// Construct the texture and use mipmapping in the process.
		this.makeRGBTexture(this.gl, this.GLU, tex[0], GL.GL_TEXTURE_2D, mipmapped);

	}

	public void bind() {
		this.gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);
	}

	public void enable() {
		this.gl.glEnable(GL.GL_TEXTURE_2D);
	}

	public void disable() {
		this.gl.glDisable(GL.GL_TEXTURE_2D);
	}
	
	private void makeRGBTexture(GL gl, GLU GLU, TextureReader.Texture img,
			int target, boolean mipmapped) {
		if (mipmapped) {
			GLU.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img
					.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img
					.getPixels());
		} else {
			gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img
					.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img
					.getPixels());
		}
	}

	public TextureReader.Texture getTex() {
		return tex[0];
	}
}
