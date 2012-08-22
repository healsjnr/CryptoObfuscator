package Module;

public class SecureModuleImplementation implements ModuleInterface,
		SecurePackageInterface {

	private byte[] packageKey = "Package".getBytes();
	
	@Override
	public byte[] getPackageKey() {
		return this.packageKey;
	}

	@Override
	public void initPackage(byte[] clientKey) {
		
	}

	@Override
	public byte[] getSomeData(String dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] putSomeData(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

}
