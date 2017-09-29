import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class MessageDigestAndHex {
    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    
    private static final String[] ALGORITHM = {"MD2", "MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512"};
    
    public static void main(String[] args) throws Exception {
        // Provider List
        for(Provider p : Security.getProviders()){
            System.out.println(p.getName() + " : " +p.getInfo());
        }
        
        System.out.println(validateDigest("message1", "message2", ALGORITHM[3]));
        
        File file = new File(filePath);
        System.out.println(ApacheDigestUtilsFileChecksum(file));
        System.out.println(JavaDigestInputStream(file));

        String foo="123";
        System.out.println(MD5Message(foo));
        System.out.println(new String(ApacheEncodeHex(foo.getBytes())));
    }

    public static String JavaDigestInputStream(File file) throws NoSuchAlgorithmException, IOException, FileNotFoundException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM[1]);
        FileInputStream fis = new FileInputStream(file);
        DigestInputStream dis = new DigestInputStream(fis, md);

        byte[] buffer = new byte[8192];
        try{
            while(dis.read(buffer)>-1);
        }finally{
            dis.close();
            fis.close();
        }

        return new String(ApacheEncodeHex(md.digest()));
    }

    public static String ApacheDigestUtilsFileChecksum(File file) throws IOException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        String md5 = DigestUtils.md5Hex(fis);
        String sha1 = DigestUtils.sha1Hex(fis);
        String sha256 = DigestUtils.sha256Hex(fis);
        String sha384 =	DigestUtils.sha384Hex(fis);
        String sha512 =	DigestUtils.sha512Hex(fis);
        System.out.println("md5 : " + md5);
        System.out.println("sha1 : " + sha1);
        System.out.println("sha256 : " + sha256);
        System.out.println("sha384 : " + sha384);
        System.out.println("sha512 : " + sha512);

        fis.close();
        return md5;
    }

    public static boolean validateDigest(String msg1, String msg2, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        //MessageDigest md = DigestUtils.getSha256Digest(); // Apache DigestUtils
        md.update(msg1.getBytes("UTF-8"));
        //md.update("test".getBytes("UTF-8"));
        //md.reset();
        //md.update("test1".getBytes("UTF-8"));
        
        MessageDigest md2 = MessageDigest.getInstance(algorithm);
        md2.update(msg2.getBytes("UTF-8"));
        //byte[] digest1 = md.digest();
        //System.out.println(ApacheEncodeHex(digest1));
        //byte[] digest2 = md2.digest();
        //System.out.println(ApacheEncodeHex(digest2));
        return MessageDigest.isEqual(md.digest(), md2.digest());
    }

    public static String MD5Message(String source) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //MessageDigest md = MessageDigest.getInstance(ALGORITHM[1], provider.getName());
            byte[] bytes = md.digest(source.getBytes("UTF-8"));
            result = Hex.encodeHexString(bytes);
        } catch (Exception e) {
            result = "";
        }
        return result;
    }
    
    public static char[] ApacheEncodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0; for (int j = 0; i < l; i++) {
            out[(j++)] = DIGITS_LOWER[((0xF0 & data[i]) >>> 4)];
            out[(j++)] = DIGITS_LOWER[(0xF & data[i])];
        }
        return out;
    }
}