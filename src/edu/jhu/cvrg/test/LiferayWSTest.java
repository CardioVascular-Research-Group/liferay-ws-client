package edu.jhu.cvrg.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import edu.jhu.cvrg.liferay.portal.service.ServiceContext;
import edu.jhu.cvrg.liferay.portlet.documentlibrary.service.http.DLAppServiceSoap;

import edu.jhu.cvrg.liferay.portal.kernel.repository.model.FileEntrySoap;
import edu.jhu.cvrg.liferay.portal.kernel.repository.model.FolderSoap;
import edu.jhu.cvrg.liferay.portlet.documentlibrary.service.http.DLAppServiceSoapServiceLocator;
import edu.jhu.cvrg.liferay.portlet.documentlibrary.service.http.Portlet_DL_DLAppServiceSoapBindingStub;

public class LiferayWSTest {

	public static void main(String[] args) {
		
		DLAppServiceSoapServiceLocator locator = new DLAppServiceSoapServiceLocator();
		
		try {
			DLAppServiceSoap service = locator.getPortlet_DL_DLAppService(new URL("http://127.0.0.1:8080/api/secure/axis/Portlet_DL_DLAppService"));
			
			((Portlet_DL_DLAppServiceSoapBindingStub)service).setUsername("axis2@apache.org");
			((Portlet_DL_DLAppServiceSoapBindingStub)service).setPassword("liferay");
			
			FolderSoap[]  folders = service.getFolders(10179L, 0L);
			
			long destFolderId = 0L;
			
			if(folders != null){
				for (FolderSoap folderSoap : folders) {
					System.out.println(folderSoap.getName());
					destFolderId = folderSoap.getFolderId();
				}
			}
			File orign = new File("/home/WIN/avilard4/XML/Philips104/ecg_900657176_1.xml");
			FileInputStream fis = new FileInputStream(orign);
			
			long groupId = 10179;
			String fileName = "axis2.xml";
			byte[] bytes = new byte[Long.valueOf(orign.length()).intValue()];
			
			fis.read(bytes);
			fis.close();
			
			FileEntrySoap file = service.addFileEntry(groupId, destFolderId, fileName, "", fileName, "", "1.0", bytes, new ServiceContext());
			System.out.println(file.getVersion());
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
