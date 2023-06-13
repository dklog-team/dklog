package kr.dklog.service;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileTransferService {

    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.port}")
    private Integer port;

    @Value("${sftp.username}")
    private String username;

    @Value("${sftp.password}")
    private String password;

    @Value("${sftp.sessionTimeout}")
    private Integer sessionTimeout;

    @Value("${sftp.channelTimeout}")
    private Integer channelTimeout;

    public boolean uploadFile(String localFilePath, String remoteFilePath) {
        ChannelSftp channelSftp = createChannelSftp();

        try {
            channelSftp.put(localFilePath, remoteFilePath);
            return true;
        } catch(SftpException ex) {
            log.error("Error upload file", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }

        return false;
    }

    private ChannelSftp createChannelSftp() {
        log.info("data: {}, {}, {}, {}", username, host, port, password);

        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect(sessionTimeout);
            Channel channel = session.openChannel("sftp");
            channel.connect(channelTimeout);
            return (ChannelSftp) channel;
        } catch(JSchException ex) {
            log.error("Create ChannelSftp error", ex);
        }

        return null;
    }

    private void disconnectChannelSftp(ChannelSftp channelSftp) {
        try {
            if( channelSftp == null)
                return;

            if(channelSftp.isConnected())
                channelSftp.disconnect();

            if(channelSftp.getSession() != null)
                channelSftp.getSession().disconnect();

        } catch(Exception ex) {
            log.error("SFTP disconnect error", ex);
        }
    }
}
