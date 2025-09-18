package io.github.xrbbm.xattack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
@Mod(Xattack.ID)
public class Xattack {
	public static final String ID = "xattack";
	public Xattack(ModContainer container, Dist dist) {
		if (dist != Dist.CLIENT) return;
		var mod = container.getEventBus();
		if (mod == null) return;
		mod.addListener(XattackKey::register);
		var game = NeoForge.EVENT_BUS;
		game.addListener(KeyInput::tick);
		game.addListener(MobAttack::tick);
	}
}
