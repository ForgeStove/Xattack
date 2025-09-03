package io.github.xrbbm.xattack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;

import java.util.*;
public class MobAttack {
	public static Queue<LivingEntity> entityQueue = new ArrayDeque<>();
	public static Minecraft mc = Minecraft.getInstance();
	public static void attackMob(Entity entity) {
		var player = mc.player;
		if (player == null) return;
		if (player.getAttackStrengthScale(0) != 1.0F) return;
		if (mc.gameMode == null) return;
		mc.gameMode.attack(player, entity);
		player.swing(InteractionHand.MAIN_HAND);
	}
	public static boolean canAttackEntity(LivingEntity entity) {
		if (!(entity instanceof Enemy || entity instanceof WaterAnimal)) return false;
		if (!entity.canBeSeenAsEnemy()) return false;
		if (entity.hurtTime > 0 && !mc.mouseHandler.isLeftPressed()) return false;
		var player = mc.player;
		if (player == null) return false;
		if (mc.gameMode == null) return false;
		if (mc.gameMode.getPlayerMode() == GameType.SPECTATOR) return false;
		if (!isVisible(entity)) return false;
		entity.setSharedFlag(6, true);
		return true;
	}
	public static boolean isVisible(LivingEntity entity) {
		return mc.levelRenderer.getFrustum().isVisible(entity.getBoundingBox());
	}
	public static void tick() {
		if (!KeyInput.attackEnabled) return;
		if (mc.level == null) {
			entityQueue.clear();
			return;
		}
		var player = mc.player;
		if (player == null) return;
		var eyePosition = player.getEyePosition();
		mc.level.getEntitiesOfClass(LivingEntity.class, new AABB(eyePosition, eyePosition).inflate(player.entityInteractionRange()))
			.stream()
			.filter(MobAttack::canAttackEntity)
			.forEach(entityQueue::offer);
		var livingEntity = getNextAvailableEntityFromQueue();
		if (livingEntity != null) attackMob(livingEntity);
	}
	public static LivingEntity getNextAvailableEntityFromQueue() {
		return entityQueue.isEmpty() ? null : entityQueue.poll();
	}
}
