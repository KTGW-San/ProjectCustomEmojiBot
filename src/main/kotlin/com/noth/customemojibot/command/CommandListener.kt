package com.noth.customemojibot.command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandListener(var listener: ICommandListener) : ListenerAdapter(){
    private var cmdPrefix = '!'
    private var emojiPrefix = '#'

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message
        val content = message.contentRaw

        if (!(message.author.isBot)) {
            when(content[0]) {
                cmdPrefix ->    listener.onCommand(cmdPrefix, parseMessage(content[0], content), event)
                emojiPrefix ->  listener.onCommand(emojiPrefix, parseMessage(content[0], content), event)
            }
        }
    }

    private fun parseMessage(prefix: Char , msg: String) : List<String> {
        val str: MutableList<String> = mutableListOf()
        var builder:StringBuilder = StringBuilder()

        try {
            for (i in msg.trimStart(prefix)) {
                //Detect Spaces
                if(i == ' ') {
                    //Add String to List
                    str.add(builder.toString())
                    //Clear StringBuilder
                    builder = builder.delete(0,builder.length)
                } else {
                    builder.append(i)
                }
            }
        } finally {
            str.add(builder.toString())
        }

        return str
    }
}