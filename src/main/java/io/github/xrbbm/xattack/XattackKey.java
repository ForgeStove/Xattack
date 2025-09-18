package io.github.xrbbm.xattack;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.*;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.jetbrains.annotations.*;
import org.lwjgl.glfw.GLFW;
public enum XattackKey {
	attack(GLFW.GLFW_KEY_I);
	private final KeyMapping keyMapping;
	@Contract(pure = true)
	XattackKey(int key) {
		keyMapping = new KeyMapping(Xattack.ID + ".key." + name(), key, "key.categories." + Xattack.ID);
	}
	public static void register(RegisterKeyMappingsEvent event) {
		for (var key : values()) event.register(key.get());
	}
	public @NotNull KeyMapping get() {
		return keyMapping;
	}
	public boolean isKeyDown() {
		return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), keyMapping.getKey().getValue());
	}
}
