package com.jim.msg.push.commons.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/**
 * 启动时记录pid 和 port 启动脚本时需要的话可以用
 */
public class ApplicationPortFileWriter implements ApplicationListener<ApplicationStartedEvent>{

    private static final Log logger = LogFactory.getLog(ApplicationPortFileWriter.class);

    private static final String DEFAULT_FILE_NAME = "application.port";

    private File file;

    private String port;

    private static final PosixFilePermission[] WRITE_PERMISSIONS = {
            PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_WRITE,
            PosixFilePermission.OTHERS_WRITE };

    public ApplicationPortFileWriter(String fileName){
        this(new File(fileName));
    }

    public ApplicationPortFileWriter(File file){
        this.file = file;
    }

    public ApplicationPortFileWriter(){
        this(DEFAULT_FILE_NAME);
    }


    private void writePortFile(String port) throws IOException {
        File portFile = this.file;
        createParentFolder(file);
        if (file.exists()) {
            assertCanOverwrite(file);
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.append(this.port);
        }

        portFile.deleteOnExit();
    }

    private void createParentFolder(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
    }

    private boolean canWritePosixFile(File file) throws IOException {
        try {
            Set<PosixFilePermission> permissions = Files
                    .getPosixFilePermissions(file.toPath());
            for (PosixFilePermission permission : WRITE_PERMISSIONS) {
                if (permissions.contains(permission)) {
                    return true;
                }
            }
            return false;
        }
        catch (UnsupportedOperationException ex) {
            // Assume that we can
            return true;
        }
    }

    private void assertCanOverwrite(File file) throws IOException {
        if (!file.canWrite() || !canWritePosixFile(file)) {
            throw new FileNotFoundException(file.toString() + " (permission denied)");
        }
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableEnvironment applicationContext = event.getApplicationContext().getEnvironment();
        this.port = applicationContext.getProperty("local.server.port");
        try {
            writePortFile(port);
        }
        catch (Exception ex) {
            String message = String.format("Cannot create port file %s", this.file);
            logger.warn(message, ex);
        }
    }
}
