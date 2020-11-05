package testing;

public class consolidateIP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		consolidateIP_BL bl=new consolidateIP_BL();
		
		bl.initPath();
		String[] ipFiles=bl.getIPFiles();
		bl.WeedOut(ipFiles);
		bl.xlwrite();
		
		
	}
}
