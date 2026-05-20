import openai
import requests
import logging
import urllib3

from com.bwd.broker.chat_prep.payload_prepare import construct_message, construct_datasource


def content_chat(user_input, user_context, params, is_ds_included):
    openai.verify_ssl_certs = False
    urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

    url = (f"{params['openai_endpoint']}/openai/deployments/{params['openai_deployment_name']}/chat/completions"
           f"?api-version={params['openai_api_version']}")
    logging.info(f"Preparing chats via: {url}")

    headers = {
        "Content-Type": "application/json",
        "api-key": params['openai_api_key']
    }

    messages = construct_message(user_input, user_context, "")
    data_sources = construct_datasource(user_input, params)

    payload = {
        "messages": messages,
        "max_tokens": 1000,
        "temperature": 0.7,
        "top_p": 0.9
    }

    if is_ds_included:
        payload["data_sources"] = data_sources

    response = requests.post(url, headers=headers, json=payload, verify=False)
    response.raise_for_status()
    return response.json()


def chunks_chat(user_input, user_context, chunks_context, params, is_ds_included):
    url = (f"{params['openai_endpoint']}/openai/deployments/{params['openai_deployment_name']}/chat/completions"
           f"?api-version={params['openai_api_version']}")
    logging.info(f"Preparing chats via: {url}")

    headers = {
        "Content-Type": "application/json",
        "api-key": params['openai_api_key']
    }

    messages = construct_message(user_input, user_context, chunks_context)
    data_sources = construct_datasource(user_input, params)

    payload = {
        "messages": messages,
        "max_tokens": 1000,
        "temperature": 0.7,
        "top_p": 0.9
    }

    if is_ds_included:
        payload["data_sources"] = data_sources

    response = requests.post(url, headers=headers, json=payload, verify=False)
    response.raise_for_status()
    return response.json()
