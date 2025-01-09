package opsnow.framework.core.pooling;

import java.nio.charset.Charset;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 * Pooling of StringBuilder instances.
 */
public class StringBuilderPool {
    private static final int DEFAULT_CAPACITY = 256;
    private static final ObjectPoolImpl<StringBuilder> pool = new ObjectPoolImpl<>(
            Integer.MAX_VALUE, // 사용할 수 있는 최대 StringBuilder 수
            1024 * 16, // 각 StringBuilder의 최대 용량
            capacity -> new StringBuilder(capacity),
            sb -> {
                sb.setLength(0); // StringBuilder 초기화
                sb.trimToSize(); // 용량 최적화
            },
            StringBuilder::capacity
    );

    /**
     * Take retrieves the StringBuilder from the object pool (if already exists)
     * or else creates an instance of StringBuilder and returns (if not exists)
     * @return
     */
    public static StringBuilder take() {
        return take(-1);
    }

    public static StringBuilder take(int capacity) {
        StringBuilder sb = pool.take();
        if (capacity > 0 && sb.capacity() < capacity) {
            sb.ensureCapacity(capacity); // 요구된 용량을 보장
        }
        return sb;
    }

    public static void release(StringBuilder sb) {
        if (sb != null) {
            pool.release(sb);
        }
    }

    public static String toString(StringBuilder sb) {
        String result = "";
        if (sb != null) {
            result = sb.toString();
            release(sb); // StringBuilder를 풀에 반환
        }
        return result;
    }

    public static byte[] toBytes(StringBuilder sb, Charset charset) {
        String result = toString(sb);
        return result.isEmpty() ? new byte[0] : charset.encode(result).array();
    }
}
