/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package za.gov.sars.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import za.gov.sars.testWebService.AttachmentRestClient;
import za.gov.sars.testWebService.JsonDocumentDto;
import za.gov.sars.testWebService.PdfWebServiceClient;
import za.gov.sars.testWebService.TestDataSourceConfiguration;
import za.gov.sars.testWebService.XmlToObject;
import za.gov.sars.testWebService.pojo.PDFDocumentGenerationManagementResponse;

/**
 *
 * @author S2026987
 */
@EnableJpaAuditing
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfiguration.class, RestTemplateAutoConfiguration.class})
public class WebServiceTestCase {

    @Autowired
    private AttachmentRestClient attachmentRestClient;
    
    @Autowired
    private XmlToObject xmlToObject;
    
    @Autowired
    private PdfWebServiceClient pdfWebServiceClient;

    public WebServiceTestCase() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    @Ignore
    public void testA() {
        //JsonDocumentDto details = attachmentRestClient.getDocumentJsonValue("090002088014ad41"); //PDF
        //JsonDocumentDto details = attachmentRestClient.getDocumentJsonValue("090002088014acf7"); //TXT
        JsonDocumentDto details1 = attachmentRestClient.getDocumentJsonValue("090002088014acf9"); //XML
        //System.out.println("Response Details: " + details.getContent());090002088014acf7
        File file1 = new File(details1.getObjectName());

        try ( FileOutputStream fos = new FileOutputStream(file1);) {
            // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file
            String b64 = details1.getContent();
            byte[] decoder = Base64.getDecoder().decode(b64);

            fos.write(decoder);
            System.out.println("The " + details1.getObjectName() + " File has been Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JsonDocumentDto details2 = attachmentRestClient.getDocumentJsonValue("090002088014ad41"); //PDF
        
        File file2 = new File(details2.getObjectName());

        try ( FileOutputStream fos = new FileOutputStream(file2);) {
            // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file
            String b64 = details2.getContent();
            byte[] decoder = Base64.getDecoder().decode(b64);

            fos.write(decoder);
            System.out.println("The " + details2.getObjectName() + " File has been Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JsonDocumentDto details3 = attachmentRestClient.getDocumentJsonValue("090002088014acf7"); //TXT
        
        File file3 = new File(details3.getObjectName());

        try ( FileOutputStream fos = new FileOutputStream(file3);) {
            // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file
            String b64 = details3.getContent();
            byte[] decoder = Base64.getDecoder().decode(b64);

            fos.write(decoder);
            System.out.println("The " + details3.getObjectName() + " File has been Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    @Ignore
    public void testB() {
        ResponseEntity<String> details = attachmentRestClient.uploadDocument("");
        System.out.println("upload Results: " + details.getStatusCode());
    }
    
    @Test
    @Ignore
    public void testC(){
        String objectId = attachmentRestClient.uploadRestDocument();
        System.out.println("upload Results: " + objectId);
    }
    
    @Test
    public void testD(){
        System.out.println("Read Sample XML Begins");
        xmlToObject.testXmlToObject();
         System.out.println("Read Sample XML End");
    }
    
    @Test
    public void testE(){
        try {
            System.out.println("Read Letter Generation XML Begins");
            File file = new File("LetterGenerated.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(PDFDocumentGenerationManagementResponse.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PDFDocumentGenerationManagementResponse managementResponse = (PDFDocumentGenerationManagementResponse) jaxbUnmarshaller.unmarshal(file);
            System.out.println("Generated PDF Content: " + managementResponse.getPdfDocuments());
            
            System.out.println("Read Letter Generation XML End");
        } catch (JAXBException ex) {
            Logger.getLogger(WebServiceTestCase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
