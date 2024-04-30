## ComPDFKit API

[ComPDFKit PDF API](https://api.compdf.com/) is organized around the REST standard and provides you with a simple document-upload, document-process, document-download workflow. Supporting various programming languages (such as [Java](https://api.compdf.com/api-libraries/in-java), [Python](https://api.compdf.com/api-libraries/in-python), JavaScript, etc.), ComPDFKit API offers rich PDF functionalities, including conversion, document editor, data extraction, and so forth. 

Before integrating the below PDF capabilities, you can register a free [ComPDFKit API](https://api.compdf.com/signup) account to process 1,000 files per month without costs and limitations. 

| [PDF to Word](https://api.compdf.com/api-reference/pdf-to-word) | [PDF to Excel](https://api.compdf.com/api-reference/pdf-to-excel) | **[PDF to PPT](https://api.compdf.com/api-reference/pdf-to-ppt)** | [PDF to HTML](https://api.compdf.com/api-reference/pdf-to-html) |
| :----------------------------------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| [PDF to RTF](https://api.compdf.com/api-reference/pdf-to-rtf) | [PDF To Image](https://api.compdf.com/api-reference/pdf-to-image) | [PDF to CSV](https://api.compdf.com/api-reference/pdf-to-csv) | [PDF to TXT](hhttps://api.compdf.com/api-reference/pdf-to-txt) |
| [PDF to JSON](https://api.compdf.com/api-reference/pdf-to-json) | [PDF to Editable PDF](https://api.compdf.com/api-reference/pdf-to-editable-pdf-tool-guide) | [Image to Word](https://api.compdf.com/api-reference/image-to-word) | [Image to Excel](https://api.compdf.com/api-reference/image-to-excel) |
| [Image to PPT](https://api.compdf.com/api-reference/image-to-ppt) | [Image to HTML](https://api.compdf.com/api-reference/image-to-html) | [Image to RTF](https://api.compdf.com/api-reference/image-to-rtf) | [Image to CSV](https://api.compdf.com/api-reference/image-to-csv) |
| [Image to TXT](https://api.compdf.com/api-reference/image-to-txt) | [Word to PDF](https://api.compdf.com/api-reference/word-to-pdf) | [Excel to PDF](https://api.compdf.com/api-reference/excel-to-pdf) | [PPT to PDF](https://api.compdf.com/api-reference/ppt-to-pdf) |
| [HTML to PDF](https://api.compdf.com/api-reference/html-to-pdf) | [RTF to PDF](https://api.compdf.com/api-reference/rtf-to-pdf) | [PNG to PDF](https://api.compdf.com/api-reference/image-to-pdf) | [CSV to PDF](https://api.compdf.com/api-reference/csv-to-pdf) |
| [TXT to PDF](https://api.compdf.com/api-reference/txt-to-pdf) | [Merge PDF](https://api.compdf.com/api-reference/merge)      | [Split PDF](https://api.compdf.com/api-reference/split)      | [Rotate PDF](https://api.compdf.com/api-reference/rotate)    |
| [Delete PDF](https://api.compdf.com/api-reference/delete)    | [Insert PDF](https://api.compdf.com/api-reference/insert)    | [Extract PDF](https://api.compdf.com/api-reference/extract)  | [Compare PDF](https://api.compdf.com/api-reference/compare-documents) |
| [OCR](https://api.compdf.com/api-reference/ocr)              | [Layout Analysis](https://api.compdf.com/api-reference/layout-analysis) | [Image Sharpening Enhancement](https://api.compdf.com/api-reference/image-processing) | [Form Recognizer](https://api.compdf.com/api-reference/form-recognizer) |
| [Trim Correction](https://api.compdf.com/api-reference/trim-correction) | [Stamp Inspection](https://api.compdf.com/api-reference/stamp-inspection) | [Add Watermark](https://api.compdf.com/api-reference/watermark-guides) | [Compression PDF](https://api.compdf.com/api-reference/compress-guides) |



## Getting Started with Code Samples

This GitHub repository provides public access to code examples that demonstrate how to programmatically submit requests to the [ComPDFKit API](https://api.compdf.com/) service.

Before you begin, you may need to do some preparatory work.

- [Register](https://api.compdf.com/signup) a free ComPDFKit API account using email only.
- Obtain the project ID and its related key from the [API Keys](https://api-dashboard.compdf.com/api/keys) section of the console.
- To start requesting the ComPDFKit API, please read the comprehensive [API reference](https://api.compdf.com/api-reference/overview) for the function you need to call.



## Instructions for Running Code Demo

### Authentication

You can get **accessToken** and related verification information by sending your **publicKey** and  **SecretKey** . AccessToken will expire after **12** hours. **When calling the subsequent interface, you must carry this token in the request header**: `Authorization: Bearer {accessToken}`.

Before running each sample program, look for a comment that reads:

> `public_key_******`
>
> ` secret_key_******`

and replace `******` with your API Keys.

### Create task

A task ID is automatically generated for you based on the type of PDF tool you choose. You can provide the callback notification URL. After the task processing is completed, we will notify you of the task result through the callback interface. You can perform other operations according to the task result, such as downloading the result file.

### Upload files

Upload the original file and bind the file to the task ID. The field **parameter** is used to pass the JSON string to set the processing parameters for the file. Each file will generate automatically a unique **filekey**. 

Please note that a maximum of five files can be uploaded for a task ID and no files can be uploaded for that task after it has started.

### Execute task and get task information

After the file is uploaded, the file processing starts and the download link of the corresponding result file is obtained according to the **filekey** of each file



## API Documentation

After you've successfully sent an API Call using these examples, take a look at the [Documentation](https://api.compdf.com/api-reference/overview) for each API endpoint for a full description of parameters you can adjust to customize your solution.



## Support

ComPDFKit has a professional R&D team that produces comprehensive technical documentation and guides to help developers. Also, you can get an immediate response when reporting your problems to our support team.

- For detailed information, please visit our [API Reference](https://api.compdf.com/api-reference/overview) page.
- Stay updated with the latest improvements through our [Changelog](https://www.compdf.com/api/changelog-compdfkit-api).
- For technical assistance, please reach out to our [Technical Support](https://www.compdf.com/support).
- To get more details and an accurate quote, please contact our [Sales Team](https://compdf.com/contact-us).



## Related

- Convert PDF to/ from other formats using [PDF online tools](https://www.compdf.com/pdf-tools).
- Experience [ComPDFKit Web Demo](https://www.compdf.com/webviewer/demo) on any browser.
- [Convert PDF to Image (JPG, PNG) with ComPDFKit API and Java](https://www.compdf.com/blog/convert-pdf-to-image-in-java-compdfkit-api)
- [Convert Excel to PDF Using Java - Free PDF Converter API](https://www.compdf.com/blog/convert-excel-to-pdf-using-java-api)
