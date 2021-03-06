package org.tdr.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class BytesURLConnection extends URLConnection {
    protected byte[] content;

    protected int offset;

    protected int length;

    public BytesURLConnection( URL url, byte[] content ) {
        super( url );
        this.content = content;
    }

    public void connect()  {
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream( content );
    }
}