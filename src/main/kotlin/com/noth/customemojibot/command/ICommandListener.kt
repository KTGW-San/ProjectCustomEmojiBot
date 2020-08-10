package com.noth.customemojibot.command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

interface ICommandListener : EventListener {
    fun onCommand(prefix:Char, args:List<String>, event:MessageReceivedEvent)

    //fun onCommandExecuted()
}