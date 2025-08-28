package com.drtdrc.mineabletrials.duck;

import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;
import java.util.UUID;

public interface VaultServerDataAccessor {
    int mineableTrials$getPlayerCooldownTicks(PlayerEntity player);
    void mineableTrials$setPlayerCooldownTicks(PlayerEntity player, int ticks);
    int mineableTrials$getGlobalCooldownTicks();
    void mineableTrials$setGlobalCooldownTicks(int ticks);
    void mineableTrials$removePlayerFromRewardedPlayers(PlayerEntity player);
    void mineableTrials$removePlayerFromRewardedPlayers(UUID uuid);
    void mineableTrials$addPlayerToRewardedPlayers(PlayerEntity player);
    boolean mineableTrials$HasRewardedPlayer(PlayerEntity player);
    Map<UUID, Integer> mineableTrials$getCooldownMap();
    void mineableTrials$setCooldownMap(Map<UUID, Integer> map);
    int mineableTrials$getCooldownTickConstant();

}
