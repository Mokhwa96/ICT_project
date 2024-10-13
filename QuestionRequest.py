# 답변 관련 코드
import requests

API_URL = "http://localhost:8080/question"

# POST 요청 (답변 생성)
def create_answer(content):
    url = API_URL
    payload = {"content": content}
    response = requests.post(url, json=payload)
    if response.status_code == 200:
        return f"Created Answer: {response.json()}"
    else:
        return f"Error: {response.status_code}"
