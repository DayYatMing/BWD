from openai import AzureOpenAI

import httpx
import openai

from com.bwd.broker.chat_prep.payload_prepare import construct_keyword_msg


def keyword_retrival(query, params):
    openai.verify_ssl_certs = False

    model_name = params['openai_deployment_name']

    client = AzureOpenAI(
        api_key=params['openai_api_key'],
        api_version=params['openai_api_version'],
        azure_endpoint=params['openai_endpoint'],
        http_client=httpx.Client(verify=False)
    )

    messages = construct_keyword_msg(query)
    keys = client.chat.completions.create(
        model=model_name,
        messages=messages
    )

    return keys.choices[0].message.content
