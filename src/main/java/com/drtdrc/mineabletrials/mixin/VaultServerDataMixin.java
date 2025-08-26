package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.MineableTrials;
import com.drtdrc.mineabletrials.duck.VaultServerDataAccess;
import net.minecraft.block.vault.VaultServerData;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mixin(VaultServerData.class)
public abstract class VaultServerDataMixin implements VaultServerDataAccess {

    @Unique private static final int ONE_HOUR_TICKS = 200;//20 * 60 * 60;
    @Unique private int globalCooldownTicks = 300;
    @Unique private final Map<UUID, Integer> playerCooldownTicks = new HashMap<>();

    @Shadow @Final private Set<UUID> rewardedPlayers;

    @Inject(method = "markPlayerAsRewarded", at = @At(value = "HEAD"))
    void onMarkPlayerAsRewarded (PlayerEntity player, CallbackInfo ci) {
        // store uuid and associated cooldown
        playerCooldownTicks.put(player.getUuid(), ONE_HOUR_TICKS);

        // will remove player from rewardedPlayers when cooldown is done
    }

    @Inject(method = "hasRewardedPlayer", at = @At(value = "RETURN"), cancellable = true)
    void onHasRewardedPlayer(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        // adds condition for global placement cooldown
        cir.setReturnValue(cir.getReturnValue() && globalCooldownTicks > 0);
    }


    @Override
    public int mineableTrials$getPlayerCooldownTicks(PlayerEntity player) {
        return (player == null) ? 0 : playerCooldownTicks.getOrDefault(player.getUuid(), 0);
    }
    @Override
    public void mineableTrials$setPlayerCooldownTicks(PlayerEntity player, int ticks) {
        playerCooldownTicks.put(player.getUuid(), ticks);
//        MineableTrials.LOG.info(player.toString());
//        MineableTrials.LOG.info(String.valueOf(ticks));
    }
    @Override
    public int mineableTrials$getGlobalCooldownTicks() {
        return globalCooldownTicks;
    }
    @Override
    public void mineableTrials$setGlobalCooldownTicks(int ticks) {
        globalCooldownTicks = ticks;
        MineableTrials.LOG.info(String.valueOf(ticks));
    }
    @Override
    public void mineableTrials$removePlayerFromRewardedPlayers(PlayerEntity player) {
        rewardedPlayers.remove(player.getUuid());
    }
}
