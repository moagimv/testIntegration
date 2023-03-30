/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.gov.sars.testWebService;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import za.gov.sars.testWebService.gen.GeneratePdf;
import za.gov.sars.testWebService.gen.GeneratePdfResponse;

/**
 *
 * @author S2026987
 */
@Component
public class PdfWebServiceClient extends WebServiceGatewaySupport {

    public GeneratePdfResponse getPdfDocument(String requestString, String code, String soapAction) {
        GeneratePdf request = new GeneratePdf();
        request.setGenerateRequest(requestString);
        request.setCode(code);

        GeneratePdfResponse response = (GeneratePdfResponse) getWebServiceTemplate().marshalSendAndReceive(request,new SoapActionCallback(soapAction));
        //PDFDocumentGenerationResponseStructure response = (PDFDocumentGenerationResponseStructure) getWebServiceTemplate().marshalSendAndReceive(request,new SoapActionCallback(soapAction));
        return response;
    }
}
