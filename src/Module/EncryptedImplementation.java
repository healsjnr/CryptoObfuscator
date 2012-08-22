package Module;

public class EncryptedImplementation {
	
	private byte[] packageKey = "Package".getBytes();
	private byte[] clientKey;
	
	public byte[] getPackageKey() {
		return this.packageKey;
	}

	public void initPackage(byte[] clientKey) {
		this.clientKey = new byte[clientKey.length];
		System.arraycopy(clientKey, 0, this.clientKey, 0, clientKey.length);
		
	}

	public byte[] AddcnOpGMfdwtqYzbbOb(String dataId) {
		String s = "You called getSomeData with parameter: " + dataId;
		return s.getBytes();
		
	}

	public byte[] VUBbFgueJzYqUhnBgliP(byte[] data) {
		String s = "You called putSomeData with parameter: " + new String(data);
		return s.getBytes();
	}

}
