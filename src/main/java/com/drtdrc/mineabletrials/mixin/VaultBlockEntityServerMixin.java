package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.duck.VaultServerDataAccessor;
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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Mixin(VaultBlockEntity.Server.class)
public abstract class VaultBlockEntityServerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private static void onTick(ServerWorld world, BlockPos pos, BlockState state, VaultConfig config, VaultServerData serverData, VaultSharedData sharedData, CallbackInfo ci) {

        VaultServerDataAccessor serverDataMixin = (VaultServerDataAccessor) (Object) serverData;
        int gcTicks = serverDataMixin.mineableTrials$getGlobalCooldownTicks();
        Map<UUID, Integer> pcTickMap = serverDataMixin.mineableTrials$getCooldownMap();
        List<ServerPlayerEntity> onlinePlayers = world.getPlayers();

        if (gcTicks > 0) {
            onlinePlayers.forEach( p -> {
                if (!serverDataMixin.mineableTrials$HasRewardedPlayer(p)) {
                    serverData.markPlayerAsRewarded(p);
                }
            });
            pcTickMap.replaceAll((u, t) -> gcTicks);

            serverDataMixin.mineableTrials$setGlobalCooldownTicks(gcTicks - 1);

            return;
        }

        if (!pcTickMap.isEmpty()) {
            Iterator<Map.Entry<UUID, Integer>> it = pcTickMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<UUID, Integer> e = it.next();
                int next = e.getValue() - 1;

                if (next <= 0) {
                    it.remove();
                    serverDataMixin.mineableTrials$removePlayerFromRewardedPlayers(e.getKey());
                } else {
                    e.setValue(next);
                }
            }
        }
    }

}
