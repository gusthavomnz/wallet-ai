from flask import Flask, request, jsonify
from agent import enviarMensagem
app = Flask(__name__)


@app.route("/hello", methods=["GET"])
def hello_world():
    return jsonify({"message": "Hello, World!"}), 200


@app.route("/ai", methods=["POST"])
def ai():
    body = request.get_json()
    response = enviarMensagem(body["message"])
    return jsonify(response),200



if __name__ == "__main__":
    app.run(port=5000, debug=True)
