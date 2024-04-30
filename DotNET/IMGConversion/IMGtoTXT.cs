using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace PDFConversion
{
    class IMGtoTXT
    {
        // PDF 文件路径
        private static readonly string pdfPath = "input_files/test.png";
        // 项目 public Key：您可以在 ComPDFKit API 控制台的 API Key 板块获取
        private static readonly string publicKey = "public_key_******";
        // 项目 secret Key：您可以在 ComPDFKit API 控制台的 API Key 板块获取
        private static readonly string secretKey = "secret_key_******";

        private static readonly string AUTH_URL = "https://api-server.compdf.com/server/v1/oauth/token";
        private static readonly string CREATE_TASK_URL = "https://api-server.compdf.com/server/v1/task";
        private static readonly string UPLOAD_FILE_URL = "https://api-server.compdf.com/server/v1/file/upload";
        private static readonly string EXECUTE_TASK_URL = "https://api-server.compdf.com/server/v1/execute/start";
        private static readonly string GET_TASK_INFO_URL = "https://api-server.compdf.com/server/v1/task/taskInfo";

        static async Task Main(string[] args)
        {
            // 1. Authentication
            using (var client = new HttpClient())
            {
                client.DefaultRequestHeaders.TryAddWithoutValidation("Content-Type", "application/json");
                var tokenParam = new Dictionary<string, string>
                {
                    ["publicKey"] = publicKey,
                    ["secretKey"] = secretKey
                };
                
                var responseEntity = client.PostAsync(AUTH_URL, new StringContent(JsonConvert.SerializeObject(tokenParam), Encoding.UTF8, "application/json")).Result;

                var authResponseString = responseEntity.Content.ReadAsStringAsync().Result;
                var result = JsonConvert.DeserializeObject(authResponseString) as Newtonsoft.Json.Linq.JObject;
                if(result == null)
                {
                    Console.WriteLine("Failed to get ComPDFKit Token");
                    return;
                }
                string bearerToken = string.Empty;
                if (result.TryGetValue("data", out var data1))
                {
                    var dataObject1 = data1 as Newtonsoft.Json.Linq.JObject;
                    if (dataObject1 != null && dataObject1.TryGetValue("accessToken", out var accessToken))
                    {
                        bearerToken = accessToken.ToString();
                    }
                    else
                    {
                        Console.WriteLine("Failed to get accessToken from data.");
                    }
                }
                else
                {
                    Console.WriteLine("Failed to get data from result.");
                }

                Console.WriteLine($"bearerToken: {bearerToken}");

                // 2. Create Task
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", bearerToken);
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("*/*"));
                client.DefaultRequestHeaders.Connection.Add("keep-alive");
                
                var createTaskResponse = client.GetAsync($"{CREATE_TASK_URL}/img/txt?language=2").Result;
                var createTaskResponseString = createTaskResponse.Content.ReadAsStringAsync().Result;
                var createTaskJsonObject = JsonConvert.DeserializeObject(createTaskResponseString) as Newtonsoft.Json.Linq.JObject;
                if(createTaskJsonObject == null)
                {
                    Console.WriteLine("Failed to create task");
                    return;
                }

                string taskId;
                if (createTaskJsonObject.TryGetValue("data", out var dataObject))
                {
                    var data = dataObject as Newtonsoft.Json.Linq.JObject;
                    if (data != null && data.TryGetValue("taskId", out var taskIdObject))
                    {
                        taskId = taskIdObject.ToString();
                    }
                    else
                    {
                        Console.WriteLine("Failed to get taskId from data.");
                        return;
                    }
                }
                else
                {
                    Console.WriteLine("Failed to get data from createTaskJsonObject.");
                    return;
                }

                // 3. Upload File
                var fileContent = new StreamContent(File.OpenRead(pdfPath));
                var formData = new MultipartFormDataContent();
                formData.Add(fileContent, "file", Path.GetFileName(pdfPath));
                formData.Add(new StringContent(taskId), "taskId");
                formData.Add(new StringContent("{    \"isAllowOcr\":1,\"isContainOcrBg\":0 }"), "parameter");
                formData.Add(new StringContent("2"), "language");

                var uploadFileResponse = client.PostAsync(UPLOAD_FILE_URL, formData).Result;
                if (!uploadFileResponse.IsSuccessStatusCode)
                {
                    Console.WriteLine($"Upload File Failed: {uploadFileResponse.StatusCode}, {uploadFileResponse.ReasonPhrase}");
                    return;
                }
                var uploadFileResponseString = uploadFileResponse.Content.ReadAsStringAsync().Result;
                Console.WriteLine($"Upload File Result: {uploadFileResponseString}");

                // 4. Execute Task
                var executeTaskResponse = client.GetAsync($"{EXECUTE_TASK_URL}?language=2&taskId={taskId}").Result;
                var executeTaskResponseString = executeTaskResponse.Content.ReadAsStringAsync().Result;
                Console.WriteLine($"Execute Task Result: {executeTaskResponseString}");

                // 5. Get Task Information
                bool flag = true;
                while (flag)
                {
                    await Task.Delay(1000);
                    var getTaskInfoResponse = client.GetAsync($"{GET_TASK_INFO_URL}?taskId={taskId}").Result;
                    var getTaskInfoResponseString = getTaskInfoResponse.Content.ReadAsStringAsync().Result;
                    dynamic getTaskInfoJsonObject = Newtonsoft.Json.JsonConvert.DeserializeObject(getTaskInfoResponseString);
                    string taskStatus = getTaskInfoJsonObject.data.taskStatus;
                    if (taskStatus == "TaskFinish")
                    {
                        Console.WriteLine($"Task Information: {getTaskInfoResponseString}");
                        flag = false;
                    }
                }
            }
        }
    }
}
