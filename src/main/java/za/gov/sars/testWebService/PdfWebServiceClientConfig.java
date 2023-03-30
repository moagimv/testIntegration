/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.gov.sars.testWebService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 *
 * @author S2026987
 */
@Configuration
public class PdfWebServiceClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("za.gov.sars.testWebService.gen");
        return marshaller;
    }

    @Bean
    public PdfWebServiceClient pdfWebServiceClient(Jaxb2Marshaller marshaller) {
        PdfWebServiceClient pdfWebServiceClient = new PdfWebServiceClient();
        pdfWebServiceClient.setDefaultUri("http://ptadviis06:90/pdfwebservice/Service1.asmx");
        pdfWebServiceClient.setMarshaller(marshaller);
        pdfWebServiceClient.setUnmarshaller(marshaller);
        return pdfWebServiceClient;
    }
}
