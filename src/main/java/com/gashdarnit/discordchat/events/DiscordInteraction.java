import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.io.*;

public class DiscordInteraction implements Listener {
    private static String fileDir = "Replace this part with the desired directory" + "\\data\\";

    @EventHandler
    private void advancementMessageListener(PlayerAdvancementDoneEvent event) {
        String playerName = event.getPlayer().getName();
        String advancement = event.getAdvancement().getDisplay().getTitle();
        String addMessage = "[Server]: " + playerName + " has made the advancement [" + advancement + "]";

        try {
            FileWriter fw = new FileWriter(fileDir + "minecraft_chat.txt", true);
            fw.write(addMessage);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @EventHandler
    private void chatMessageListener(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();
        String addMessage = "[" +  playerName + "]: " + message;

        try {
            FileWriter fw = new FileWriter(fileDir + "minecraft_chat.txt", true);
            fw.write(addMessage);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
