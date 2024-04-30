<?php

// PDF 文件路径
$pdfPath1 = "compare1.pdf";
$pdfPath2 = "compare2.pdf";
// 项目 public Key：您可以在 ComPDFKit API 控制台的 API Key 板块获取
$publicKey  = "public_key_******";
// 项目 secret Key：您可以在 ComPDFKit API 控制台的 API Key 板块获取
$secretKey  = "secret_key_******";

const AUTH_URL = "https://api-server.compdf.com/server/v1/oauth/token";
const CREATE_TASK_URL = "https://api-server.compdf.com/server/v1/task";
const UPLOAD_FILE_URL = "https://api-server.compdf.com/server/v1/file/upload";
const EXECUTE_TASK_URL = "https://api-server.compdf.com/server/v1/execute/start";
const GET_TASK_INFO_URL = "https://api-server.compdf.com/server/v1/task/taskInfo";

// 1.Authentication
$params = [
    'publicKey' => $publicKey,
    'secretKey' => $secretKey
];
$headers = ['Content-Type: application/json'];
$curl = curl_init();
curl_setopt_array($curl, array(
    CURLOPT_URL => AUTH_URL,
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_ENCODING => '',
    CURLOPT_MAXREDIRS => 10,
    CURLOPT_TIMEOUT => 0,
    CURLOPT_FOLLOWLOCATION => true,
    CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
    CURLOPT_CUSTOMREQUEST => 'POST',
    CURLOPT_HTTPHEADER => $headers,
    CURLOPT_POSTFIELDS => json_encode($params)
));
$response = curl_exec($curl);
curl_close($curl);
$result = json_decode($response, true);
$accessToken = $result['data']['accessToken'];
$bearerToken = "Bearer $accessToken";
echo "bearerToken: $bearerToken\n";

// 2.Create Task
$headers = [
    'Content-Type: application/json',
    'Authorization: ' . $bearerToken
];
$curl = curl_init();
curl_setopt_array($curl, array(
    CURLOPT_URL => CREATE_TASK_URL . '/pdf/contentCompare?language=2',
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_ENCODING => '',
    CURLOPT_MAXREDIRS => 10,
    CURLOPT_TIMEOUT => 0,
    CURLOPT_FOLLOWLOCATION => true,
    CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
    CURLOPT_CUSTOMREQUEST => 'GET',
    CURLOPT_HTTPHEADER => $headers,
));
$response = curl_exec($curl);
curl_close($curl);
$result = json_decode($response, true);
$taskId = $result['data']['taskId'];
echo "taskId：$taskId\n";

// 3.Upload File1
$params = [
    'taskId' => $taskId, //任务ID
    'file' => new CURLFile($pdfPath1), //文件
    'parameter' => json_encode([ 'imgCompare' => '1', 'isSaveTwo' => '0', 'textCompare' => '1', 'replaceColor' => '#FF0000', 'insertColor' => '#00FF00', 'deleteColor' => '#0000FF' ]),
    'language' => 2,
];
$headers = [
    'Authorization: ' . $bearerToken
];
$curl = curl_init();
curl_setopt_array($curl, array(
    CURLOPT_URL => UPLOAD_FILE_URL,
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_ENCODING => '',
    CURLOPT_MAXREDIRS => 10,
    CURLOPT_TIMEOUT => 0,
    CURLOPT_FOLLOWLOCATION => true,
    CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
    CURLOPT_CUSTOMREQUEST => 'POST',
    CURLOPT_HTTPHEADER => $headers,
    CURLOPT_POSTFIELDS => $params
));
$response = curl_exec($curl);
curl_close($curl);
$result = json_decode($response, true);
$fileKey = $result['data']['fileKey'];
echo "Upload File1 Result：$response\n";

// Upload File2
$params = [
    'taskId' => $taskId, //任务ID
    'file' => new CURLFile($pdfPath2), //文件
    'parameter' => json_encode([ 'imgCompare' => '1', 'isSaveTwo' => '0', 'textCompare' => '1', 'replaceColor' => '#FF0000', 'insertColor' => '#00FF00', 'deleteColor' => '#0000FF' ]),
    'language' => 2,
];
$headers = [
    'Authorization: ' . $bearerToken
];
$curl = curl_init();
curl_setopt_array($curl, array(
    CURLOPT_URL => UPLOAD_FILE_URL,
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_ENCODING => '',
    CURLOPT_MAXREDIRS => 10,
    CURLOPT_TIMEOUT => 0,
    CURLOPT_FOLLOWLOCATION => true,
    CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
    CURLOPT_CUSTOMREQUEST => 'POST',
    CURLOPT_HTTPHEADER => $headers,
    CURLOPT_POSTFIELDS => $params
));
$response = curl_exec($curl);
curl_close($curl);
$result = json_decode($response, true);
$fileKey = $result['data']['fileKey'];
echo "Upload File2 Result：$response\n";

// 4. Execute Task
$headers = [
    'Content-Type: application/json',
    'Authorization: ' . $bearerToken
];
$curl = curl_init();
curl_setopt_array($curl, array(
    CURLOPT_URL => EXECUTE_TASK_URL . '?language=2&taskId=' . $taskId,
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_ENCODING => '',
    CURLOPT_MAXREDIRS => 10,
    CURLOPT_TIMEOUT => 0,
    CURLOPT_FOLLOWLOCATION => true,
    CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
    CURLOPT_CUSTOMREQUEST => 'GET',
    CURLOPT_HTTPHEADER => $headers,
));
$response = curl_exec($curl);
curl_close($curl);
echo "Execute Task Result: $response\n";

// 5. Get Task Information
$headers = [
    'Content-Type: application/json',
    'Authorization: ' . $bearerToken
];

while (true){
    sleep(10);
    $curl = curl_init();
    curl_setopt_array($curl, array(
        CURLOPT_URL => GET_TASK_INFO_URL . '?taskId=' . $taskId,
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_ENCODING => '',
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 0,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
        CURLOPT_CUSTOMREQUEST => 'GET',
        CURLOPT_HTTPHEADER => $headers,
    ));
    $response = curl_exec($curl);
    curl_close($curl);
    $result = json_decode($response, true);
    if($result['data']['taskStatus'] == 'TaskFinish'){
        echo "Task Information: $response\n";
        break;
    }
}