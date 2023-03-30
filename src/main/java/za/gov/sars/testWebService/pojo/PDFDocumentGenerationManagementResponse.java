/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.gov.sars.testWebService.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author S2026987
 */
@XmlRootElement(name = "PDFDocumentGenerationManagementResponse", namespace = "http://www.sars.gov.za/enterpriseMessagingModel/PDFDocumentGenerationManagement/xml/schemas/version/1.0")
public class PDFDocumentGenerationManagementResponse {
    
    protected PDFDocuments pdfDocuments;

    @XmlElement
    public PDFDocuments getPdfDocuments() {
        return pdfDocuments;
    }

    public void setPdfDocuments(PDFDocuments pdfDocuments) {
        this.pdfDocuments = pdfDocuments;
    }

}
