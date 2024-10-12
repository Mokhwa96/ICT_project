import gradio as gr
import AnswerRequest as answerRequest
import QuestionRequest as questionRequest
import UserRequest as userRequest
import requests

SESSION_CHECK_API_URL = "http://localhost:8080/user/checkSession"

def response(message, history):
    # 답변을 조회하는 함수 호출
    #questionRequest.create_question(message)
    #request = answerRequest.get_answers(1)
    # 질문을 생성하는 함수 호출
    question_id = questionRequest.create_question(message)
    
    # 질문 생성이 성공했을 때만 답변을 조회
    if isinstance(question_id, dict) and "error" not in question_id:
        request = answerRequest.get_answers(question_id)
    else:
        return "질문 생성에 실패했습니다."
    
    # 만약 'error' 키가 있으면 에러 메시지 반환
    if isinstance(request, dict) and "error" in request:
        return request["error"]
    
    # 응답이 리스트인 경우 첫 번째 항목에서 content를 가져옴
    if isinstance(request, list) and len(request) > 0:
        first_item = request[0]
        if "content" in first_item:
            return first_item["content"]

    return "답변을 받을 수 없습니다."


# 로그인 페이지 구성
def login_page():
    with gr.Column():
        user_id_input = gr.Textbox(label="User_Id", placeholder="Enter your username")
        password_input = gr.Textbox(label="Password", type="password", placeholder="Enter your password")
        login_button = gr.Button("Login")
        result_output = gr.Textbox(label="Result", interactive=False)
        
        # 버튼 클릭 시 로그인 검증 실행
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

# 챗봇 페이지 구성
def chatbot_page():
    gr.ChatInterface(
        fn=response,
        title="Over Mind",
        description="질문하면 답변을 받을 수 있습니다.",
        theme="soft",
        examples=[["질문 1"], ["질문 2"], ["질문 3"]],
        retry_btn="다시보내기 ↩",
        undo_btn="이전챗 삭제 ❌",
        clear_btn="전체 채팅 💫",
        submit_btn = "Enter"
    )

# 기록 보기 페이지 구성
def history_page(chat_history):
    if chat_history:
        gr.Markdown("### Chat History")
        for question, answer in chat_history:
            gr.Markdown(f"**Q**: {question}")
            gr.Markdown(f"**A**: {answer}")
    else:
        gr.Markdown("No chat history available.")


# 페이지 선택 함수
def select_page(page):
    if page == "로그인":
        return gr.update(visible=True), gr.update(visible=False), gr.update(visible=False)
    else:
        # 로그인 상태를 확인하여 페이지 접근 허용 여부 결정
        if userRequest.check_login_status():
            if page == "챗봇":
                return gr.update(visible=False), gr.update(visible=True), gr.update(visible=False)
            elif page == "기록 보기":
                return gr.update(visible=False), gr.update(visible=False), gr.update(visible=True)
        else:
            return gr.update(visible=True), gr.update(visible=False), gr.update(visible=False)  # 로그인 페이지로 이동

# Main Interface
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

    with login_page_box:
        login_page()
    with chatbot_page_box:
        chatbot_page()
    with history_page_box:
        history_page(chat_history)

    # 버튼 클릭 시 페이지 전환 (로그인 상태 확인)
    login_btn.click(select_page, inputs=[gr.State("로그인")], outputs=[login_page_box, chatbot_page_box, history_page_box])
    chatbot_btn.click(select_page, inputs=[gr.State("챗봇")], outputs=[login_page_box, chatbot_page_box, history_page_box])
    history_btn.click(select_page, inputs=[gr.State("기록 보기")], outputs=[login_page_box, chatbot_page_box, history_page_box])

demo.launch()