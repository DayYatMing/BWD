
def construct_message(user_input, user_context, chunks_context):
    messages = [{
        "role": "system",
        "content": f"You are a helpful assistant. {chatbot_profile()}"
                   "Use provided content and context if there are relevant information. "
                   "Also use provided data source if there are relevant information. "
                   "Finally, if no relevant information, use your own knowledge or you don't know. "
    }]

    current_role = None
    current_content = []
    for line in user_context.splitlines():
        if line.startswith("User Previous Question: "):
            if current_role:
                messages.append({"role": current_role, "content": "\n".join(current_content).strip()})
            current_role = "user"
            current_content = [line.replace("User Previous Question: ", "").strip()]
        elif line.startswith("Bot Previous Answer: "):
            if current_role:
                messages.append({"role": current_role, "content": "\n".join(current_content).strip()})
            current_role = "assistant"
            current_content = [line.replace("Bot Previous Answer: ", "").strip()]
        else:
            current_content.append(line)

    if current_role:
        messages.append({"role": current_role, "content": "\n".join(current_content).strip()})

    usr_msg = f"Question: {user_input}"
    if not chunks_context:
        usr_msg = f"Context: {chunks_context}\nQuestion: {user_input}"

    messages.append({"role": "user", "content": usr_msg})

    return messages


def construct_datasource(query, params):
    datasource = [{
        "type": "azure_search",
        "parameters": {
            "endpoint": params['search_endpoint'],
            "index_name": params['search_index_name'],
            "authentication": {
                "type": "api_key",
                "key": params['search_api_key']
            },
            "search": query,
            "top": 5
        }
    }]

    return datasource


def construct_keyword_msg(query):
    messages = [
        {"role": "system", "content": "You are a helpful assistant that extracts keywords."},
        {"role": "user", "content": f"Extract keywords from this text: {query}"}
    ]

    return messages


def stress_bot_unknown_reply(user_context, keywords):
    reply = (f"{user_context}\nBot Previous Answer: "
             f"It seems like you are asking for an information related to {keywords}. "
             f"However, the context is unclear. "
             f"Could you provide more details or specify what {keywords} refers to? "
             f"For example, is it a product code, model number, service config, issue caused and troubleshoot "
             f"or something else?")

    return reply


def chatbot_profile():
    profile = "Your name is Amy. "

    return profile
