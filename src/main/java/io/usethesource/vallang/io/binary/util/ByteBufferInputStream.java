package io.usethesource.vallang.io.binary.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ByteBufferInputStream extends InputStream {
    
    private ByteBuffer source;
    
    public ByteBuffer getByteBuffer() {
        return source;
    }

    public ByteBufferInputStream(ByteBuffer source) {
        this.source = source;
    }
    
    protected ByteBuffer refill(ByteBuffer torefill) throws IOException {
        return torefill;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len <= source.remaining()) {
            source.get(b, off, len);
            return len;
        }
        // else first get what is left
        int read = 0;
        while (read < len) {
            int chunk = Math.min(source.remaining(), len - read);
            source.get(b, off + read, chunk);
            read += chunk;
            if (read < len && !source.hasRemaining()) {
                source = refill(source);
                if (!source.hasRemaining()) {
                    throw new EOFException();
                }
            }
        }
        return len;
    }
    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }
    
    @Override
    public int read() throws IOException {
        if (!source.hasRemaining()) {
            source = refill(source);
            if (!source.hasRemaining()) {
                throw new EOFException();
            } 
        }
        return source.get();
    }
    
}