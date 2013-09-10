package XMLStorage.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class responsible to count bytes in stream
 */
public class CountInputStream extends FilterInputStream {

    private long count = 0L;

    public CountInputStream(InputStream in) {
        super(in);
    }

    /**
     *
     * @return value in Long
     */
    public long getCount() {
        return count;
    }

    public int read() throws IOException {
        final int c = super.read();
        if (c >= 0) {
            count++;
        }
        return c;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        final int bytesRead = super.read(b, off, len);
        if (bytesRead > 0) {
            count += bytesRead;
        }
        return bytesRead;
    }

    public int read(byte[] b) throws IOException {
        final int bytesRead = super.read(b);
        if (bytesRead > 0) {
            count += bytesRead;
        }
        return bytesRead;
    }


}