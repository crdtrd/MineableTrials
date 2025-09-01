package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.duck.VaultServerDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VaultBlock;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
}
