package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.duck.VaultServerDataAccess;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.block.vault.VaultConfig;
import net.minecraft.block.vault.VaultServerData;
import net.minecraft.block.vault.VaultSharedData;
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

        if (gcTicks > 0) {
            players.forEach(p -> {
                serverData.markPlayerAsRewarded(p);
                serverDataMixin.mineableTrials$setPlayerCooldownTicks(p, gcTicks);
            });
            serverDataMixin.mineableTrials$setGlobalCooldownTicks(gcTicks - 1);
        }

        if (gcTicks == 0) {
            players.forEach(p -> {
                int pcTicks = serverDataMixin.mineableTrials$getPlayerCooldownTicks(p);
                if (pcTicks == 0) {
                    serverDataMixin.mineableTrials$removePlayerFromRewardedPlayers(p);
                }
                if (pcTicks > 0) {
                    serverDataMixin.mineableTrials$setPlayerCooldownTicks(p, pcTicks - 1);
                }
            });
        }




    }

}
