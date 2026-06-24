package org.zawarka.svcMute.voicemessages

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import ru.dimaskama.voicemessages.api.VoiceMessagesApiInitCallback

object VoiceMessagesHook {

    fun init(plugin: Plugin){
        val voice = Bukkit.getPluginManager().getPlugin("voicemessages")

        if(voice?.isEnabled == true){
            VoiceMessagesApiInitCallback.EVENT.invoker().setVoiceMessagesApi(VoiceMessagesApiImpl())
            plugin.logger.info { "Plugin VoiceMessages hooked" }
        }
    }


}