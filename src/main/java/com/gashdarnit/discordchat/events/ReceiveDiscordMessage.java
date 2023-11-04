package com.gashdarnit.discordchat.events;

import org.bukkit.Bukkit;

import java.io.*;
import java.text.Normalizer;
import java.util.Base64;

public class ReceiveDiscordMessage implements Runnable {
    private static String fileDir = "Replace this part with the desired directory" + "\\data\\";
    private volatile boolean isRunning = true;

    @Override
    public void run() {
        while (isRunning) {
            sendMessageToChat();

            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    private void sendMessageToChat() {
        String line = "";
        String lastMessage = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileDir + "discord_chat.txt"));

            while((line = reader.readLine()) != null) {
                int index = line.indexOf(">");
                String username = line.substring(0, index + 1); // Include the '>'
                String messages = line.substring(index + 1).trim();

                Bukkit.broadcastMessage(username + " " + messages);
                lastMessage = line;
            }
            reader.close();

            clearFile(lastMessage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void clearFile(String match) {
        try {
            File file = new File(fileDir + "discord_chat.txt");
            BufferedReader br = new BufferedReader(new FileReader(fileDir + "discord_chat.txt"));

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            long currentPosition = 0;
            String line;

            while((line = br.readLine()) != null) {
                String encodedLine = encode(line);
                if(encodedLine.contains(encode(match))) {
                    break;
                }
                currentPosition++;
            }

            raf.seek(currentPosition);
            raf.setLength(currentPosition);
            raf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

}
