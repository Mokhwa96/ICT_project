import requests

API_URL = "http://localhost:8080/questions"

# POST 요청 (질문 생성)
def create_question(content):
    url = API_URL
    payload = {"content": content}
    response = requests.post(url, json=payload)
    # 응답이 성공적일 경우 JSON 데이터 반환
    if response.status_code == 200:
        return f"Created Question: {response.json()}"   # JSON 데이터 반환
    else:
        return f"Error: {response.status_code}"

# GET 요청 (질문 조회)
def get_question(question_id):
    url = f"{API_URL}/{question_id}"
    response = requests.get(url)
    
    if response.status_code == 200:
        return response.json()
    else:
        return {"error": f"Error: {response.status_code}"}

# GET 요청 (전체 질문 조회)
def get_all_questions():
    url = API_URL
    response = requests.get(url)
    
    if response.status_code == 200:
        return response.json()
    else:
        return {"error": f"Error: {response.status_code}"}

# PATCH 요청 (질문 수정)
def update_question(question_id, content):
    url = f"{API_URL}/{question_id}"
    payload = {"content": content}
    response = requests.patch(url, json=payload)
    
    if response.status_code == 200:
        return response.json()
    else:
        return {"error": f"Error: {response.status_code}"}

# DELETE 요청 (질문 삭제)
def delete_question(question_id):
    url = f"{API_URL}/{question_id}"
    response = requests.delete(url)
    
    if response.status_code == 204:
        return "Deleted successfully"
    else:
        return {"error": f"Error: {response.status_code}"}

# DELETE 요청 (전체 질문 삭제)
def delete_all_questions():
    url = API_URL
    response = requests.delete(url)
    
    if response.status_code == 204:
        return "Deleted all questions successfully"
    else:
        return {"error": f"Error: {response.status_code}"}
