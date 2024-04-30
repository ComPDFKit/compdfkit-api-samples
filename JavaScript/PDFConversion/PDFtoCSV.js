/**
 * Upload file form
 *
<form id="uploadForm" encType="multipart/form-data" method="post">
  <input type="file" id="pdfFileInput" name="pdfFile">
    <button type="submit">Upload PDF</button>
</form>
 *
 */

async function authenticate() {
  const authResponse = await fetch('https://api-server.compdf.com/server/v1/oauth/token', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      publicKey: 'public_key_******',
      secretKey: 'secret_key_******'
    })
  });
  const authData = await authResponse.json();
  return authData.data.accessToken;
}

document.getElementById('uploadForm').addEventListener('submit', async function (event) {
  event.preventDefault();

  // 1.Authenticatio
  const accessToken = await authenticate();

  // 2.Create Task
  const createTaskResponse = await fetch('https://api-server.compdf.com/server/v1/task/pdf/csv?language=2', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${accessToken}`,
    }
  });
  const createTaskData = await createTaskResponse.json();
  const taskId = createTaskData.data.taskId;

  // 3.Upload File
  const pdfFile = document.getElementById('pdfFileInput').files[0];
  if (!pdfFile) {
    alert('Please select a PDF file to upload.');
    return;
  }
  const pdfData = new FormData();
  pdfData.append('file', pdfFile, pdfFile.name);
  pdfData.append('taskId', taskId);
  pdfData.append('parameter', '{  "isCsvMerge": "1" }');
  pdfData.append('language', '2');
  const uploadFileResponse = await fetch('https://api-server.compdf.com/server/v1/file/upload', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${accessToken}`,
    },
    body: pdfData
  });
  const uploadFileData = await uploadFileResponse.json();
  console.log('File uploaded successfully.');

  // 4. Execute Task
  const executeTaskResponse = await fetch(`https://api-server.compdf.com/server/v1/execute/start?language=2&taskId=${taskId}`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${accessToken}`,
    }
  });
  const executeTaskData = await executeTaskResponse.json();
  console.log('Task execution started.');

  // 5. Get Task Information
  let taskCompleted = false;
  while (!taskCompleted) {
    await new Promise(resolve => setTimeout(resolve, 1000));
    const taskInfoResponse = await fetch(`https://api-server.compdf.com/server/v1/task/taskInfo?taskId=${taskId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${accessToken}`,
      }
    });
    const taskInfoData = await taskInfoResponse.json();
    if (taskInfoData.data.taskStatus === 'TaskFinish') {
      taskCompleted = true;
      console.log('Task Information:', taskInfoData);
    }
  }
  console.log('Task completed.');
});