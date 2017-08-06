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

    public void execute(String command) throws IOException{
        final SSHClient ssh = new SSHClient();
        ssh.loadKnownHosts();

        ssh.connect("localhost");
        try {
            ssh.authPassword("login", "password");
            final Session session = ssh.startSession();
            try {
                final Session.Command cmd = session.exec(command);
                ByteArrayOutputStream outputStream = IOUtils.readFully(cmd.getInputStream());

            } finally {
                session.close();
            }
        } finally {
            ssh.disconnect();
        }
    }
}
