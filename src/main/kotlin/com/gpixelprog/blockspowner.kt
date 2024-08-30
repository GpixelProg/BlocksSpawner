package com.gpixelprog

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

class blockspowner : ModInitializer {

    val logger = LoggerFactory.getLogger("first-mod")

    override fun onInitialize() {
        logger.info("Hello Fabric world!")
    }
}
