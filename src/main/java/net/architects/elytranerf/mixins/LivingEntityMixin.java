package net.architects.elytranerf.mixins;

import com.google.common.collect.Lists;
import net.architects.elytranerf.Config;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.TicketType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.dimension.end.DragonRespawnAnimation;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.IForgeDimensionSpecialEffects;
import net.minecraftforge.common.ForgeMod;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(value = LivingEntity.class, priority = 69430)
public class LivingEntityMixin implements IForgeDimensionSpecialEffects {


    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 6))
    public void redirectTravel(LivingEntity instance, Vec3 vec3) {
//        double mult = Math.pow(((((50/100)*26.639)+4.131)-3.9),(1/64.16063664))*.95;
//        instance.setDeltaMovement(vec3.multiply(Config.Multiplier, ((Config.Multiplier)*0.9898989996293674), Config.Multiplier));
        instance.setDeltaMovement(vec3.multiply(Config.Multiplier, ((Config.Multiplier)*0.9898989996293674), Config.Multiplier));


    }

//    /**
//     * @author Apothicon
//     * @reason Removes hardcoded ambient lighting from the end.
//     */
//    @Overwrite
//    public void travel(Vec3 p_21280_) {
//
//        if (livingEntity.isControlledByLocalInstance()) {
//            double d0 = 0.08;
//            AttributeInstance gravity = livingEntity.getAttribute((Attribute) ForgeMod.ENTITY_GRAVITY.get());
//            boolean flag = livingEntity.getDeltaMovement().y <= 0.0;
//            if (flag && livingEntity.hasEffect(MobEffects.SLOW_FALLING)) {
//                if (!gravity.hasModifier(SLOW_FALLING)) {
//                    gravity.addTransientModifier(SLOW_FALLING);
//                }
//            } else if (gravity.hasModifier(SLOW_FALLING)) {
//                gravity.removeModifier(SLOW_FALLING);
//            }
//
//            d0 = gravity.getValue();
//            FluidState fluidstate = livingEntity.level().getFluidState(livingEntity.blockPosition());
//            float f4;
//            double d9;
//            if ((livingEntity.isInWater() || livingEntity.isInFluidType(fluidstate) && fluidstate.getFluidType() != ForgeMod.LAVA_TYPE.get()) && livingEntity.isAffectedByFluids() && !livingEntity.canStandOnFluid(fluidstate)) {
//                if (livingEntity.isInWater() || livingEntity.isInFluidType(fluidstate) && !livingEntity.moveInFluid(fluidstate, p_21280_, d0)) {
//                    d9 = livingEntity.getY();
//                    f4 = livingEntity.isSprinting() ? 0.9F : livingEntity.getWaterSlowDown();
//                    float f5 = 0.02F;
//                    float f6 = (float) EnchantmentHelper.getDepthStrider(livingEntity);
//                    if (f6 > 3.0F) {
//                        f6 = 3.0F;
//                    }
//
//                    if (!livingEntity.onGround()) {
//                        f6 *= 0.5F;
//                    }
//
//                    if (f6 > 0.0F) {
//                        f4 += (0.54600006F - f4) * f6 / 3.0F;
//                        f5 += (livingEntity.getSpeed() - f5) * f6 / 3.0F;
//                    }
//
//                    if (livingEntity.hasEffect(MobEffects.DOLPHINS_GRACE)) {
//                        f4 = 0.96F;
//                    }
//
//                    f5 *= (float)livingEntity.getAttribute((Attribute)ForgeMod.SWIM_SPEED.get()).getValue();
//                    livingEntity.moveRelative(f5, p_21280_);
//                    livingEntity.move(MoverType.SELF, livingEntity.getDeltaMovement());
//                    Vec3 vec36 = livingEntity.getDeltaMovement();
//                    if (livingEntity.horizontalCollision && livingEntity.onClimbable()) {
//                        vec36 = new Vec3(vec36.x, 0.2, vec36.z);
//                    }
//
//                    livingEntity.setDeltaMovement(vec36.multiply((double)f4, 0.800000011920929, (double)f4));
//                    Vec3 vec32 = livingEntity.getFluidFallingAdjustedMovement(d0, flag, livingEntity.getDeltaMovement());
//                    livingEntity.setDeltaMovement(vec32);
//                    if (livingEntity.horizontalCollision && livingEntity.isFree(vec32.x, vec32.y + 0.6000000238418579 - livingEntity.getY() + d9, vec32.z)) {
//                        livingEntity.setDeltaMovement(vec32.x, 0.30000001192092896, vec32.z);
//                    }
//                }
//            } else if (livingEntity.isInLava() && livingEntity.isAffectedByFluids() && !livingEntity.canStandOnFluid(fluidstate)) {
//                d9 = livingEntity.getY();
//                livingEntity.moveRelative(0.02F, p_21280_);
//                livingEntity.move(MoverType.SELF, livingEntity.getDeltaMovement());
//                Vec3 vec34;
//                if (livingEntity.getFluidHeight(FluidTags.LAVA) <= livingEntity.getFluidJumpThreshold()) {
//                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(0.5, 0.800000011920929, 0.5));
//                    vec34 = livingEntity.getFluidFallingAdjustedMovement(d0, flag, livingEntity.getDeltaMovement());
//                    livingEntity.setDeltaMovement(vec34);
//                } else {
//                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().scale(0.5));
//                }
//
//                if (!livingEntity.isNoGravity()) {
//                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0.0, -d0 / 4.0, 0.0));
//                }
//
//                vec34 = livingEntity.getDeltaMovement();
//                if (livingEntity.horizontalCollision && livingEntity.isFree(vec34.x, vec34.y + 0.6000000238418579 - livingEntity.getY() + d9, vec34.z)) {
//                    livingEntity.setDeltaMovement(vec34.x, 0.30000001192092896, vec34.z);
//                }
//            } else if (livingEntity.isFallFlying()) {
//                livingEntity.checkSlowFallDistance();
//                Vec3 vec3 = livingEntity.getDeltaMovement();
//                Vec3 vec31 = livingEntity.getLookAngle();
//                f4 = livingEntity.getXRot() * 0.017453292F;
//                double d1 = Math.sqrt(vec31.x * vec31.x + vec31.z * vec31.z);
//                double d3 = vec3.horizontalDistance();
//                double d4 = vec31.length();
//                double d5 = Math.cos((double)f4);
//                d5 = d5 * d5 * Math.min(1.0, d4 / 0.4);
//                vec3 = livingEntity.getDeltaMovement().add(0.0, d0 * (-1.0 + d5 * 0.75), 0.0);
//                double d11;
//                if (vec3.y < 0.0 && d1 > 0.0) {
//                    d11 = vec3.y * -0.1 * d5;
//                    vec3 = vec3.add(vec31.x * d11 / d1, d11, vec31.z * d11 / d1);
//                }
//
//                if (f4 < 0.0F && d1 > 0.0) {
//                    d11 = d3 * (double)(-Mth.sin(f4)) * 0.04;
//                    vec3 = vec3.add(-vec31.x * d11 / d1, d11 * 3.2, -vec31.z * d11 / d1);
//                }
//
//                if (d1 > 0.0) {
//                    vec3 = vec3.add((vec31.x / d1 * d3 - vec3.x) * 0.1, 0.0, (vec31.z / d1 * d3 - vec3.z) * 0.1);
//                }
//
//                livingEntity.setDeltaMovement(vec3.multiply(0.9900000095367432, 0.9800000190734863, 0.9900000095367432));
//                livingEntity.move(MoverType.SELF, livingEntity.getDeltaMovement());
//                if (livingEntity.horizontalCollision && !livingEntity.level().isClientSide) {
//                    d11 = livingEntity.getDeltaMovement().horizontalDistance();
//                    double d7 = d3 - d11;
//                    float f1 = (float)(d7 * 10.0 - 3.0);
//                    if (f1 > 0.0F) {
//                        livingEntity.playSound(livingEntity.getFallDamageSound((int)f1), 1.0F, 1.0F);
//                        livingEntity.hurt(livingEntity.damageSources().flyIntoWall(), f1);
//                    }
//                }
//
//                if (livingEntity.onGround() && !livingEntity.level().isClientSide) {
//                    livingEntity.setSharedFlag(7, false);
//                }
//            } else {
//                BlockPos blockpos = livingEntity.getBlockPosBelowThatAffectsMyMovement();
//                float f2 = livingEntity.level().getBlockState(livingEntity.getBlockPosBelowThatAffectsMyMovement()).getFriction(livingEntity.level(), livingEntity.getBlockPosBelowThatAffectsMyMovement(), livingEntity);
//                f4 = livingEntity.onGround() ? f2 * 0.91F : 0.91F;
//                Vec3 vec35 = livingEntity.handleRelativeFrictionAndCalculateMovement(p_21280_, f2);
//                double d2 = vec35.y;
//                if (livingEntity.hasEffect(MobEffects.LEVITATION)) {
//                    d2 += (0.05 * (double)(livingEntity.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec35.y) * 0.2;
//                } else if (livingEntity.level().isClientSide && !livingEntity.level().hasChunkAt(blockpos)) {
//                    if (livingEntity.getY() > (double)livingEntity.level().getMinBuildHeight()) {
//                        d2 = -0.1;
//                    } else {
//                        d2 = 0.0;
//                    }
//                } else if (!livingEntity.isNoGravity()) {
//                    d2 -= d0;
//                }
//
//                if (livingEntity.shouldDiscardFriction()) {
//                    livingEntity.setDeltaMovement(vec35.x, d2, vec35.z);
//                } else {
//                    livingEntity.setDeltaMovement(vec35.x * (double)f4, d2 * 0.9800000190734863, vec35.z * (double)f4);
//                }
//            }
//        }
//
//        livingEntity.calculateEntityAnimation(livingEntity instanceof FlyingAnimal);
//    }



}
