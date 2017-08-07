package ru.pits.keywords.ufm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pits.conector.DBConector;
import ru.pits.sshClient.SshClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**"UFM: Выгрузка данных по клиенту*/

public class UploadingClientData {
    Logger log = LoggerFactory.getLogger(DBConector.class);
    private String option;
    private String msisdn;
    private SshClient sshClient;
    private String sshCommand;

    public UploadingClientData(String option, String msisdn) {
        this.option = option + msisdn;
        this.msisdn = msisdn;
        this.sshClient = new SshClient("");
    }

    public void setSshCommand() {
        this.sshCommand = "/data/ufm/appufm/bin/uadmin_light.sh bis exportclient " + this.option + " -file 123";
    }

    private ByteArrayOutputStream execSSHCommand(String sshCommand) {

        try {
            return this.sshClient.execute("subs " + this.msisdn);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public Map<String, String> getResult() {
         execSSHCommand("/data/ufm/appufm/bin/uadmin_light.sh bis exportclient " + this.option + " -file "
                 + this.msisdn + ".txt");
        ByteArrayOutputStream os = execSSHCommand("cat /data/ufm/app-ufm/temp/" + this.msisdn + ".txt");
        Map<String, String> result = new HashMap<>();
        //Описание в каком виде все выдодится нет, поэтому будет заглушка.
        result.put("objId","objId");
        result.put("insertDate","insertDate");
        result.put("SUBS_ID","SUBS_ID");
        result.put("PACK_ID", "PACK_ID");
        result.put("actualDate", "actualDate");
        result.put("MSISDN", "MSISDN");

        return result;
    }
}
