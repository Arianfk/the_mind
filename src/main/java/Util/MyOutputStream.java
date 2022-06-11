package Util;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MyOutputStream extends DataOutputStream {
    /**
     * Creates a new data output stream to write data to the specified
     * underlying output stream. The counter {@code written} is
     * set to zero.
     *
     * @param out the underlying output stream, to be saved for later
     *            use.
     * @see FilterOutputStream#out
     */
    public MyOutputStream(OutputStream out) {
        super(out);
    }

    public void writeLine(String s) throws IOException {
        String tmp = s + '\n';
        write(tmp.getBytes(StandardCharsets.UTF_8), 0, tmp.length());
    }
}
