package Client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import sun.misc.BASE64Encoder;

import com.rsa.jsafe.provider.SensitiveData;

public class ClientTools {
	
	private final static int METHOD_NAME_LENGTH = 20;
	
	public static String getEncryptedMethodName(String methodName, byte[] combinedKey) throws 
		NoSuchAlgorithmException, NoSuchProviderException {
		
		MessageDigest md = null;
		byte[] methodBytes = methodName.getBytes(); 

        try {
            // Calculate and print out the hash value.
            md = MessageDigest.getInstance("SHA256", "JsafeJCE");
            md.update(methodBytes);
            md.update(combinedKey);
            byte[] digest = md.digest();
            
            BASE64Encoder encoder = new BASE64Encoder();
            String encryptedMethodName = encoder.encode(digest);
            
            return parseStringToValidCharacters(encryptedMethodName);
            
        } finally {
            // Cryptographic objects should be cleared once they are no longer
            // needed.
            SensitiveData.clear(md);
        }
	}
	
	public static String parseStringToValidCharacters(String intialString) {
		return intialString.replaceAll("[^A-Za-z]", "").substring(0, METHOD_NAME_LENGTH);
	}

}
