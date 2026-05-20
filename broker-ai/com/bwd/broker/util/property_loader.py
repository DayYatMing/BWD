def get_params(req):
    params = {
        "search_endpoint": req.headers.get('Azure-Search-Endpoint'),
        "search_api_key": req.headers.get('Azure-Search-Apikey'),
        "search_index_name": req.headers.get('Azure-Search-Index-Name'),
        "search_index_api_version": req.headers.get('Azure-Search-Api-Version'),
        "openai_endpoint": req.headers.get('Azure-Openai-Endpoint'),
        "openai_api_key": req.headers.get('Azure-Openai-Apikey'),
        "openai_deployment_name": req.headers.get('Azure-Openai-Deployment-Name'),
        "openai_api_version": req.headers.get('Azure-Openai-Api-Version'),
        "openai_text_embedding_name": req.headers.get('Azure-Openai-Text-Embedding-Name')
    }
    return params
