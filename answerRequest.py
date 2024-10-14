import requests

API_URL = "http://localhost:8080/answer"

# POST 요청 (답변 생성)
def create_answer(question_id, content):
    url = API_URL
    payload = {"questionId": question_id, "content": content}
    response = requests.post(url, json=payload)
    if response.status_code == 200:
        return f"Created Answer: {response.json()}"
    else:
        return f"Error: {response.status_code}"

def get_answers(question_id):
    url = f"{API_URL}/{question_id}"
    response = requests.get(url)
    
    # 서버 응답이 200(OK)인 경우 JSON 데이터 반환
    if response.status_code == 200:
        return response.json()  # 문자열이 아닌 JSON 데이터를 반환
    
    else:
        return {"error": f"Error: {response.status_code}"}
    

# PATCH 요청 (답변 수정)
def update_answer(id, content):
    url = f"{API_URL}/{id}"
    payload = {"content": content}
    response = requests.patch(url, json=payload)
    if response.status_code == 200:
        return f"Updated Answer: {response.json()}"
    else:
        return f"Error: {response.status_code}"

# DELETE 요청 (답변 삭제)
def delete_answer(id):
    url = f"{API_URL}/{id}"
    response = requests.delete(url)
    if response.status_code == 204:
        return "Deleted successfully"
    else:
        return f"Error: {response.status_code}"
