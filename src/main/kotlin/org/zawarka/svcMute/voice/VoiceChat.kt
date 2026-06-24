package org.zawarka.svcMute.voice

import de.maxhenkel.voicechat.api.VoicechatApi
import de.maxhenkel.voicechat.api.VoicechatPlugin
import de.maxhenkel.voicechat.api.events.EventRegistration
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent

class VoiceChat : VoicechatPlugin {

    companion object{
        const val PLUGIN_ID: String = "svc-mute"

        lateinit var api: VoicechatApi
    }

    /**
     * @return the unique ID for this voice chat plugin
     */
    override fun getPluginId(): String {
        return PLUGIN_ID
    }

    /**
     * Called when the voice chat initializes the plugin.
     *
     * @param api the voice chat API
     */
    override fun initialize(api: VoicechatApi) {
        VoiceChat.api = api
    }

    /**
     * Called once by the voice chat to register all events.
     *
     * @param registration the event registration
     */
    override fun registerEvents(registration: EventRegistration) {
        registration.registerEvent(VoicechatServerStartedEvent::class.java, this::onServerStarted, 100)
    }

    private fun onServerStarted(event: VoicechatServerStartedEvent) {
        val api = event.voicechat
    }
}