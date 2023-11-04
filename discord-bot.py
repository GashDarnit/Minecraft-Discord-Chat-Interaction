import discord
import discord
from discord.ext import commands, tasks
from discord import app_commands
import asyncio
import json

TOKEN = "" #Bot token goes here - Better keep this inside of an env file
chatroom_id =  #channel id of the channel you want to have the interaction

intents = discord.Intents.all()
client = commands.Bot(command_prefix = "!", intents = intents)

bot_info_directory = "data/"
username_map = {}

####################### COMMANDS AREA ##############################################
@client.event
async def on_ready():
    load_json_map()
    send_message_in_chat.start()
    
    print("Bot is ready!")

@client.event
async def on_message(message):
    # Handle the on_message event here
    if message.channel.id == chatroom_id:
        await add_to_file(message)
    
    await client.process_commands(message)


#Update display name in minecraft
@client.command()
async def username(ctx, *, args):
    user_id = ctx.message.author.id
    global username_map
    
    username_map[str(user_id)] = args
    update_username_map()
    await ctx.send("Updated " +  ctx.message.author.name + " to " + username_map[str(user_id)])


def update_username_map():
    json_info = json.dumps(username_map)
    file = open(bot_info_directory + "username_mapping.json", 'w')
    file.write(json_info)
    file.close()


def load_json_map():
    global username_map
    file_path = bot_info_directory + "username_mapping.json"
    
    try:
        with open(file_path, 'r') as file:
            username_map = json.load(file)
    except:
        print("File does not exist")

async def add_to_file(message):
    user_id = message.author.id
    user = await client.fetch_user(user_id)
    username = user.display_name
    
    if str(user_id) in username_map:
        username = username_map[str(user_id)]
    
    write_to_file(username, message)

#Write message to file
def write_to_file(username, message):
    if message.author.id != client.user.id:
        add_message = "<" + username + "> " + message.content + "\n"
        file = open(bot_info_directory + "discord_chat.txt", 'a')
        
        file.write(add_message)
        file.close()

#run listener in the background indefinitely
@tasks.loop(seconds = 1)
async def send_message_in_chat():
    chatroom = client.get_channel(chatroom_id)
    last_message = ""
    lines = []
    
    with open(bot_info_directory + "minecraft_chat.txt", 'r') as file:
        lines = file.readlines()

    if len(lines) > 0:
        for i in lines:
            last_message = i
            await chatroom.send(i)
            await asyncio.sleep(1)  # Add a sleep between messages if needed

        remove_from_file(last_message)

#remove messages from file
def remove_from_file(match_string):
    try:
        with open(bot_info_directory + "minecraft_chat.txt", 'r') as file:
            lines = file.readlines()

        with open(bot_info_directory + "minecraft_chat.txt", 'w') as file:
            # Set a flag to indicate when to start writing lines to the file
            start_writing = False
            

            for line in lines:
                if match_string in line:
                    start_writing = True
                    continue

                if start_writing:
                    file.write(line)

    except Exception as e:
        print("An error occurred: " + e)



client.run(TOKEN);


