package com.drtdrc.mineabletrials.duck;

import net.minecraft.entity.player.PlayerEntity;

public interface VaultServerDataAccess {
    int mineableTrials$getPlayerCooldownTicks(PlayerEntity player);
    void mineableTrials$setPlayerCooldownTicks(PlayerEntity player, int ticks);
    int mineableTrials$getGlobalCooldownTicks();
    void mineableTrials$setGlobalCooldownTicks(int ticks);
    void mineableTrials$removePlayerFromRewardedPlayers(PlayerEntity player);
}
