from flask import jsonify

import ast
import logging

from com.bwd.broker.chat_prep.chat_prepare import content_chat, chunks_chat
from com.bwd.broker.services.doc_vector import vector_chunks
from com.bwd.broker.services.doc_search import doc_search
from com.bwd.broker.services.doc_search_chunks import semantic_process


def doc_content(user_question, user_context, params, is_ds_included):
    if is_ds_included:
        logging.info(f"Selected method: doc_content")
    else:
        logging.info(f"Selected method: default")

    openai_response = content_chat(user_question, user_context, params, is_ds_included)
    answer = openai_response["choices"][0]["message"]["content"]

    logging.info("Replied.")
    return jsonify({"reply": answer})


def doc_chunk(user_question, user_context, params, is_ds_included):
    logging.info(f"Selected method: doc_chunk")
    docs = doc_search(user_question, params)

    chunks = []
    for result in docs:
        if "contentChunks" in result:
            if isinstance(result["contentChunks"], list):
                chunks.extend(result["contentChunks"])
            else:
                chunks.append(result["contentChunks"])

    max_chunks = 5
    selected_chunks = chunks[:max_chunks]
    chunks_context = "\n".join(selected_chunks)

    openai_response = chunks_chat(user_question, user_context, chunks_context, params, is_ds_included)
    answer = openai_response["choices"][0]["message"]["content"]

    logging.info("Replied.")
    return jsonify({"reply": answer})


def doc_semantic(user_question, user_context, params, is_ds_included):
    logging.info(f"Selected method: doc_semantic")
    docs = doc_search(user_question, params)

    chunks = []
    for result in docs:
        if "contentChunks" in result:
            if isinstance(result["contentChunks"], list):
                chunks.extend(result["contentChunks"])
            else:
                chunks.append(result["contentChunks"])

    max_chunks = 5
    selected_chunks = semantic_process(user_question, chunks, params, max_chunks)
    chunks_texts = [chunk[0] for chunk in selected_chunks]
    chunks_context = "\n".join(chunks_texts)

    openai_response = chunks_chat(user_question, user_context, chunks_context, params, is_ds_included)
    answer = openai_response["choices"][0]["message"]["content"]

    logging.info("Replied.")
    return jsonify({"reply": answer})


def doc_vector(user_question, user_context, params, is_ds_included):
    logging.info(f"Selected method: doc_vector")
    docs = doc_search(user_question, params)

    chunk_pairs = []
    for result in docs:
        content = result.get("contentChunks", [])
        if isinstance(content, str):
            content = [content]

        embed = result.get("chunksEmbeddings", [])
        if isinstance(embed, list):
            embed = [ast.literal_eval(e) if isinstance(e, str) else e for e in embed]
        elif isinstance(embed, str):
            embed = [ast.literal_eval(embed)]

        chunk_pairs.extend(zip(content, embed))

    max_chunks = 5
    selected_chunks = vector_chunks(chunk_pairs, params, user_question, max_chunks)
    chunks_context = "\n".join(selected_chunks)

    openai_response = chunks_chat(user_question, user_context, chunks_context, params, is_ds_included)
    answer = openai_response["choices"][0]["message"]["content"]

    logging.info("Replied.")
    return jsonify({"reply": answer})
