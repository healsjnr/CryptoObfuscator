package Module;

public interface SecurePackageInterface {
	
	byte[] getPackageKey();
	
	void initPackage(byte[] clientKey) throws Exception;

}
