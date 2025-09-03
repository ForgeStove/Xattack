package io.github.xrbbm.xattack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.*;

import java.awt.Color;
public class KeyInput {
	public static boolean attackEnabled = false;
	public static void tick() {
		attack();
	}
	public static void attack() {
		var mc = Minecraft.getInstance();
		var player = mc.player;
		if (!XattackKeyMapping.attack.isDown() || mc.screen != null || player == null) return;
		var name = Component.translatable("xattack.name").append(" ");
		player.displayClientMessage(
			name.append(attackEnabled
				? Component.translatable("xattack.off").withColor(Color.RED.getRGB())
				: Component.translatable("xattack.on").withColor(Color.GREEN.getRGB())), true
		);
		if (!attackEnabled) player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1));
		else {
			var effect = player.getEffect(MobEffects.NIGHT_VISION);
			if (effect != null && effect.isInfiniteDuration()) player.removeEffect(MobEffects.NIGHT_VISION);
		}
		attackEnabled = !attackEnabled;
	}
}
