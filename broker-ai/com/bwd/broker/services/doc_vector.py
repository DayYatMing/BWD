from openai import AzureOpenAI
from sklearn.metrics.pairwise import cosine_similarity

import numpy as np
import logging
import httpx
import openai


def vector_chunks(chunk_pairs, params, query, top_n):
    openai.verify_ssl_certs = False

    model_name = params['openai_text_embedding_name']

    client = AzureOpenAI(
        api_key=params['openai_api_key'],
        api_version=params['openai_api_version'],
        azure_endpoint=params['openai_endpoint'],
        http_client=httpx.Client(verify=False)
    )

    resp_query = client.embeddings.create(
        model=model_name,
        input=query
    ).data[0]
    query_embedding = get_embedding_vector(resp_query)
    logging.info("User query embeddings done.")

    query_vec = np.array(query_embedding).reshape(1, -1)
    scored_chunks = [
        (text, cosine_similarity(query_vec, np.array(embedding).reshape(1, -1))[0][0])
        for text, embedding in chunk_pairs
    ]

    ranked = sorted(scored_chunks, key=lambda x: x[1], reverse=True)
    top_chunks = [chunk for chunk, _ in ranked[:top_n]]
    return top_chunks


def get_embedding_vector(obj):
    if hasattr(obj, "embedding"):
        return obj.embedding
    elif isinstance(obj, (list, tuple)):
        return obj
    elif isinstance(obj, dict) and "embedding" in obj:
        return obj["embedding"]
    else:
        raise TypeError(f"Cannot get embedding from object: {obj}")
