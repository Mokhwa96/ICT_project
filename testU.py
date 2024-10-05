import gradio as gr
import AnswerRequest
import QuestionRequest

def response(message, history):
    # 답변을 조회하는 함수 호출
    QuestionRequest.create_answer(message)
    request = AnswerRequest.get_answers(1)
    
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
    gr.Textbox(label="Username", placeholder="Enter your username")
    gr.Textbox(label="Password", type="password", placeholder="Enter your password")
    gr.Button("Login")

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
        textbox=gr.Textbox(placeholder="질문해주세요", container=False, scale=7),
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
    elif page == "챗봇":
        return gr.update(visible=False), gr.update(visible=True), gr.update(visible=False)
    elif page == "기록 보기":
        return gr.update(visible=False), gr.update(visible=False), gr.update(visible=True)

# Main Interface
with gr.Blocks() as demo:
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

    # 버튼 클릭 시 페이지 전환
    login_btn.click(select_page, inputs=[gr.State("로그인")], outputs=[login_page_box, chatbot_page_box, history_page_box])
    chatbot_btn.click(select_page, inputs=[gr.State("챗봇")], outputs=[login_page_box, chatbot_page_box, history_page_box])
    history_btn.click(select_page, inputs=[gr.State("기록 보기")], outputs=[login_page_box, chatbot_page_box, history_page_box])

demo.launch()
