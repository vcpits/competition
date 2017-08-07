package competetion.src.main.java.ru.pits.keywords.brt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import ru.pits.sshClient.SshClient;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**brt:Получение "Telnet Port*/

public class GetTelnetPort {
    Logger log = LoggerFactory.getLogger(GetTelnetPort.class);

    private String port;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String returnResult() throws IOException {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        SshClient sshClient = new SshClient("");
        sshClient.execute("sudo –u brt -i");
        ByteArrayOutputStream execute = sshClient.execute("cat /data/brt/BRT/brt-conf/config.xml");
        Document dom = builder.parse(new ByteArrayInputStream(execute));
        return dom.getElementsByTagName("Telnet Port").item(0).getTextContent() ;
    }
}
