import gradio as gr

# 예시 데이터
data = [
    [1, "질문1", "답변1", "2024-10-13 14:00:00", "user1", "O"],
    [2, "질문2", "답변2", "2024-10-13 14:00:00", "user2", "O"],
    [3, "질문3", "답변3", "2024-10-13 14:00:00", "user3", "O"],
    [4, "질문4", "답변4", "2024-10-13 14:30:00", "user1", "O"],
]

# 필터링된 데이터를 저장할 전역 변수
filtered_data = []

# 특정 user_id의 기록만 필터링하는 함수
def fetch_chat_records(user_id):
    global filtered_data
    filtered_data = [record for record in data if record[4] == user_id]
    return filtered_data

# 기록을 수정하는 함수
def modify_chat_record(record_id, new_question, new_answer):
    for record in data:
        if record[0] == record_id:
            record[1] = new_question
            record[2] = new_answer
            return f"Record with ID {record_id} updated successfully."
    return "Record not found."

# 기록을 삭제하는 함수
def delete_chat_record(record_id):
    global data
    # 해당 record_id의 데이터를 삭제
    data = [record for record in data if record[0] != record_id]
    return f"Record with ID {record_id} deleted successfully."

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
    with gr.Blocks() :
        # 기록 확인 및 수정 화면
        with gr.Column():
            gr.Markdown("### 챗봇 기록")

            # user_id 입력 텍스트 박스 추가
            user_id_input = gr.Textbox(label="User ID")

            # 기록 표시용 데이터프레임
            chat_records = gr.DataFrame(headers=["Record_ID", "질문", "답변", "답변 시간", "유저 ID", "답변 여부"], interactive=False)

            # 기록 가져오기 버튼
            fetch_button = gr.Button("기록 불러오기")
            fetch_button.click(fetch_chat_records, inputs=user_id_input, outputs=chat_records)

        # 기록 수정 및 삭제 섹션
        with gr.Column():
            gr.Markdown("### 기록 수정 및 삭제")
            record_id = gr.Number(label="Record ID", interactive=False)  # 수정할 수 없게 설정
            selected_question = gr.Textbox(label="Question")
            selected_answer = gr.Textbox(label="Answer")

            # 수정 및 삭제 버튼을 같은 행에 배치
            with gr.Row():
                update_button = gr.Button("수정하기")
                delete_button = gr.Button("삭제하기")

            # 수정 및 삭제 결과를 출력할 텍스트 박스 하나로 통합
            result_box = gr.Textbox(label="Result")

            # DataFrame 행 클릭 시 해당 데이터를 텍스트 박스에 채워 넣음
            chat_records.select(handle_select, outputs=[record_id, selected_question, selected_answer])

            # 수정 버튼 클릭 시 기록 수정 함수 호출
            update_button.click(modify_chat_record, [record_id, selected_question, selected_answer], outputs=result_box)

            # 삭제 버튼 클릭 시 기록 삭제 함수 호출
            delete_button.click(delete_chat_record, inputs=record_id, outputs=result_box)
