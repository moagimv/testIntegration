/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.gov.sars.testWebService;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import za.gov.sars.testWebService.pojo.Answer;
import za.gov.sars.testWebService.pojo.Question;

/**
 *
 * @author S2026987
 */
@Service
public class XmlToObject {

    public void testXmlToObject() {

        try {
            File file = new File("question.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Question.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Question que = (Question) jaxbUnmarshaller.unmarshal(file);

            System.out.println(que.getId() + " " + que.getQuestionname());

            System.out.println("Answers:");

            List<Answer> list = que.getAnswers();

            for (Answer ans : list) {
                System.out.println(ans.getId() + " " + ans.getAnswername() + "  " + ans.getPostedby());
            }
        } catch (JAXBException ex) {

        }
    }
}
