package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Provider;
import java.security.Security;

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			addJsafeJCE();
			
			ClientModuleImplementation module = new ClientModuleImplementation();
			String password = readPassword();
			byte[] key = password.getBytes();
			module.initPackage(key);
			
			String firstCall = new String(module.getSomeData("Testing Testing..."));
			System.out.println("Method 1: " + firstCall);
			String secondCall = new String(module.getSomeData("Calling method 2"));
			System.out.println("Method 2: " + secondCall);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static String readPassword() {
		String password = "";
		try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            System.out.println("Enter your password: ");
            password = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return password;
		
	}
	
	private static void addJsafeJCE() throws Exception {

        // Remove provider if it's already registered, or insert will fail.
        Security.removeProvider("JsafeJCE");

        // Create a new provider object for the JsafeJCE provider.
        Provider jsafeProvider = new com.rsa.jsafe.provider.JsafeJCE();

        // Register provider in 1st position.
        int position = Security.insertProviderAt(jsafeProvider, 1);
        if (position != 1) {
            throw new RuntimeException(
                    "Failed to insert provider at first position");
        }
    }

}
