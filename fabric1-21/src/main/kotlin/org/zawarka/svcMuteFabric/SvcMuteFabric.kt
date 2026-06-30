package org.zawarka.svcMuteFabric

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer
import org.zawarka.svcMuteCore.SvcMuteCore
import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.voice.VoiceChat
import org.zawarka.svcMuteFabric.command.CommandsRegister
import org.zawarka.svcMuteFabric.data.FabricMessageService
import org.zawarka.svcMuteFabric.data.FabricPlayerService
import org.zawarka.svcMuteFabric.voice.FabricVoiceChat


class SvcMuteFabric : ModInitializer {

    companion object {
        lateinit var instance : SvcMuteFabric private set
    }

    val core = SvcMuteCore()

    lateinit var server: MinecraftServer
        private set

    lateinit var voiceChat: VoiceChat

    override fun onInitialize() {
        instance = this

        val dataFolder = FabricLoader.getInstance()
            .configDir
            .resolve("svc-mute")
            .toFile()

        core.init(dataFolder, FabricMessageService())

        voiceChat = FabricVoiceChat()

        ServerLifecycleEvents.SERVER_STARTING.register { srv ->
            server = srv
        }

        CommandsRegister.register()

        ServerLifecycleEvents.SERVER_STOPPED.register { _ ->
            core.storage.saveAll()
        }
    }
}
