
# Discord-Minecraft Chat Interaction

A very primitive approach to allowing 2-way communication between users in a Discord server with users inside of a Minecraft server.


## Instructions
### Discord Bot
Within the `discord-bot.py` file, add your bot token to the `TOKEN` variable, and the channel ID of the channel you want to allow the interaction to take place in the `chatroom_id` variable. Both of these variables can be found at the top of the script.

### Minecraft Server Plugin
Make sure that the server you are running allows plugins such as `spigot`. Within the `events` folder, replace the `"Replace this part with the desired directory"` lines that you can find under the `fileDir` variable at the top of the scripts with the directory that you want. Note that this directory needs to be the same directory that the `data` folder is located at.

After modifying the information that needed to be changed, build the artifact and place it inside of your server's `plugins` folder. After that, all there's left to do is launch your server and everything would work on its own.
## Discord Commands
The prefix used in this bot is set to `!`. You can modify this to whatever prefix that suits your fancy.

### Update Username Displayed in Minecraft
You can use the command `!username {desired username}` to update your username that would be displayed in-game. By default, your discord username will be displayed.