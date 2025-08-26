package com.drtdrc.mineabletrials.mixin;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin {

//    @Unique
//    private static final int MT_HOUR_TICKS = 20 * 60 * 60;     // 1 hour
//    @Unique
//    private static final int MT_HALF_HOUR_TICKS = 20 * 60 * 30; // 30 minutes
//
//    @Inject(
//            method = "onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
//            at = @At("TAIL")
//    )
//    private void mineabletrials$applyOnPlace(World world, BlockPos pos, BlockState state,
//                                             LivingEntity placer, ItemStack stack, CallbackInfo ci) {
//        if (world.isClient()) return;
//
//        // V A U L T  — arm 1h cooldown *on placement*
//        if (state.isOf(Blocks.VAULT)) {
//            var be = world.getBlockEntity(pos);
//            if (be instanceof com.drtdrc.mineabletrials.duck.VaultServerDataAccess acc) {
//                acc.setCooldownTicks(MT_HOUR_TICKS);
//                be.markDirty();
//            }
//            return;
//        }
//
//        // T R I A L   S P A W N E R  — allow ominous & arm 30m placement cooldown
//        if (state.isOf(Blocks.TRIAL_SPAWNER)) {
//            var be = world.getBlockEntity(pos);
//
//            // 1) Let manually placed spawners go ominous
//            if (be instanceof com.drtdrc.mineabletrials.duck.TrialSpawnerOminousAccess omin) {
//                omin.mt$setAllowOminous(true);
//            }
//
//            // 2) Start the 30-minute placement cooldown on the LOGIC
//            if (be instanceof net.minecraft.block.entity.TrialSpawnerBlockEntity spawnerBe) {
//                var logic = ((com.drtdrc.mineabletrials.mixin.TrialSpawnerBlockEntityAccessor) spawnerBe)
//                        .mineabletrials$getLogic();
//
//                // Duck-cast via Object so it compiles before mixin adds the interface at runtime
//                @SuppressWarnings("ConstantConditions")
//                com.drtdrc.mineabletrials.duck.TrialSpawnerPlacementCooldownAccess acc =
//                        (com.drtdrc.mineabletrials.duck.TrialSpawnerPlacementCooldownAccess) (Object) logic;
//
//                acc.mt$setPlacementCooldown(30 * 60 * 20); // 30 minutes in ticks
//            }
//        }
//
//    }
}

