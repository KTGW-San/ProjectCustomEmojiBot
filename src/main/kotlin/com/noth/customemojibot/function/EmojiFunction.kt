package com.noth.customemojibot.function

import com.noth.customemojibot.data.Emoji
import com.noth.customemojibot.database.DatabaseThread
import com.noth.customemojibot.database.TestDatabase
import jetbrains.exodus.ArrayByteIterable
import jetbrains.exodus.ByteIterable
import jetbrains.exodus.bindings.StringBinding
import jetbrains.exodus.env.StoreConfig
import java.lang.NullPointerException
import java.net.URL

object EmojiFunction {
    private var environment = DatabaseThread.getEnvironment()
    private var transaction = environment.beginExclusiveTransaction()
    private var store = environment.openStore("TestEmojiStore",StoreConfig.WITHOUT_DUPLICATES, transaction)

    init {
        println("Env: ${environment.isOpen} from EmojiFunc.")
    }

    fun removeEmoji(name:String) : Boolean{
        return if(store.delete(transaction, stringToEntry(name))){
            transaction.flush()
            println("Emoji entry removed successfully!")
            true
        }else{
            println("Entry not found! Aborting.")
            false
        }
    }

    fun registerEmoji(name:String, link:String) : Boolean{
        return if(store.add(transaction,stringToEntry(name),stringToEntry(link))){
            transaction.flush()
            println("Emoji entry registered successfully!")
            true
        }else{
            println("Entry already exists! Aborting.")
            false
        }
    }

    fun getEmoji(name:String) : Emoji?{
        try {
            if(store.exists(transaction, stringToEntry(name), store.get(transaction, stringToEntry(name))!!)){
                return Emoji(name, entryToString(store.get(transaction, stringToEntry(name))!!))
            }
        }catch (exception:NullPointerException){
            print("Entry could not found.")
        }
        return null
    }

    fun stringToEntry(string:String) : ByteIterable{
        return StringBinding.stringToEntry(string)
    }

    fun entryToString(entry: ByteIterable) : String{
        return StringBinding.entryToString(entry)
    }
}

