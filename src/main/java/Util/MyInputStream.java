package Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyInputStream extends DataInputStream {
    /**
     * Creates a DataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    public MyInputStream(InputStream in) {
        super(in);
    }

    public String nextLine() throws IOException {
        StringBuilder res = new StringBuilder();
        char c;
        while ((c = (char) read()) != '\n') res.append(c);
        return res.toString();
    }
}
