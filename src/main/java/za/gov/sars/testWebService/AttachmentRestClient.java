/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.gov.sars.testWebService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

}
