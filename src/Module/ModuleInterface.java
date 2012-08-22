package Module;

public interface ModuleInterface {
	
	byte[] getSomeData(String dataId) throws Exception;
	
	byte[] putSomeData(byte[] data) throws Exception;
	
}
