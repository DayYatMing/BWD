from openai import AzureOpenAI

import httpx
import numpy as np
import openai
import logging


def semantic_process(query, chunks, params, top_n):
    openai.verify_ssl_certs = False

    model_name = params['openai_text_embedding_name']

    client = AzureOpenAI(
        api_key=params['openai_api_key'],
        api_version=params['openai_api_version'],
        azure_endpoint=params['openai_endpoint'],
        http_client=httpx.Client(verify=False)
    )

    chunk_embeddings = []
    for chunk in chunks:
        resp = client.embeddings.create(
            model=model_name,
            input=chunk
        ).data[0]
        emb = get_embedding_vector(resp)
        chunk_embeddings.append(emb)
    logging.info("Chunk embeddings done.")

    resp_query = client.embeddings.create(
        model=model_name,
        input=query
    ).data[0]
    query_embedding = get_embedding_vector(resp_query)
    logging.info("User query embeddings done.")

    ranked_chunks = sorted(
        zip(chunks, chunk_embeddings),
        key=lambda x: cosine_similarity(query_embedding, x[1]),
        reverse=True
    )

    return ranked_chunks[:top_n]


def cosine_similarity(a, b):
    return np.dot(a, b) / (np.linalg.norm(a) * np.linalg.norm(b))


def get_embedding_vector(obj):
    if hasattr(obj, "embedding"):
        return obj.embedding
    elif isinstance(obj, (list, tuple)):
        return obj
    elif isinstance(obj, dict) and "embedding" in obj:
        return obj["embedding"]
    else:
        raise TypeError(f"Cannot get embedding from object: {obj}")
