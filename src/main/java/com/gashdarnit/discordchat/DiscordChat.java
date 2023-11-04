package com.gashdarnit.discordchat;

import com.gashdarnit.discordchat.events.ReceiveDiscordMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import com.gashdarnit.discordchat.events.DiscordInteraction;

public final class DiscordChat extends JavaPlugin {
    private ReceiveDiscordMessage receiveDiscordMessage;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DiscordInteraction(), this);

        receiveDiscordMessage = new ReceiveDiscordMessage();
        Thread thread = new Thread(receiveDiscordMessage);
        thread.start();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Discord Chat]: Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Discord Chat]: Plugin is disabled!");

        receiveDiscordMessage.stop();
    }

}
