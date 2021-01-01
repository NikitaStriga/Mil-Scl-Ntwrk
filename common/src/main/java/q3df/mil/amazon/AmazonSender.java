package q3df.mil.amazon;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import q3df.mil.exception.AmazonCustomException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
@PropertySource("classpath:amazon-config.properties")
public class AmazonSender {

    private final AmazonConfig amazonConfig;
    private AmazonS3 amazonS3Client;
    private String pathForUploadOnPc;


    @Autowired
    public AmazonSender(AmazonConfig amazonConfig) {
        this.amazonConfig = amazonConfig;
    }


    /**
     * define amazon client that is needed for which is required to interact with Amazon
     * + pathForUploadOnPc (path will root_of_the_project/uploaded/...)
     */
    @PostConstruct
    public void defineAmazonS3Client() {

        //credentials for AmazonS3 interface
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                amazonConfig.getAccessKey(), amazonConfig.getSecretKey()
        );

        //interface for accessing the Amazon S3 web service
        this.amazonS3Client =
                AmazonS3ClientBuilder
                        .standard()
                        .withRegion(amazonConfig.getRegion())
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                        .build();

        //path for saving files on local storage
        this.pathForUploadOnPc = System.getProperty("user.dir") + "/uploaded/";
    }


    /**
     * save photo to Amazon and then do record to db
     * @param multipartFile photo
     * @param userId userId
     * @return file Name of saved photo to Amazon
     */
    public String sendToAmazon(MultipartFile multipartFile, Long userId) {

        //get original name, it will be need to save in local storage
        String originalName =
                Optional
                        .ofNullable(multipartFile.getOriginalFilename())
                        .orElseThrow(() -> new AmazonCustomException("File name not specified!"));

        //get extension of file
        String extensionOfFile =
                Optional
                        .ofNullable(originalName)
                        .filter(f -> f.contains("."))
                        .map(f -> f.substring(originalName.lastIndexOf(".") + 1))
                        .orElseThrow(() -> new AmazonCustomException("Extension of file is not specified! "));

        //amazon search pattern
        String searchPattern = amazonConfig.getSearchPattern();

        //bucketName
        String bucketName = amazonConfig.getBucketName();

        //folder
        String folderName = "photos/" + userId + "/";

        //random file name without extension
        String fileName = UUID.randomUUID().toString();

        //fullName of file
        String fullName = StringUtils.join(folderName, fileName,".", extensionOfFile);

        //convert MultiPartFile to File
        File file = convertMultipartFileToFile(multipartFile);

        //create request
        PutObjectRequest request = new PutObjectRequest(bucketName, fullName, file);

        //send file
        try {
            this.amazonS3Client.putObject(request);

            //delete file after upload to Amazon
            if (!file.delete()) {
                log.error("Can't delete file " + file.getName());
            }

        } catch (SdkClientException ex) {
            throw new AmazonCustomException("Cant send file to Amazon, cause - " + ex.getMessage());
        }

        //url of file
        String urlOfFile = searchPattern + fullName;

        return urlOfFile;

    }



    /**
     * convert MultipartFile to File
     * used by -----> sendToAmazon(MultipartFile multipartFile, Long userId)
     * @param file MultipartFile
     * @return File which was obtained from MultipartFile
     */
    private File convertMultipartFileToFile(MultipartFile file) {

        //get original fileName
        String fileName = file.getOriginalFilename();

        //create file
        File convertedFile = new File(pathForUploadOnPc + fileName);

        //create directory (if it already exist this method just return false)
        convertedFile.getParentFile().mkdir();

        //create inputStream and getBytes
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException ex) {
            throw new AmazonCustomException("Can't read bytes from file!");
        }
        return convertedFile;

    }


    /**
     * get bytes from downloaded file from Amazon
     * method is NOT USED in the application
     * @param urlOfFile url  of file in Amazon
     * @return bytes of file
     */
    public byte[] getFile(String urlOfFile) {

        //fileName
        String fileName = urlOfFile.replace(amazonConfig.getSearchPattern(),"");

        //get object
        S3Object object = this.amazonS3Client.getObject(amazonConfig.getBucketName(), fileName);

        //get input stream
        S3ObjectInputStream objectContent = object.getObjectContent();
        byte[] bytes;
        try {
            //read bytes
            bytes = objectContent.readAllBytes();
        } catch (IOException exception) {
            throw new AmazonCustomException("Can't read bytes from file!" + exception);
        }
        return bytes;
    }


    /**
     * delete file from Amazon and then from db
     * @param urlOfFile url of file  on Amazon
     */
    public void deleteFromAmazon(String urlOfFile) {

        //fileName
        String fileName = urlOfFile.replace(amazonConfig.getSearchPattern(),"");

        try {
            this.amazonS3Client.deleteObject(amazonConfig.getBucketName(), fileName);
        } catch (SdkClientException ex) {
            throw new AmazonCustomException("Can't delete file from Amazon" + ex.getMessage());
        }

    }

}
