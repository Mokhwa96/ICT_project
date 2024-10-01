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
    

if __name__ == "__main__":
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
    ).launch()

