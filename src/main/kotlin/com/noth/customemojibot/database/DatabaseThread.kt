package com.noth.customemojibot.database

import jetbrains.exodus.env.Environment
import jetbrains.exodus.env.Environments

object DatabaseThread : Thread(){
    private lateinit var environment:Environment

    override fun start(){
        environment = Environments.newContextualInstance("/home/noth/Documents/ExodusDB")
        println("Env: ${environment.isOpen} from DatabaseThread")
    }

    fun getEnvironment() : Environment{
        return environment
    }
}