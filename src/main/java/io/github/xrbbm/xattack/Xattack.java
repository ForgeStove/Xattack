package io.github.xrbbm.xattack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.*;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.InputEvent.Key;
@Mod(Xattack.ID)
public class Xattack {
	public static final String ID = "xattack";
	@EventBusSubscriber(modid = ID, value = Dist.CLIENT)
	public static class ClientGameEvents {
		@SubscribeEvent
		public static void key(Key event) {
			KeyInput.tick();
		}
		@SubscribeEvent
		public static void clientTick(Post event) {
			MobAttack.tick();
		}
	}
	@EventBusSubscriber(modid = ID, value = Dist.CLIENT, bus = Bus.MOD)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
			XattackKeyMapping.register(event);
		}
	}
}
