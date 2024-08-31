package com.gpixelprog

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.block.Blocks
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos


data class BlockHighlightPayload(val blockPos: BlockPos) : CustomPayload {
    companion object {
        val ID = CustomPayload.Id<BlockHighlightPayload>(Identifier.of("modid", "highlight_packet"))
        val CODEC: PacketCodec<PacketByteBuf, BlockHighlightPayload> = PacketCodec.tuple(
            BlockPos.PACKET_CODEC,
            { it.blockPos },
            ::BlockHighlightPayload
        )
    }

    override fun getId(): CustomPayload.Id<out CustomPayload> = ID
}

object GenNetworking {

    fun registerServerReceivers() {
        PayloadTypeRegistry.playC2S().register(BlockHighlightPayload.ID, BlockHighlightPayload.CODEC)

        ServerPlayNetworking.registerGlobalReceiver(
            BlockHighlightPayload.ID
        ) { payload: BlockHighlightPayload, context: ServerPlayNetworking.Context ->

            context.server().execute {
                generateBlocks(context.player(), payload.blockPos)
            }
        }
    }

    private fun generateBlocks(player: ServerPlayerEntity, pos: BlockPos) {
        val world = player.world as ServerWorld
        val startX = pos.x + player.horizontalFacing.offsetX
        val startY = pos.y
        val startZ = pos.z + player.horizontalFacing.offsetZ

        for (x in 0..2) {
            for (z in 0..2) {
                val blockPos = BlockPos(startX + x, startY, startZ + z)
                world.setBlockState(blockPos, Blocks.GOLD_BLOCK.defaultState, 3)
            }
        }
    }
}