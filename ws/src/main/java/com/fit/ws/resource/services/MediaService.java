package com.fit.ws.resource.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fit.ws.core.commons.AbstractCrudService;
import com.fit.ws.core.exceptions.AppException;
import com.fit.ws.core.exceptions.ErrorCode;
import com.fit.ws.core.utils.CommonUtils;
import com.fit.ws.core.utils.DateTimeUtils;
import com.fit.ws.resource.dto.MediaInputDto;
import com.fit.ws.resource.models.MediaModel;
import com.fit.ws.resource.repositorys.MediaReponsitory;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MediaService extends AbstractCrudService<MediaModel, ObjectId>{
	private final MediaReponsitory mediaReponsitory;
	private final FileLocalService fileLocalService;
	private final XSSValidationService xssValidationService;
	
	public MediaModel createMedia(MediaInputDto mediaInputDto) throws FileNotFoundException, IOException {
		MediaModel mediaModel = new MediaModel();
		
		File uploadRootDir = new File(fileLocalService.getPathAttachments());
		if(!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		
		MultipartFile file = mediaInputDto.getFile();
		if(file == null || file.isEmpty()) {
			throw new FileNotFoundException();
		}
		
		xssValidationService.santize(file.getName());
		xssValidationService.santize(file.getOriginalFilename());
		
		String desription = mediaInputDto.getDescription();
		
		String date = DateTimeUtils.getDateFolder().format(new Date());
		
		mediaModel.setFileType(file.getContentType());
		mediaModel.setFileDescription(desription);
		mediaModel.setFilePath(date+"/"+System.currentTimeMillis()+"_"+CommonUtils.toFilename(file.getOriginalFilename()));
		
		File folderFile = new File(uploadRootDir.getAbsolutePath() + File.separator+date);
		if(!folderFile.exists()) {
			folderFile.mkdirs();
		}
		
		File storeFile = new File(uploadRootDir.getAbsolutePath() + File.separator + mediaModel.getFilePath());
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(storeFile));
		stream.write(file.getBytes());
		stream.close();
		
		mediaModel.setCreateTime(new Date());
		
		mediaModel = mediaReponsitory.save(mediaModel);
		
		return mediaModel;
	}
	
	public MediaModel storeVideo(MediaInputDto mediaInputDto) throws FileNotFoundException, IOException{
		
		MediaModel mediaModel = new MediaModel();
		
		
		MultipartFile file = mediaInputDto.getFile();
		
		String originalFileName = file.getOriginalFilename();
		String fileExtension = getFileExtension(originalFileName);
		String uniqueFilename = generateUniqueFilename(fileExtension);
		
		Path uploadPath = Paths.get(fileLocalService.getPathAttachments(), "videos");
		Files.createDirectories(uploadPath);
		
		Path filePath = uploadPath.resolve(uniqueFilename);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		
		mediaModel.setFileType(file.getContentType());
		mediaModel.setFileDescription(mediaInputDto.getDescription());
		mediaModel.setFilePath(Paths.get("videos", uniqueFilename).toString());
		
		mediaModel.setCreateTime(new Date());
		
		return mediaReponsitory.save(mediaModel);
	}
	
	public Resource loadAsResource(String filePath) throws MalformedURLException {
		Path path = Paths.get(fileLocalService.getPathAttachments()).resolve(filePath);
		System.out.println("Paht: "+path.toUri());
		Resource resource = new UrlResource(path.toUri());
		System.out.println("exit"+resource);
		if(resource.exists() && resource.isReadable()) {
			return resource;
		}else {
			throw new AppException(ErrorCode.DATA_NOT_FOUND);
		}
	}
	
    private String generateUniqueFilename(String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }
    
    private String getFileExtension(String filename) {
        return filename != null ? 
            filename.substring(filename.lastIndexOf(".") + 1) : "";
    }
    
    public MediaModel getInfoMedia(String id) {
    	MediaModel mediaModel = getById(new ObjectId(id));
    	Path path = Paths.get(fileLocalService.getPathAttachments()).resolve(mediaModel.getFilePath());
    	mediaModel.setFilePath(path.toUri().toString());
    	return mediaModel;
    }

	@Override
	protected MongoRepository<MediaModel, ObjectId> getRepository() {
		return mediaReponsitory;
	}
	
}
