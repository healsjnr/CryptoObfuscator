package Client;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Module.EncryptedImplementation;
import Module.ModuleInterface;
import Module.SecureModuleImplementation;
import Module.SecurePackageInterface;

public class ClientModuleImplementation implements ModuleInterface,
		SecurePackageInterface {

	private EncryptedImplementation implementation;
	private Map<String, Method> methodMap;
	private boolean initialised = false;
	
	public ClientModuleImplementation() {
		this.implementation = new EncryptedImplementation();
		this.methodMap = new HashMap<String, Method>();
	}
	
	@Override
	public byte[] getPackageKey() {
		return this.implementation.getPackageKey();
	}

	@Override
	public void initPackage(byte[] clientKey) throws Exception {
		this.implementation.initPackage(clientKey);
		byte[] packageKey = this.implementation.getPackageKey();
		byte[] combinedKey = Arrays.copyOf(clientKey, clientKey.length + packageKey.length);
		System.arraycopy(packageKey, 0, combinedKey, clientKey.length, packageKey.length);
		
		// Get list of methods in ModuleInterface.
		Method[] methods = ModuleInterface.class.getMethods();
		Method[] encryptedMethods = EncryptedImplementation.class.getMethods();
		
	
		for (Method method : methods) {
			String methodName = method.getName(); 
			String encryptedName = ClientTools.getEncryptedMethodName(methodName, combinedKey);
			for (Method encMethod : encryptedMethods) {
				if (encMethod.getName().equals(encryptedName)) {
					this.methodMap.put(methodName, encMethod);
				}
			}
		}
		
		for(Entry<String, Method> set : this.methodMap.entrySet()) {
			System.out.println("Method: " + set.getKey() + " Encrypted: " + set.getValue().getName());
		}
		
		this.initialised = true;
	
	}

	@Override
	public byte[] getSomeData(String dataId) throws Exception {
		if (!this.initialised) {
			return null;
		}
	
		return (byte[]) callMethod("getSomeData", new Object[] {dataId});
	}

	@Override
	public byte[] putSomeData(byte[] data) throws Exception {
		if (!this.initialised) {
			return null;
		}
				
		return (byte[]) callMethod("putSomeData", new Object[] {data});
	}
	
	private Object callMethod(String name, Object... params) throws Exception {
		
		Method encryptedMethod = this.methodMap.get(name);
		
		if (encryptedMethod == null) {
			throw new Exception("Unable to find method - Authentication Error!");
		}
			
		byte[] result = null;
		try {
			result = (byte[]) encryptedMethod.invoke(this.implementation, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}

}
