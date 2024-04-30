 # 1. Authentication
 curl --location --request POST 'https://api-server.compdf.com/server/v1/oauth/token' \
 --header 'Content-Type: application/json' \
 --data-raw '{
   "publicKey": "publicKey",
   "secretKey": "secretKey"
 }'

 # 2. Create Task
curl --location --request GET 'https://api-server.compdf.com/server/v1/task/documentAI/pdf/compress?language=2' \
 --header 'Authorization: Bearer accessToken'

 # 3. Upload File
 curl --location --request POST 'https://api-server.compdf.com/server/v1/file/upload' \
 --header 'Authorization: Bearer accessToken' \
 --form 'file=@"/input_files/compare1.pdf"' \
 --form 'taskId="taskId"' \
 --form 'password=""' \
 --form 'parameter="{  \"quality\":\"50\"}"' \

 # 4. Execute Task
 curl --location --request GET 'https://api-server.compdf.com/server/v1/execute/start?taskId=taskId' \
 --header 'Authorization: Bearer accessToken'

 # 5. Get Task Information
 curl --location --request GET 'https://api-server.compdf.com/server/v1/task/taskInfo?taskId=taskId' \
 --header 'Authorization: Bearer accessToken'
