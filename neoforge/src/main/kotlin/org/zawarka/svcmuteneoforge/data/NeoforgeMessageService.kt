package org.zawarka.svcmuteneoforge.data

import net.kyori.adventure.text.Component
import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.level.ServerPlayer
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcmuteneoforge.utils.hasPermission
import org.zawarka.svcmuteneoforge.SvcMuteNeoforge
import org.zawarka.svcmuteneoforge.utils.mm
import org.zawarka.svcmuteneoforge.utils.string
import org.zawarka.svcmuteneoforge.utils.toVanilla

class NeoforgeMessageService : MessageService() {
    companion object {
        lateinit var instance: MessageService
    }

    init {
        instance = this
    }

    override fun sendMessage(player: Any, values: MessageValue) {
        if(player is ServerPlayer) {
            sendMessage(player, values)
        }
        if(player is CommandSourceStack) {
            if(player.isPlayer){
                sendMessage(player.player ?: return, values)
            }else{
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal(getFormatedMessage(values)?.string() ?: return))
            }
        }
    }

    private fun sendMessage(player: ServerPlayer, values: MessageValue) {
        val msg = getFormatedMessage(values) ?: return
        player.displayClientMessage(msg.toVanilla(), false)
    }

    override fun sendActionbar(player: Any, values: MessageValue) {
        if(player is ServerPlayer) {
            sendActionbar(player, values)
        }
        if(player is CommandSourceStack) {
            if(player.isPlayer){
                sendActionbar(player.player ?: return, values)
            }
        }
    }

    private fun sendActionbar(player: ServerPlayer, values: MessageValue) {
        val msg = getFormatedMessage(values) ?: return
        player.displayClientMessage(msg.toVanilla(), true)
        //SvcMuteNeoforge.adventure().player(player.uuid).sendActionBar(getFormatedMessage(values) ?: return)
    }

    override fun sendGlobalMessage(
        value: MessageValue,
        permission: MutePermission
    ) {
        for (player in SvcMuteNeoforge.server?.playerList?.players ?: return) {
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