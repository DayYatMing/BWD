import requests
import urllib3
import logging


def doc_search(query, params):
    url = (f"{params['search_endpoint']}/indexes/{params['search_index_name']}/docs/search"
           f"?api-version={params['search_index_api_version']}")
    urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
    logging.info(f"Searching docs via: {url}")

    headers = {
        "Content-Type": "application/json",
        "api-key": params['search_api_key']
    }
    payload = {
        "search": query,
        "select": "",
        "top": 5
    }
    response = requests.post(url, headers=headers, json=payload, verify=False)
    response.raise_for_status()
    results = response.json()

    return results.get("value", [])
