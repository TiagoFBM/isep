package fabrica.smm.machinemonitoring.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class NetworkUtils {
    public static final int VERSION_OFFSET = 0;
    public static final int CODE_OFFSET = 1;
    public static final int ID_OFFSET = 2;
    public static final int DATA_LENGTH_OFFSET = 4;
    public static final int RAW_DATA_OFFSET = 6;
    public static final int DATA_SIZE = 512;
    public static final int TIMEOUT_TIME_SECONDS = 60;
    public static final int THREAD_REQUEST = 30;

    public static short buildShortFromBytes(byte[] data, int offset) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(data[offset]);
        bb.put(data[offset + 1]);
        return bb.getShort(0);
    }

    public static String buildStringFromData(byte[] data, short stringSize) {
        byte[] newData = new byte[stringSize];
        for (int i = 0; i < stringSize; i++) {
            newData[i] = data[RAW_DATA_OFFSET + i];
        }
        return new String(newData, StandardCharsets.UTF_8);
    }
}
