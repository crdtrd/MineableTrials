package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.MineableTrials;
import com.drtdrc.mineabletrials.duck.VaultServerDataAccess;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.vault.VaultServerData;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(VaultServerData.class)
public abstract class VaultServerDataMixin implements VaultServerDataAccess {

    @Unique private static final int COOLDOWN_TICKS = 1200; //20 * 60 * 60, 1 hour
    @Unique private int globalCooldownTicks = 0; // gets set to COOLDOWN_TICKS when placed by player
    @Unique private final Map<UUID, Integer> playerCooldownTicks = new HashMap<>();

    @Shadow @Final private Set<UUID> rewardedPlayers;

    private static final String CODEC_GLOBAL = "global_cooldown";
    private static final String CODEC_LIST = "player_cooldowns";
    private static final String CODEC_PLAYER = "player";
    private static final String CODEC_TICKS = "ticks";
    @Shadow @Mutable static Codec<VaultServerData> codec;

    @Invoker("markDirty")
    abstract void mt$markDirty();

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void augmentCodec(CallbackInfo ci) {
        Codec<VaultServerData> base = codec;
        codec = Codec.of(
                new Encoder<VaultServerData>() {
                    @Override
                    public <T> DataResult<T> encode(VaultServerData input, DynamicOps<T> ops, T prefix) {
                        DataResult<T> encoded = base.encode(input, ops, prefix);
                        return encoded.flatMap(mapNode -> {
                            VaultServerDataAccess serverDataMixin = (VaultServerDataAccess) (Object) input;
                            DataResult<T> withGlobal = ops.mergeToMap(mapNode, ops.createString(CODEC_GLOBAL), ops.createInt(serverDataMixin.mineableTrials$getGlobalCooldownTicks()));
                            return withGlobal.flatMap(current -> {
                                List<T> listElems = new ArrayList<>();
                                for (Map.Entry<UUID, Integer> e : serverDataMixin.mineableTrials$getCooldownMap().entrySet()) {
                                    T entry = ops.createMap(Map.of(
                                            ops.createString(CODEC_PLAYER), ops.createString(e.getKey().toString()),
                                            ops.createString(CODEC_TICKS), ops.createInt(e.getValue())
                                    ));
                                    listElems.add(entry);
                                }
                                T listNode = ops.createList(listElems.stream());

                                return ops.mergeToMap(current, ops.createString(CODEC_LIST), listNode);
                            });

                        });
                    }
                },
                new Decoder<VaultServerData>() {
                    @Override
                    public <T> DataResult<Pair<VaultServerData, T>> decode(DynamicOps<T> ops, T input) {
                        DataResult<Pair<VaultServerData, T>> baseRes = base.decode(ops, input);
                        return baseRes.map(pair -> {
                           VaultServerData data = pair.getFirst();
                           Dynamic<T> dyn = new Dynamic<>(ops, input);

                           int global = dyn.get(CODEC_GLOBAL).asInt(0);

                           Map<UUID, Integer> map = new HashMap<>();
                           dyn.get(CODEC_LIST).asList(el -> {
                               String u = el.get(CODEC_PLAYER).asString(null);
                               int ticks = el.get(CODEC_TICKS).asInt(0);
                               if (u != null) {
                                   try { map.put(UUID.fromString(u), ticks); } catch (IllegalArgumentException ignored) {}
                               }
                               return el;
                           });
                           VaultServerDataAccess serverDataMixin = (VaultServerDataAccess) (Object) data;
                           serverDataMixin.mineableTrials$setGlobalCooldownTicks(global);
                           serverDataMixin.mineableTrials$setCooldownMap(map);

                           return Pair.of(data, pair.getSecond());

                        });
                    }
                }
        );
    }


    @Inject(method = "markPlayerAsRewarded", at = @At(value = "HEAD"))
    void onMarkPlayerAsRewarded (PlayerEntity player, CallbackInfo ci) {
        // store uuid and associated cooldown
        playerCooldownTicks.put(player.getUuid(), COOLDOWN_TICKS);
    }

    @Override
    public int mineableTrials$getPlayerCooldownTicks(PlayerEntity player) {
        return (player == null) ? 0 : playerCooldownTicks.getOrDefault(player.getUuid(), 0);
    }
    @Override
    public void mineableTrials$setPlayerCooldownTicks(PlayerEntity player, int ticks) {
        playerCooldownTicks.put(player.getUuid(), ticks);
        mt$markDirty();
    }
    @Override
    public int mineableTrials$getGlobalCooldownTicks() {
        return globalCooldownTicks;
    }
    @Override
    public void mineableTrials$setGlobalCooldownTicks(int ticks) {
        globalCooldownTicks = ticks;
        MineableTrials.LOG.info(String.valueOf(ticks));
        mt$markDirty();
    }
    @Override
    public void mineableTrials$removePlayerFromRewardedPlayers(PlayerEntity player) {
        rewardedPlayers.remove(player.getUuid());
        mt$markDirty();
    }
    @Override
    public void mineableTrials$addPlayerToRewardedPlayers(PlayerEntity player) {
        rewardedPlayers.add(player.getUuid());
        mt$markDirty();
    }
    @Override
    public Map<UUID, Integer> mineableTrials$getCooldownMap() {
        return playerCooldownTicks;
    }
    @Override
    public void mineableTrials$setCooldownMap(Map<UUID, Integer> map) {
        playerCooldownTicks.clear();
        if (map != null) playerCooldownTicks.putAll(map);
        mt$markDirty();
    }
}
