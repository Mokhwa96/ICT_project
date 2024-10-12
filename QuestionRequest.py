import requests

API_URL = "http://localhost:8080/questions"

# POST 요청 (질문 생성)
def create_question(content):
    url = API_URL
    payload = {"content": content}
    response = requests.post(url, json=payload)
    if response.status_code == 200:
        return f"Created Question: {response.json()}"
    else:
        return f"Error: {response.status_code}"
