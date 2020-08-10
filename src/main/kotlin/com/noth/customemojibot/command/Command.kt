package com.noth.customemojibot.command

import com.noth.customemojibot.data.Emoji
import com.noth.customemojibot.function.EmojiFunction
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class Command : ICommandListener {
    private var cmdPrefix = '!'
    private var emojiPrefix = '#'

    var listener:CommandListener = CommandListener(this)


    override fun onCommand(prefix: Char, args: List<String>, event: MessageReceivedEvent) {
        when(prefix) {
            cmdPrefix -> {
                command(args, event)
            }
            emojiPrefix -> {
                getEmoji(args,event.channel,event.message)
            }
        }
    }

    fun command(args: List<String>, event: MessageReceivedEvent) {
        if(args[0] == "emoji" && args.size > 1) {
            event.message.delete().queue()
            when (args[1]) {
                "help" -> {
                    event.channel.sendMessage("Help Command").queue()
                }

                "register" -> {
                    if(args.size > 3 && EmojiFunction.registerEmoji(args[2],args[3])){
                        event.channel.sendMessage(embedBuilder(event.author,":white_check_mark: 正常に追加されました！","#${args[2]}", Color.GREEN)).queue()
                    }else{
                        event.channel.sendMessage(embedBuilder(event.author,":no_entry_sign: 追加に失敗しました","既に登録されているか、使い方を間違えています\n" + "ヘルプコマンドを参照してください", Color.RED)).queue()
                    }
                }

                "delete" -> {
                    if(args.size > 2 && EmojiFunction.removeEmoji(args[2])){
                        event.channel.sendMessage(embedBuilder(event.author,":white_check_mark: 正常に削除が完了しました！","#${args[2]}", Color.GREEN)).queue()
                    }else{
                        event.channel.sendMessage(embedBuilder(event.author,":no_entry_sign: 削除に失敗しました","既に削除されているか、使い方を間違えています\n" + "ヘルプコマンドを参照してください", Color.RED)).queue()
                    }
                }

                else -> event.channel.sendMessage("nanmo nai yo").queue()
            }
        }
    }

    fun embedBuilder(author: User, emoji: Emoji) : MessageEmbed {
        val builder = EmbedBuilder()
            .setAuthor(author.name,null,author.avatarUrl)
            .setTitle("#${emoji.name}")
            .setImage(emoji.link)
        return builder.build()
    }

    fun embedBuilder(author: User, message: String, desctiption:String?,  color: Color) : MessageEmbed{
        val builder = EmbedBuilder()
            .setAuthor(author.name,null,author.avatarUrl)
            .setTitle(message)
            .setColor(color)
            .setDescription(desctiption)
        return builder.build()
    }

    fun getEmoji(args: List<String>,channel: MessageChannel, msg: Message){
        val emoji: Emoji? = EmojiFunction.getEmoji(args[0])
        if(emoji != null){
            channel.sendMessage(embedBuilder(msg.author, emoji)).queue()
        }
    }
}