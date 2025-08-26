package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.MineableTrials;
import com.drtdrc.mineabletrials.duck.VaultServerDataAccess;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.block.vault.VaultConfig;
import net.minecraft.block.vault.VaultServerData;
import net.minecraft.block.vault.VaultSharedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(VaultBlockEntity.Server.class)
public abstract class VaultBlockEntityServerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private static void onTick(ServerWorld world, BlockPos pos, BlockState state, VaultConfig config, VaultServerData serverData, VaultSharedData sharedData, CallbackInfo ci) {

        VaultServerDataAccess serverDataMixin = (VaultServerDataAccess) (Object) serverData;
        int gcTicks = serverDataMixin.mineableTrials$getGlobalCooldownTicks();
        List<ServerPlayerEntity> players = world.getPlayers();

        // check each players tick. If 0, remove from rewardedPlayers, else deduct a tick
        players.forEach(p -> {
            int pcTicks = serverDataMixin.mineableTrials$getPlayerCooldownTicks(p);
            if ( pcTicks == 0) {
                serverDataMixin.mineableTrials$removePlayerFromRewardedPlayers(p);
            } else {
                serverDataMixin.mineableTrials$setPlayerCooldownTicks(p, pcTicks - 1);
            }
        });

        if (gcTicks > 0) {
            serverDataMixin.mineableTrials$setGlobalCooldownTicks(gcTicks - 1);
        }

    }

}
