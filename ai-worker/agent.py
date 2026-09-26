import os
from pathlib import Path
from dotenv import load_dotenv
from google import genai  # Fixed import
root_env = Path(__file__).resolve().parent.parent / ".env"
load_dotenv(dotenv_path=root_env if root_env.exists() else None)
GEMINI_TOKEN = os.getenv("GEMINI_KEY")

client = genai.Client(api_key=GEMINI_TOKEN)
chat = client.chats.create(model="gemini-3.8-flash")
while True:
    user_input = input('Eu: ')
    if user_input.lower() == '0':
        break
    else:
        response = chat.send_message(user_input)
        print(response.text) 