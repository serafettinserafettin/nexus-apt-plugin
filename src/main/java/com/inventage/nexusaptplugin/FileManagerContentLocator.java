package com.inventage.nexusaptplugin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.sonatype.nexus.proxy.item.ContentLocator;

import com.inventage.nexusaptplugin.cache.DebianFileManager;
import com.inventage.nexusaptplugin.cache.RepositoryData;


public class FileManagerContentLocator
        implements ContentLocator {

    private final DebianFileManager fileManager;

    private final String mimeType;

    private final RepositoryData data;

    private final String name;

    public FileManagerContentLocator(DebianFileManager fileManager,
                                     String mimeType, RepositoryData data, String name) {
        this.fileManager = fileManager;
        this.mimeType = mimeType;
        this.data = data;
        this.name = name;
    }

    @Override
    public InputStream getContent()
            throws IOException {
        try {
            return new ByteArrayInputStream(fileManager.getFile(name, data));
        }
        catch (ExecutionException e) {
            throw new IOException("Could not generate " + name, e);
        }
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public long getLength() {
        return UNKNOWN_LENGTH;
    }

    @Override
    public boolean isReusable() {
        return false;
    }

}
