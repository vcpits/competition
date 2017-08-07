package ru.pits.keywords.brt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pits.sshClient.SshClient;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**brt:Получение "Telnet Port*/

public class GetTelnetPort {
    Logger log = LoggerFactory.getLogger(GetTelnetPort.class);

    public String getPort() {
        try {
            return parseSSHOutput(execCommand());
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
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
