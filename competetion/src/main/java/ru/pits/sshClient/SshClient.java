package ru.pits.sshClient;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SshClient {
    Logger log = LoggerFactory.getLogger(SshClient.class);

    String port;
    String host;

    public SshClient(String port) {
        this.port = port;
        if(!port.isEmpty())
            this.host = "localhost:" + port;
        else
            this.host = "localhost";
    }

    public ByteArrayOutputStream execute(String command) throws IOException{
        final SSHClient ssh = new SSHClient();
        ssh.loadKnownHosts();

        ssh.connect(this.host);
        try {
            ssh.authPassword("login", "password");
            final Session session = ssh.startSession();
            try {
                final Session.Command cmd = session.exec(command);
                ByteArrayOutputStream outputStream = IOUtils.readFully(cmd.getInputStream());
                return outputStream;
            } finally {
                session.close();
            }
        } finally {
            ssh.disconnect();
        }
    }
}
