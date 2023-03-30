/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.gov.sars.testWebService.pojo;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author S2026987
 */
public class PDFDocument {
    
    protected String content;

    @XmlElement
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
