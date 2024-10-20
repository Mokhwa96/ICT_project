import requests
import gradio as gr

# API URL 설정
API_URL = "http://localhost:8080/questions/user-records" # 기록 불러오기
UPDATE_URL = "http://localhost:8080/questions/update" # 기록 수정하기
DELETE_URL = "http://localhost:8080/questions/delete" # 기록 삭제하기


# 필터링된 데이터를 저장할 전역 변수
filtered_data = []

# 특정 user_id의 기록만 필터링하는 함수
def fetch_chat_records(user_id):
    global filtered_data
    try:
        # GET 요청을 보냅니다.
        response = requests.get(API_URL, params={'user_id': user_id})
        response.raise_for_status()  # 요청이 성공했는지 확인

        # 응답 데이터를 JSON 형식으로 파싱
        records = response.json()

        # Gradio의 데이터프레임 형식에 맞게 데이터 변환
        filtered_data = [
            [
                record["recordId"],
                record["question"],
                record["answer"],
                record["answerTime"] if record["answerTime"] else "No time",
                record["userId"],
                "O" if record["answered"] else "X"
            ]
            for record in records
        ]

        return filtered_data

    except requests.exceptions.RequestException as e:
        print(f"Error fetching data: {e}")
        return []  # 오류가 발생한 경우 빈 리스트 반환

# 기록을 수정하는 함수
def modify_chat_record(record_id, new_question, new_answer):
    try:
        response = requests.put(
            UPDATE_URL,
            json={
                "recordId": record_id,
                "question": new_question,
                "answer": new_answer
            }
        )
        response.raise_for_status()
        return f"Record with ID {record_id} updated successfully."
    except requests.exceptions.RequestException as e:
        print(f"Error updating data: {e}")
        return f"Error updating record with ID {record_id}: {e}"


# 기록을 삭제하는 함수
def delete_chat_record(record_id):
    try:
        response = requests.delete(DELETE_URL, params={'recordId': record_id})
        response.raise_for_status()
        return f"Record with ID {record_id} deleted successfully."
    except requests.exceptions.RequestException as e:
        print(f"Error deleting data: {e}")
        return f"Error deleting record with ID {record_id}: {e}"

# 선택된 데이터 행을 텍스트 박스에 넣기 위한 함수
def handle_select(evt: gr.SelectData):
    # 선택된 행의 인덱스만 사용하여 필터링된 데이터에서 데이터 추출
    row_index = evt.index[0]
    
    # 필터링된 데이터에서 선택된 행의 데이터를 추출
    selected_record = filtered_data[row_index]
    record_id_value = selected_record[0]  # ID 값
    question_value = selected_record[1]
    answer_value = selected_record[2]
    
    return record_id_value, question_value, answer_value

# Gradio 인터페이스 설정
def history_Page(chat_history):
    with gr.Blocks():
        with gr.Column():
            gr.Markdown("### 챗봇 기록")
            user_id_input = gr.Textbox(label="User ID")
            chat_records = gr.DataFrame(headers=["Record_ID", "질문", "답변", "답변 시간", "유저 ID", "답변 여부"], interactive=False)
            fetch_button = gr.Button("기록 불러오기")
            fetch_button.click(fetch_chat_records, inputs=user_id_input, outputs=chat_records)

        with gr.Column():
            gr.Markdown("### 기록 수정 및 삭제")
            record_id = gr.Number(label="Record ID", interactive=False)
            selected_question = gr.Textbox(label="Question")
            selected_answer = gr.Textbox(label="Answer")
            with gr.Row():
                update_button = gr.Button("수정하기")
                delete_button = gr.Button("삭제하기")
            result_box = gr.Textbox(label="Result")
            chat_records.select(handle_select, outputs=[record_id, selected_question, selected_answer])
            update_button.click(modify_chat_record, [record_id, selected_question, selected_answer], outputs=result_box)
            delete_button.click(delete_chat_record, inputs=record_id, outputs=result_box)
