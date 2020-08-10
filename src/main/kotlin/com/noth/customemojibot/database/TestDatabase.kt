package com.noth.customemojibot.database

import jetbrains.exodus.env.Environments

class TestDatabase {
    private var environment = Environments.newInstance("/home/noth/Documents/ExodusDB")

    fun run(){
        if(environment.isOpen){
            System.out.println("Exodus Env Available")
        }
    }

    fun close() {
        environment.close()
    }
}