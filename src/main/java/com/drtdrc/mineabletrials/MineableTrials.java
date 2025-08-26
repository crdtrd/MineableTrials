package com.drtdrc.mineabletrials;

import com.drtdrc.mineabletrials.component.MTComponents;
import com.drtdrc.mineabletrials.item.MTItems;
import com.drtdrc.mineabletrials.logic.HarvestHandler;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineableTrials implements ModInitializer {
    public static final String MOD_ID = "mineabletrials";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        MTComponents.init();
        MTItems.init();
        HarvestHandler.register();
        LOG.info("MineableTrials initialized.");
    }
}
