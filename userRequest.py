import requests

# Spring 서버의 로그인 API URL
LOGIN_API_URL = "http://localhost:8080/user/login"
LOGOUT_API_URL = "http://localhost:8080/user/logout"
SESSION_CHECK_API_URL = "http://localhost:8080/user/checkSession"

session = requests.Session()

# 로그인 요청 함수
def login(user_id, password):
    data = {
        "user_id": user_id,
        "password": password
    }

    # Spring REST API에 POST 요청 (세션 객체 사용)
    response = session.post(LOGIN_API_URL, json=data)

    # 응답 결과에 따라 메시지 반환
    if response.status_code == 200:
        return response.text
    else:
        return "로그인 실패! 사용자 이름 또는 비밀번호가 틀렸습니다."
    
    # 로그아웃 요청 함수
def logout():
    response = session.get(LOGOUT_API_URL)
    return response.text

# 세션 상태 확인 함수
def check_session():
    response = requests.get(SESSION_CHECK_API_URL)
    return response.text

# 로그인 상태 확인 함수
def check_login_status():
    # 세션 객체를 사용해 로그인 상태 확인
    response = session.get(SESSION_CHECK_API_URL)
    if response.status_code == 200:
        return True  # 로그인된 상태
    else:
        return False  # 로그인되지 않은 상태
