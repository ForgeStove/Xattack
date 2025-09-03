package io.github.xrbbm.xattack;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.*;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.*;
import org.lwjgl.glfw.GLFW;
public enum XattackKeyMapping {
	attack(GLFW.GLFW_KEY_I);
	private final Lazy<KeyMapping> keyMapping;
	@Contract(pure = true)
	XattackKeyMapping(int key) {
		keyMapping = Lazy.of(() -> new KeyMapping(
			"key." + Xattack.ID + "." + name(),
			KeyConflictContext.UNIVERSAL,
			Type.KEYSYM,
			key,
			"key.categories." + Xattack.ID
		));
	}
	public static void register(RegisterKeyMappingsEvent event) {
		for (var key : values()) event.register(key.get());
	}
	public @NotNull KeyMapping get() {
		return keyMapping.get();
	}
	public boolean isDown() {
		var value = get().getKey().getValue();
		if (value == GLFW.GLFW_KEY_UNKNOWN) return false;
		return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), value) == GLFW.GLFW_PRESS;
	}
}
