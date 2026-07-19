package org.zawarka.svcMuteFabric.data

import net.kyori.adventure.text.Component
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcMuteFabric.SvcMuteFabric
import org.zawarka.svcMuteFabric.utils.hasPermission
import org.zawarka.svcMuteFabric.utils.mm

fun ServerCommandSource.sendMessage(value: MessageValue) {
    FabricMessageService.instance.sendMessage(this, value)
}

fun ServerPlayerEntity.sendMessage(value: MessageValue) {
    FabricMessageService.instance.sendMessage(this, value)
}

fun ServerPlayerEntity.sendActionbar(value: MessageValue) {
    FabricMessageService.instance.sendActionbar(this, value)
}

class FabricMessageService : MessageService() {
    companion object {
        lateinit var instance: MessageService
    }

    init {
        instance = this
    }

    override fun sendMessage(player: Any, values: MessageValue) {
        if(player !is ServerCommandSource && player !is ServerPlayerEntity) return

        player.sendMessage(getFormatedMessage(values) ?: return)
    }

    override fun sendActionbar(player: Any, values: MessageValue) {
        if(player !is ServerCommandSource && player !is ServerPlayerEntity) return

        player.sendActionBar(getFormatedMessage(values) ?: return)
    }

    override fun sendGlobalMessage(value: MessageValue, permission: MutePermission) {
        for (player in SvcMuteFabric.instance.server.playerManager.playerList) {
            if(!player.hasPermission(permission)) continue
            sendMessage(player, value)
        }
    }

    fun getFormatedMessage(values: MessageValue): Component?{
        val message = messagesData.getMessageString(values.type)

        if(message.isEmpty()) return null

        return putValues(message, values).mm()
    }
}