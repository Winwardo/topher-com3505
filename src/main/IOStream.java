package main;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;

public class IOStream extends DataInputStream {

    public IOStream(String file) throws FileNotFoundException {
        super(new FileInputStream(file));
    }

    public short readShortW() throws IOException {
        return (short) (readUnsignedByte() + readUnsignedByte() * 256);
    }

    public int readIntW() throws IOException {
        return readShortW() + readShortW() * 256 * 256;
    }

    void read(Buffer[] buf) {

    }
}