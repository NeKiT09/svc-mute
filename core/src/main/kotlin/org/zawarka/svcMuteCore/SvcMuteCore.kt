package org.zawarka.svcMuteCore

import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.messages.config.MessagesData
import org.zawarka.svcMuteCore.mute.MuteManager
import org.zawarka.svcMuteCore.mute.MuteStorage
import org.zawarka.svcMuteCore.utils.time.TimeConfigFormatter
import java.io.File

class SvcMuteCore {

    lateinit var storage: MuteStorage
    //lateinit var groupStorage: MuteStorage

    lateinit var muteManager: MuteManager

    lateinit var messagesData: MessagesData

    lateinit var messageService: MessageService

    lateinit var timeConfigFormatter: TimeConfigFormatter

    fun init(dataFolder: File, messageService: MessageService) {
        muteManager = MuteManager()
        storage = MuteStorage(muteManager, muteManager.mutes, dataFolder)

        //groupStorage = MuteStorage(muteManager, muteManager.groupMutes, dataFolder, "groupmutes")
        muteManager.init(storage, storage)

        storage.init()
        storage.loadAll()

        //groupStorage.init()
        //groupStorage.loadAll()

        messagesData = MessagesData(File(dataFolder, "config.yml"))

        messagesData.loadFromConfig()

        timeConfigFormatter = TimeConfigFormatter(messagesData)

        this.messageService = messageService
        this.messageService.init(messagesData)
    }

    fun saveAll(){
        storage.saveAll()
        //groupStorage.saveAll()
    }
}