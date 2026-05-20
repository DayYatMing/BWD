from flask import Flask, request, jsonify

import logging
import json
import urllib.parse

from com.bwd.broker.broker_dispatcher import doc_content, doc_chunk, doc_semantic, doc_vector
from com.bwd.broker.chat_prep.payload_prepare import stress_bot_unknown_reply
from com.bwd.broker.util.unknown_responses import get_unknown_responses
from com.bwd.broker.services.doc_keyword import keyword_retrival
from com.bwd.broker.util.property_loader import get_params

app = Flask(__name__)


@app.route('/broker_ai', methods=['POST'])
def chat_doc():
    logging.info("Starting POST request...")
    params = get_params(request)

    scope_header = request.headers.get("X-Consumer-Groups")
    if not scope_header:
        return jsonify({"message": "Forbidden"}), 403
    scopes = [scope.strip() for scope in scope_header.split(",")]
    if "nms" not in scopes:
        return jsonify({"message": "Forbidden"}), 403

    try:
        user_question = urllib.parse.unquote(request.json.get("message"))
        user_context = urllib.parse.unquote(request.json.get("context"))
        method = request.json.get("method")
        logging.info(f"User request received: {user_question} \n{user_context}")

        return method_choices(method, user_question, user_context, params)

    except Exception as e:
        logging.info(f"error: {e}")
        return jsonify({"error": str(e)})


def method_choices(method, user_question, user_context, params):
    if not method == "doc_multiple":
        return dispatch_method(method, user_question, user_context, params, False)
    else:
        keywords = keyword_retrival(user_question, params)
        logging.info(f"Keywords found: {keywords}")

        method = "doc_content"
        results = dispatch_method(method, keywords, user_context, params, True)
        results_reply = json.loads(results.get_data(as_text=True))["reply"]
        logging.info(results_reply)

        if any(str_unknown.lower() in results_reply.lower() for str_unknown in get_unknown_responses()):
            logging.info("Replied response dissatisfaction, attempting one more time...")
            method = "doc_content"
            results = dispatch_method(method, keywords, stress_bot_unknown_reply(user_context, keywords), params, True)
            results_reply = json.loads(results.get_data(as_text=True))["reply"]
            logging.info(f"2nd attempt: " + results_reply)

        if any(str_unknown.lower() in results_reply.lower() for str_unknown in get_unknown_responses()):
            logging.info("Replied response dissatisfaction, continuing next method...")
            method = "doc_vector"
            results = dispatch_method(method, keywords, user_context, params, False)

        return results


def dispatch_method(method, question, context, params, is_ds_included):
    logging.info(f"Search method: {method}")

    switch = {
        "doc_content": lambda: doc_content(question, context, params, is_ds_included),
        "doc_chunk": lambda: doc_chunk(question, context, params, is_ds_included),
        "doc_semantic": lambda: doc_semantic(question, context, params, is_ds_included),
        "doc_vector": lambda: doc_vector(question, context, params, is_ds_included)
    }

    results = switch.get(method, lambda: doc_content(question, context, params, False))()

    return results


if __name__ == '__main__':
    host = "0.0.0.0"
    port = 3100
    logging.basicConfig(level=logging.DEBUG)
    logging.info(f"##########App is running on {port}##########")

    app.run(host=host, port=port, debug=False)
