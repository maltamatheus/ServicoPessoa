package ws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

//Essa classe foi incluída nesse projeto apenas para fins de versionamento
//Se você escolher colocar essa classe num outro projeto, a fidelidade do teste ficará mais próxima da realidade
//Obs: Lembre-se que o Webservice em questão precisa estar em execução
public class ServicoClients { 
    public static void main(String[] args) throws SOAPException, IOException {

        String requestSoap;//requisicao/request no formato xml, repare que isto foi copiado da regiao destacada em azul na figura 1
        requestSoap =  
        		  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws/\">\r\n"
        		+ "   <soapenv:Header/>\r\n"
        		+ "   <soapenv:Body>\r\n"
        		+ "      <ws:servico01>\r\n"
        		+ "         <!--Optional:-->\r\n"
        		+ "         <arg0>\r\n"
        		+ "            <!--Optional:Numérico-->\r\n"
        		+ "            <idade>42</idade>\r\n"
        		+ "            <!--Optional:String-->\r\n"
        		+ "            <nome>Seu Nome Aqui</nome>\r\n"
        		+ "         </arg0>\r\n"
        		+ "      </ws:servico01>\r\n"
        		+ "   </soapenv:Body>\r\n"
        		+ "</soapenv:Envelope>";
        
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        
        //Aqui não é a para colocar a url do WSDL do webservice
        //Aqui deve ser colocada SOMENTE a url do webservice
        String url = "http://localhost:8080/Servico";
        
		MimeHeaders headers = new MimeHeaders();
        
		headers.addHeader("Content-Type", "text/xml");
 
        //exclua esta regiao caso o webservice nao possua a proprieade SOAPAction
        // header SOAPAction e sua respectiva url, esta url muda de webservice para webservice. 
		// Alguns webservice nao possuem esta proprieade, nestes webservice esta linha deve ser excluida
        // o valor "http://tempuri.org/CalcPrazo" foi obtido com base na região destacada em verde da figura 2.
		headers.addHeader("SOAPAction", "http://tempuri.org/CalcPrazo");
 
        //fim da regiao a ser excluida caso o webservice nao possua a proprieade SOAPAction
 
        MessageFactory messageFactory = MessageFactory.newInstance();
 
        SOAPMessage msg = messageFactory.createMessage(headers, (new ByteArrayInputStream(requestSoap.getBytes())));
 
        SOAPMessage soapResponse = soapConnection.call(msg, url);
        Document xmlRespostaARequisicao=soapResponse.getSOAPBody().getOwnerDocument();
        System.out.println(passarXMLParaString(xmlRespostaARequisicao,4));//imprime na tela o xml de retorno.
    }
    public static String passarXMLParaString(Document xml, int espacosIdentacao){
        try {
            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            transfac.setAttribute("indent-number", new Integer(espacosIdentacao));
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
 
            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(xml);
            trans.transform(source, result);
            String xmlString = sw.toString();
            return xmlString;
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}
