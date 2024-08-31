package com.gpixelprog

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

class blockspawner : ModInitializer {

    private val logger = LoggerFactory.getLogger("server-mod")

    override fun onInitialize() {
        logger.info("Start server mode!")
        GenNetworking.registerServerReceivers()
    }
}
