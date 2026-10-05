import os
import json
import base64
from pathlib import Path
from dotenv import load_dotenv
from google import genai
from google.genai import types

root_env = Path(__file__).resolve().parent.parent / ".env"
load_dotenv(dotenv_path=root_env if root_env.exists() else None)
GEMINI_TOKEN = os.getenv("GEMINI_KEY")

client = genai.Client(api_key=GEMINI_TOKEN)
TAGS_PERMITIDAS = ["Alimentação", "Transporte", "Lazer", "Saúde", "Moradia", "Outros"]
system_role = (
    "Você é um parser financeiro JSON. Extraia produto, preço e tag do texto ou imagem do usuário.\n"
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


def enviarMensagem(userMessage, imageBase64=None, mimeType=None):
    parts = []

    if imageBase64:
        image_bytes = base64.b64decode(imageBase64)
        parts.append(types.Part.from_bytes(data=image_bytes, mime_type=mimeType or "image/jpeg"))

    parts.append(types.Part.from_text(text=userMessage))

    response = chat.send_message(parts)
    return json.loads(response.text)
