package com.fit.ws.resource.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.ws.core.utils.PropUtils;

@Service
public class FileLocalService {
	
	@Autowired
	private PropUtils propUtils;
	
	private String folderAttachments = "attachments";
	private String folderExports = "exports";
	private String folderTemplates = "templates";
	
	public String getPathAttachments() {
		return propUtils.getStoresFolderPath() + File.separator+folderAttachments;
	}
	
	public String getPathExports() {
		return propUtils.getStoresFolderPath() + File.separator + folderExports;
	}
	
	public String getPathTemplates() {
		return propUtils.getStoresFolderPath() + File.separator + folderTemplates;
	}
	
	public byte[] getFile(String filePath) {
		try {
			File storeFile = new File(getPathAttachments() + File.separator + filePath );
			if(storeFile.exists() == false) {
				return null;
			}
			
			byte[] inFileBytes = Files.readAllBytes(Paths.get(storeFile.getPath()));
			return inFileBytes;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public void deletedVoid(String filePath) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					File file = new File(getPathAttachments() + File.separator + filePath);
					if(file.exists()) {
						try {
							Files.delete(Paths.get(file.getAbsolutePath()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		thread.run();
	}
	
}
