import gradio as gr
import userRequest
import historyPage
import googlemaps
import asyncio
import requests

SESSION_CHECK_API_URL = "http://localhost:8080/user/checkSession"
GMAPS_API_KEY = ""  # Google Maps API 키를 설정하세요.
SPRING_API_URL = "http://localhost:8080/analyze"

gmaps = googlemaps.Client(key=GMAPS_API_KEY)

chat_history = []

def response(message, chat_history):
    chat_history.append(message)  # 대화 기록에 사용자 메시지 추가
    response_message = userRequest.create_question(message)
    chat_history.append(response_message)  # 대화 기록에 챗봇 응답 추가
    return response_message

async def recommend_hospital(chat_history, user_location):
    # Spring 서버에 대화 기록을 POST 요청으로 전송
    response = requests.post(SPRING_API_URL, json={"chat_history": chat_history})
    
    if response.status_code == 200:
        data = response.json()
        symptoms = data.get("symptoms")
        disease = data.get("disease")
        hospital_type = data.get("hospital_type")

        # 주소를 위도와 경도로 변환
        geocode_result = gmaps.geocode(user_location)
        if not geocode_result:
            return "유효하지 않은 주소입니다. 정확한 주소를 입력해주세요."
        
        location = geocode_result[0]['geometry']['location']
        latitude = location['lat']
        longitude = location['lng']

        # Google Maps API를 이용해 사용자의 거주지 근처 병원 검색
        loop = asyncio.get_event_loop()
        places_result = await loop.run_in_executor(None, gmaps.places_nearby, (latitude, longitude), 5000, 'hospital', hospital_type)

        if places_result["results"]:
            nearest_hospital = places_result["results"][0]["name"]
            hospital_lat = places_result["results"][0]["geometry"]["location"]["lat"]
            hospital_lng = places_result["results"][0]["geometry"]["location"]["lng"]
            address = places_result["results"][0]["vicinity"]
            
            # HTML 지도를 생성하여 반환
            map_html = f"""
            <iframe
                width="600"
                height="450"
                style="border:0"
                loading="lazy"
                allowfullscreen
                src="https://www.google.com/maps/embed/v1/place?key={GMAPS_API_KEY}&q={hospital_lat},{hospital_lng}">
            </iframe>
            """
            return f"당신은 {', '.join(symptoms)} 등을 보아 {disease}로 확인되며, {hospital_type}으로 가야합니다. 가까운 병원은 '{nearest_hospital}' 입니다. 주소: {address}", map_html
        else:
            return "해당 증상에 맞는 가까운 병원을 찾을 수 없습니다.", ""
    else:
        return "서버와의 통신에 문제가 발생했습니다. 나중에 다시 시도해주세요.", ""

def login_page():
    with gr.Column():
        user_id_input = gr.Textbox(label="User_Id", placeholder="Enter your username")
        password_input = gr.Textbox(label="Password", type="password", placeholder="Enter your password")
        login_button = gr.Button("Login")
        result_output = gr.Textbox(label="Result", interactive=False)
        
        login_button.click(userRequest.login, inputs=[user_id_input, password_input], outputs=result_output)

def session_check_page():
    with gr.Column():
        session_button = gr.Button("세션 상태 확인")
        session_output = gr.Textbox(label="Session Result", interactive=False)
        session_button.click(userRequest.check_session, outputs=session_output)

def logout_page():
    with gr.Column():
        logout_button = gr.Button("Logout")
        logout_output = gr.Textbox(label="Logout Result", interactive=False)
        logout_button.click(userRequest.logout, outputs=logout_output)

def chatbot_page():
    global chat_history  # 전역 변수 사용 선언

    chat_interface = gr.ChatInterface(
        fn=response,
        title="Over Mind",
        description="질문하면 답변을 받을 수 있습니다.",
        theme="soft",
        examples=[["질문 1"], ["질문 2"], ["질문 3"]],
        retry_btn="다시보내기 ↩",
        undo_btn="이전챗 삭제 ❌",
        clear_btn="전체 채팅 💫",
        submit_btn="Enter",
    )
    
    # 대화 기록을 분석하여 병원 추천 버튼 추가
    with gr.Column():
        recommend_button = gr.Button("증상 기반 병원 추천")
        user_location_input = gr.Textbox(label="현재 거주지 주소", placeholder="거주지 주소를 입력하세요")
        recommend_output = gr.Textbox(label="병원 추천 결과", interactive=False)
        map_output = gr.HTML(label="병원 위치 지도")

        recommend_button.click(
            recommend_hospital,
            inputs=[gr.State(chat_history), user_location_input],
            outputs=[recommend_output, map_output]
        )

def select_page(page):
    if page == "로그인":
        return gr.update(visible=True), gr.update(visible=False), gr.update(visible=False)
    else:
        if userRequest.check_login_status():
            if page == "챗봇":
                return gr.update(visible=False), gr.update(visible=True), gr.update(visible=False)
            elif page == "기록 보기":
                return gr.update(visible=False), gr.update(visible=False), gr.update(visible=True)
        else:
            return gr.update(visible=True), gr.update(visible=False), gr.update(visible=False)

with gr.Blocks(theme="soft") as demo:
    chat_history = []  # 챗봇과의 대화 기록 저장소

    # 상단 버튼 UI
    with gr.Row():
        login_btn = gr.Button("로그인")
        chatbot_btn = gr.Button("챗봇")
        history_btn = gr.Button("기록 보기")
    
    # 각 페이지에 대한 출력 부분
    login_page_box = gr.Column(visible=True)
    chatbot_page_box = gr.Column(visible=False)
    history_page_box = gr.Column(visible=False)
    
    # 팝업 메시지용 Markdown 출력 부분
    popup_message = gr.Markdown(value="")

    with login_page_box:
        login_page()
    with chatbot_page_box:
        chatbot_page()
    with history_page_box:
        historyPage.history_Page(chat_history)

    login_btn.click(select_page, inputs=[gr.State("로그인")], outputs=[login_page_box, chatbot_page_box, history_page_box])
    chatbot_btn.click(select_page, inputs=[gr.State("챗봇")], outputs=[login_page_box, chatbot_page_box, history_page_box])
    history_btn.click(select_page, inputs=[gr.State("기록 보기")], outputs=[login_page_box, chatbot_page_box, history_page_box])

demo.launch()