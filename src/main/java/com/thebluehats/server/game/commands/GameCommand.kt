package com.thebluehats.server.game.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.regex.Pattern

abstract class GameCommand : CommandExecutor {
    private val cmdRegex = Pattern.compile("[<>]")

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) return false

        commandNames.forEach { c ->
            if (!label.equals(c, ignoreCase = true)) return@forEach

            val usageMessage = getUsageMessage(c)

            if (usageMessage != null) {
                if (args.isEmpty() && cmdRegex.matcher(usageMessage).find()) {
                    sender.sendMessage(usageMessage)

                    return@forEach
                }
            }

            runCommand(sender, c, args)
        }

        return true
    }

    fun formatStandardUsageMessage(cmd: String, description: String, vararg args: String): String {
        val argsJoiner = StringJoiner(" ")
        for (arg in args) {
            argsJoiner.add("<$arg>")
        }
        return (ChatColor.DARK_PURPLE.toString() + "/" + cmd + " - " + ChatColor.RED + description + ChatColor.DARK_PURPLE
            + " | Usage " + ChatColor.DARK_PURPLE + "/" + cmd + " " + ChatColor.RED + argsJoiner.toString())
    }

    fun formatStandardErrorMessage(error: String): String {
        return ChatColor.DARK_PURPLE.toString() + "Error! " + ChatColor.RED + error
    }

    fun registerCommand(javaPlugin: JavaPlugin) {
        for (commandName in commandNames) {
            javaPlugin.getCommand(commandName).executor = this
        }
    }

    abstract val commandNames: Array<String>
    abstract fun getUsageMessage(cmd: String?): String?
    abstract fun runCommand(player: Player, cmd: String?, args: Array<String>)
}