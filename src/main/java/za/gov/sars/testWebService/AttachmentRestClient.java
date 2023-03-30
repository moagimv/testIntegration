/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.gov.sars.testWebService;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author S2026987
 */
@Service
public class AttachmentRestClient {

    private RestTemplate restTemplate;

    private final String UPLOAD_DOCUMENT_URL = "http://10.30.5.217:9082/sars_pca/rest/sarsdocument/upload";
    private final String DOWNLOAD_DOCUMENT_URL = "http://10.30.5.217:9082/sars_pca/rest/sarsdocument/properties/{0}";

    public AttachmentRestClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(500)).setReadTimeout(Duration.ofSeconds(500)).build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
    }

    public Object getDocument(String code) {
        return restTemplate.getForObject(DOWNLOAD_DOCUMENT_URL, String.class, code);
    }

    public JsonDocumentDto getDocumentJsonValue(String code) {
        JsonDocumentDto response = restTemplate.getForObject(DOWNLOAD_DOCUMENT_URL, JsonDocumentDto.class, code);
        return response;
    }

    public ResponseEntity<String> uploadDocument(String base64Document) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_PDF);
        mediaTypes.add(MediaType.TEXT_PLAIN);
        httpHeaders.setAccept(mediaTypes);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("file", getFile());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(UPLOAD_DOCUMENT_URL, requestEntity, String.class);

        return response;
    }

    public FileSystemResource getFile() {
        return new FileSystemResource("./PCA_CI_Audit_Findings_Letter.pdf");
    }

    public String uploadRestDocument() {
        try {
            URL url = new URL(UPLOAD_DOCUMENT_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            String jsonObject = convertFileToJsonObject();
            os.write(jsonObject.getBytes("UTF-8"));
            os.close();

            // read the response 
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            //System.out.println(result); 
            System.out.println("JSON Response...........");
            JSONObject myResponse = new JSONObject(result);

            String objectId = myResponse.getString("objectId");
            //System. out. println(objectId);		

            String message = myResponse.getString("message");
            //System. out. println(message);	

            if (objectId.equals("0000000000000000")) {
                System.out.println("Failed to Upload. " + message);
            } else {
                System.out.println("Upload Successful (" + objectId + "). " + message);
            }
            
            return objectId;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getLocalizedMessage());
        }
        return null;
    }

    private String convertFileToJsonObject() {
        JsonDocumentDto jsonDocumentDto = new JsonDocumentDto();
        try {
            FileSystemResource file = getFile();
            
            jsonDocumentDto.setProperties(new Properties());
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_uuid", "hahahaha");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_part_no", "10");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_guid", "PCA");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_case_no", "1024");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_shred_ind", "0");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_application_id", "RESTService2");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_transaction_step", "0");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_parts_total", "0");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_parts_no", "10");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_is_replica", "false");
            jsonDocumentDto.getProperties().setAdditionalProperty("sars_archive_flag", "0");
            
            jsonDocumentDto.setObjectType("sars_document");
            jsonDocumentDto.setObjectName("PCA_DOCUMENT");
            jsonDocumentDto.setAuthor("WhoCares");
            jsonDocumentDto.setContentType(FilenameUtils.getExtension(file.getFilename()));
            
            Path path = Paths.get("./PCA_CI_Audit_Findings_Letter.pdf");
            
            jsonDocumentDto.setFileSize(Integer.valueOf(String.valueOf(Files.size(path))));
            
            byte[] byteData = Files.readAllBytes(path);
            //String testFile = "AndileMpelwaneTerry";
            String base64String = Base64.getEncoder().encodeToString(byteData);

            jsonDocumentDto.setContent(base64String);

            ObjectMapper mapper = new ObjectMapper();
            
            return mapper.writeValueAsString(jsonDocumentDto);
        } catch (IOException ex) {
            Logger.getLogger(AttachmentRestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
