package ru.pits.keywords.brt;

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

    public String getPort() {
        return parseSSHOutput(execCommand());
    }

    private ByteArrayOutputStream execCommand() throws IOException {
        SshClient sshClient = new SshClient("");
        sshClient.execute("sudo –u brt -i");
        ByteArrayOutputStream execute = sshClient.execute("cat /data/brt/BRT/brt-conf/config.xml");

        return sshClient.execute("cat /data/brt/BRT/brt-conf/config.xml");
    }

    private String parseSSHOutput(ByteArrayOutputStream streamSSh) {//парсилка для потока из ssh пока заглушка
        return "";
    }
}
