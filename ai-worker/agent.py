import os
from pathlib import Path
from dotenv import load_dotenv
from google import genai  
from google.genai import types
import json
root_env = Path(__file__).resolve().parent.parent / ".env"
load_dotenv(dotenv_path=root_env if root_env.exists() else None)
GEMINI_TOKEN = os.getenv("GEMINI_KEY")

client = genai.Client(api_key=GEMINI_TOKEN)
TAGS_PERMITIDAS = ["Alimentação", "Transporte", "Lazer", "Saúde", "Moradia", "Outros"]
system_role = (
    "Você é um parser financeiro JSON. Extraia produto, preço e tag do texto do usuário.\n"
    "Suas opções de tags permitidas são estritamente: " + ", ".join(TAGS_PERMITIDAS) + ".\n"
    "Retorne sempre e somente um JSON válido no formato: "
    '{"produto": string, "preco": float, "tag": string}'
)

modelConfig = types.GenerateContentConfig(
    system_instruction=system_role,
    response_mime_type="application/json"
)


chat = client.chats.create(
    model="gemini-3.8-flash",
    config=modelConfig
)




def enviarMensagem(userMessage):
    response = chat.send_message(userMessage)
    json_extraido = json.loads(response.text)
    print(response.text)
    return json_extraido