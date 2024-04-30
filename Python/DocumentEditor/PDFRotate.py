import requests
import time

pdf_path = "input_files/file.pdf"
public_key = "public_key_******"
secret_key = "secret_key_******"

AUTH_URL = "https://api-server.compdf.com/server/v1/oauth/token"
CREATE_TASK_URL = "https://api-server.compdf.com/server/v1/task"
UPLOAD_FILE_URL = "https://api-server.compdf.com/server/v1/file/upload"
EXECUTE_TASK_URL = "https://api-server.compdf.com/server/v1/execute/start"
GET_TASK_INFO_URL = "https://api-server.compdf.com/server/v1/task/taskInfo"

# 1. Authentication
auth_payload = {"publicKey": public_key, "secretKey": secret_key}
auth_response = requests.post(AUTH_URL, json=auth_payload)
auth_data = auth_response.json()
bearer_token = "Bearer " + auth_data["data"]["accessToken"]
print("bearerToken:", bearer_token)

# 2. Create Task
create_task_response = requests.get(CREATE_TASK_URL + "/pdf/rotation?language=2", headers={"Authorization": bearer_token})
create_task_data = create_task_response.json()
task_id = create_task_data["data"]["taskId"]
print("taskId:", task_id)

# 3. Upload File
with open(pdf_path, "rb") as file:
    files = {
        'file': ('file.pdf', file, 'application/octet-stream'),
    }
    upload_payload = {
        "taskId": task_id,
        "parameter": '{ "pageOptions": "[\'2\']", "rotation":"90" }',
        "language": "2"
    }
    upload_file_response = requests.post(UPLOAD_FILE_URL, files=files, data=upload_payload, headers={"Authorization": bearer_token})
    print("Upload File Result:", upload_file_response.json())

# 4. Execute Task
execute_task_response = requests.get(EXECUTE_TASK_URL + "?language=2&taskId=" + task_id, headers={"Authorization": bearer_token})
print("Execute Task Result:", execute_task_response.json())

# 5. Get Task Information
flag = True
while flag:
    time.sleep(1)
    task_info_response = requests.get(GET_TASK_INFO_URL + "?taskId=" + task_id, headers={"Authorization": bearer_token})
    task_info_data = task_info_response.json()
    task_status = task_info_data["data"]["taskStatus"]
    if task_status == "TaskFinish":
        print("Task Information:", task_info_data)
        flag = False