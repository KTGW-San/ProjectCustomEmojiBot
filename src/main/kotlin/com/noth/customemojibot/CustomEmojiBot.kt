package com.noth.customemojibot

import com.noth.customemojibot.command.Command
import com.noth.customemojibot.database.DatabaseThread
import com.noth.customemojibot.function.EmojiFunction
import net.dv8tion.jda.api.JDABuilder
import java.lang.IllegalArgumentException

fun main(args:Array<String>) {
    if (args.isNotEmpty()) {
        JDABuilder.createDefault(args[0])
                .addEventListeners(Command().listener)
                .build().run { DatabaseThread.start() }
    } else {
        throw IllegalArgumentException("Requires at least 1 arguments.")
    }
}