package cn.kdan.javaDemo.DocumentEditor;

import okhttp3.*;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ComPDF-Bob 2024-04-16
 **/
public class PDFCompare {

    // PDF 文件路径
    private static final String pdfPath1 = "input_files/compare1.pdf";
    private static final String pdfPath2 = "input_files/compare2.pdf";
    // 项目 public Key：您可以在 ComPDFKit API 控制台的 API Key 板块获取
    private static final String publicKey  = "public_key_d1da3ea00ec7b1aab2fa91b84050716e";
    // 项目 secret Key：您可以在 ComPDFKit API 控制台的 API Key 板块获取
    private static final String secretKey  = "secret_key_2c89f192d30195bd02e577cee49c998e";

    private static final String AUTH_URL = "http://101.132.103.13:8090/server/v1/oauth/token";
    private static final String CREATE_TASK_URL = "http://101.132.103.13:8090/server/v1/task";
    private static final String UPLOAD_FILE_URL = "http://101.132.103.13:8090/server/v1/file/upload";
    private static final String EXECUTE_TASK_URL = "http://101.132.103.13:8090/server/v1/execute/start";
    private static final String GET_TASK_INFO_URL = "http://101.132.103.13:8090/server/v1/task/taskInfo";

    public static void main(String[] args) {

        // 1.Authenticatio
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"publicKey\": \"" + publicKey + "\",\n    \"secretKey\": \"" + secretKey + "\"\n}");
        Request request = new Request.Builder()
                .url(AUTH_URL)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "keep-alive")
                .build();
        String bearerToken = "";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                System.out.println("Authenticatio Result:" + jsonObject.toString());
                String accessToken = jsonObject.getJSONObject("data").getString("accessToken");
                bearerToken = "Bearer " + accessToken;
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.println("bearerToken: " + bearerToken);

        // 2.Create Task
        request = new Request.Builder()
                .url(CREATE_TASK_URL + "/pdf/contentCompare?language=2")
                .method("GET", null)
                .addHeader("Authorization", bearerToken)
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "keep-alive")
                .build();
        String taskId = "";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                System.out.println("Create Task Result:" + jsonObject.toString());
                taskId = jsonObject.getJSONObject("data").getString("taskId");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.println("taskId: " + taskId);

        // 3.Upload File1
        InputStream inputStream1 = PDFCompare.class.getClassLoader().getResourceAsStream(pdfPath1);
        String parameter = "{ \"imgCompare\":\"1\", \"isSaveTwo\":\"0\", \"textCompare\":\"1\", \"replaceColor\":\"#FF0000\", \"insertColor\":\"#00FF00\", \"deleteColor\":\"#0000FF\"}";
        RequestBody uploadFileBody1 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "compare1.pdf", RequestBody.create(MediaType.parse("application/octet-stream"), readBytesFromInputStream(inputStream1)))
                .addFormDataPart("taskId",taskId)
                .addFormDataPart("parameter", parameter)
                .addFormDataPart("language","2")
                .build();
        Request uploadFileRequest1 = new Request.Builder()
                .url(UPLOAD_FILE_URL)
                .method("POST", uploadFileBody1)
                .addHeader("Authorization", bearerToken)
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "multipart/form-data;")
                .build();
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Response response = client.newCall(uploadFileRequest1).execute();
            if (response.body() != null) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                System.out.println("Upload File Result:" + jsonObject.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        // Upload File2
        InputStream inputStream2 = PDFCompare.class.getClassLoader().getResourceAsStream(pdfPath2);
        RequestBody uploadFileBody2 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "compare2.pdf", RequestBody.create(MediaType.parse("application/octet-stream"), readBytesFromInputStream(inputStream2)))
                .addFormDataPart("taskId",taskId)
                .addFormDataPart("parameter", parameter)
                .addFormDataPart("language","2")
                .build();
        Request uploadFileRequest2 = new Request.Builder()
                .url(UPLOAD_FILE_URL)
                .method("POST", uploadFileBody2)
                .addHeader("Authorization", bearerToken)
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "multipart/form-data;")
                .build();
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Response response = client.newCall(uploadFileRequest2).execute();
            if (response.body() != null) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                System.out.println("Upload File Result:" + jsonObject.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        // 4. Execute Task
        Request executeTaskRequest = new Request.Builder()
                .url(EXECUTE_TASK_URL + "?language=2&taskId=" + taskId)
                .method("GET", null)
                .addHeader("Authorization", bearerToken)
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "keep-alive")
                .build();
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Response response = client.newCall(executeTaskRequest).execute();
            if (response.body() != null) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                System.out.println("Execute Task Result:" + jsonObject.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        // 5. Get Task Information
        Request getTaskInfoRequest = new Request.Builder()
                .url(GET_TASK_INFO_URL + "?taskId=" + taskId)
                .method("GET", null)
                .addHeader("Authorization", bearerToken)
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "keep-alive")
                .build();
        boolean flag = true;
        while (flag) {
            try {
                Thread.sleep(1000);
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Response response = client.newCall(getTaskInfoRequest).execute();
                if (response.body() != null) {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String taskStatus = jsonObject.getJSONObject("data").getString("taskStatus");
                    if ("TaskFinish".equals(taskStatus)) {
                        System.out.println("Task Information:" + jsonObject.toString());
                        flag = false;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

    }

    // 从InputStream中读取字节
    private static byte[] readBytesFromInputStream(InputStream inputStream) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
