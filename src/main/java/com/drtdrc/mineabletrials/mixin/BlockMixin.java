package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.MineableTrials;
import com.drtdrc.mineabletrials.duck.VaultServerDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VaultBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.block.spawner.EntityDetector;
import net.minecraft.block.vault.VaultConfig;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "onPlaced", at = @At(value = "TAIL"))
    void onPlacedVault(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (world.isClient()) return;
        if (!(placer instanceof PlayerEntity)) return;
        if (!(state.getBlock() instanceof VaultBlock)) return;

        var be = world.getBlockEntity(pos);

        if (be instanceof VaultBlockEntity vbe) {
            var data = (VaultServerDataAccessor) (Object) vbe.getServerData();
            if (data != null) {
                data.mineableTrials$setGlobalCooldownTicks(data.mineableTrials$getCooldownTickConstant());
            }
        }
    }

//    @Inject(
//            method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
//            at = @At(value = "HEAD"),
//            cancellable = true
//    )
//    private static void onGetDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir) {
//        MineableTrials.LOG.info("onGetDroppedStacks");
//        if (!(state.getBlock() instanceof VaultBlock)) return;
//        if (!(blockEntity instanceof VaultBlockEntity vbe)) return;
//        if (!(entity instanceof PlayerEntity)) return;
//        // Build a Vault block item named "Ominous Vault" and copy full BE data
//        ItemStack newDrop = new ItemStack(state.getBlock().asItem());
//        // need to make not italic
//        newDrop.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Ominous Vault"));
//
//        vbe.setConfig(new VaultConfig(LootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_CHEST, 4.0, 4.5, new ItemStack(Items.OMINOUS_TRIAL_KEY), Optional.empty(), EntityDetector.NON_SPECTATOR_PLAYERS, EntityDetector.Selector.IN_WORLD));
//        newDrop.set(DataComponentTypes.BLOCK_ENTITY_DATA, vbe.getComponents().get(DataComponentTypes.BLOCK_ENTITY_DATA));
//        cir.setReturnValue(List.of(newDrop));
//
//    }
}
