package org.zhiqsyr.mfmall.utils;

import java.security.MessageDigest;

/**
 * @author zhiqsyr
 * @since 2017/5/11
 */
public class MD5Utils {

    /**
     * 32位加密
     *
     * @param src
     * @return
     */
    public static String encode32(String src) {
        return encode(src, true);
    }

    /**
     * md5加密
     *
     * @param src
     * @param is32
     * @return
     */
    private static String encode(String src, boolean is32) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(src.getBytes());
            byte digested[] = digest.digest();

            StringBuffer result = new StringBuffer("");
            int i;
            for (int offset = 0; offset < digested.length; offset++) {
                i = digested[offset];
                if (i < 0) i += 256;
                if (i < 16) result.append("0");
                result.append(Integer.toHexString(i));
            }

            if (is32) return result.toString();
            return result.toString().substring(8, 24);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
