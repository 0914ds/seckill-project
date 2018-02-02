package seckill.common.tools;

import org.springframework.util.DigestUtils;

public class Tools {

    public static String getMD5(long seckillId) {
         String slat = "asdfasd2341242@#$@#$%$%%#@$%#@%^%^";
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
	
}
