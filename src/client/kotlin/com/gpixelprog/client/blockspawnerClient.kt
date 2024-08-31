package com.gpixelprog.client

import com.gpixelprog.BlockHighlightPayload
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.util.math.BlockPos
import org.lwjgl.glfw.GLFW
import org.slf4j.LoggerFactory

class blockspawnerClient : ClientModInitializer {

    private val logger = LoggerFactory.getLogger("click")

    private val myKeyBinding = KeyBinding(
        "key.examplemod.mykey",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_J,
        "category.examplemod"
    )

    override fun onInitializeClient() {
        logger.info("Start client mode!")

        // Register the packet
        KeyBindingHelper.registerKeyBinding(myKeyBinding)

        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client ->
            if (client.player != null && client.world != null) {
                val player = client.player!!

                // Checking whether the F key is pressed
                if (myKeyBinding.isPressed) {
                    logger.info("Click J")
                    val playerPos = player.blockPos

                    // Determine the initial position for the square
                    val startX = playerPos.x + player.horizontalFacing.offsetX
                    val startY = playerPos.y
                    val startZ = playerPos.z + player.horizontalFacing.offsetZ

                    val pos = BlockPos(startX, startY, startZ)
                    ClientPlayNetworking.send(BlockHighlightPayload(pos))
                }
            }
        })
    }
}
