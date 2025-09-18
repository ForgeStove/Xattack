package io.github.xrbbm.xattack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.*;
import net.neoforged.neoforge.client.event.InputEvent.Key;
public class KeyInput {
	public static boolean attackEnabled = false;
	public static void tick(Key ignoredEvent) {
		attack();
	}
	public static void attack() {
		var mc = Minecraft.getInstance();
		var player = mc.player;
		if (!XattackKey.attack.isKeyDown() || mc.screen != null || player == null) return;
		var name = Component.translatable("xattack.name").append(" ");
		player.displayClientMessage(
			name.append(attackEnabled
				? Component.translatable("xattack.off").withStyle(ChatFormatting.RED)
				: Component.translatable("xattack.on").withStyle(ChatFormatting.GREEN)), true
		);
		if (!attackEnabled) player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1));
		else {
			var effect = player.getEffect(MobEffects.NIGHT_VISION);
			if (effect != null && effect.isInfiniteDuration()) player.removeEffect(MobEffects.NIGHT_VISION);
		}
		attackEnabled = !attackEnabled;
	}
}
