#
import requests

# Spring 서버의 로그인 API URL
LOGIN_API_URL = "http://localhost:8080/user/login"
LOGOUT_API_URL = "http://localhost:8080/user/logout"
SESSION_CHECK_API_URL = "http://localhost:8080/user/checkSession"

# 세션 객체 생성
session = requests.Session()

# 로그인 요청 함수
def login(user_id, password):
    data = {
        "user_id": user_id,
        "password": password
    }
    # Spring REST API에 POST 요청 (세션 객체 사용)
    response = session.post(LOGIN_API_URL, json=data)

    if response.status_code == 200:
        return response.text
    else:
        return "로그인 실패! 사용자 이름 또는 비밀번호가 틀렸습니다."
    
# 로그아웃 요청 함수
def logout():
    response = session.get(LOGOUT_API_URL)
    return response.text

def create_question(content):
    url = "http://localhost:8080/send-to-google-ai"
    payload = {"content": content}
    
    # 세션 객체를 사용하여 POST 요청을 보냄
    response = session.post(url, json=payload)  # session.post()를 사용
    print(response)
    print(response.text)
    if response.status_code == 200:
        return response.text 
    elif response.status_code == 400:
        return "세션에 문제가 있습니다."
    else :
        return response

# 세션 상태 확인 함수
def check_session():
    response = session.get(SESSION_CHECK_API_URL)  # 세션 객체를 사용하도록 수정
    return response.text

# 로그인 상태 확인 함수 : 로그인을 해야 챗봇이랑, 기록보기를 사용할 수 있음.
def check_login_status():
    # 세션 객체를 사용해 로그인 상태 확인
    response = session.get(SESSION_CHECK_API_URL)
    if response.status_code == 200:
        return True  # 로그인된 상태
    else:
        return False  # 로그인되지 않은 상태
    
# Spring 서버에서 세션 정보를 가져오는 함수
def fetch_user_id():
    try:
        response = requests.get("http://localhost:8080/session-userid")
        response.raise_for_status()
        user_id = response.json().get("userId", "")
        return user_id
    except requests.exceptions.RequestException as e:
        print(f"Error fetching user ID: {e}")
        return ""  # 오류가 발생하면 빈 문자열 반환
