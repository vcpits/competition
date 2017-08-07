package ru.pits.keywords.brt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pits.sshClient.SshClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**brt: Получение данных по пакетам
 Абонента*/


public class GetAbonentPackData {
    Logger log = LoggerFactory.getLogger(SshClient.class);

    private String port;
    private String subsId;

    SshClient sshclient;

    public GetAbonentPackData(String port, String subsId) {
        this.subsId = subsId;
        this.port = port;
        this.sshclient = new SshClient(this.port);
    }

    private ByteArrayOutputStream execSSHCommand() {
        try {
           return sshclient.execute("subs " + this.subsId);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public Map<String, String> getResult() {
        ByteArrayOutputStream os = execSSHCommand();
        Map<String, String> result = new HashMap<>();
        //Описание в каком виде все выдодится нет, поэтому будет заглушка.
        result.put("subs","subsId");
        result.put("pack","packId");
        result.put("trace_number","TRACE_NUMBER");
        result.put("start", "START_DATE");
        result.put("end", "END_DATE");

        return result;
    }

}
