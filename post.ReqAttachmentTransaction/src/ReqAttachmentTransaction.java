import com.silanis.esl.sdk.DocumentPackage;
import com.silanis.esl.sdk.EslClient;
import com.silanis.esl.sdk.ExtractionType;
import com.silanis.esl.sdk.PackageId;
import com.silanis.esl.sdk.builder.DocumentBuilder;
import com.silanis.esl.sdk.builder.PackageBuilder;
import com.silanis.esl.sdk.builder.SignerBuilder;
import com.silanis.esl.sdk.internal.EslServerException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReqAttachmentTransaction
  {
    public static final String PACKAGE_TITLE = "WSL: RequestAttachments";
    public static final String[] DOCUMENT_ONE = {"sampleAgreement.pdf", "/home/john/Documents/OSS/docs/text-tags.pdf"};
    public static final String SIGNER = "john.cyclist.mcguinness+attachment@gmail.com";
  public static void main(String[] args) throws IOException, EslServerException
    {
	String env = "EU.PROD";
    Properties prop = readPropertiesFile("/home/john/Documents/OSS/config.properties");
	    
    EslClient eslClient = new EslClient(prop.getProperty(env + ".API"), prop.getProperty(env + ".URL"));

    // Build the DocumentPackage object
    DocumentPackage pkg = PackageBuilder.newPackageNamed(PACKAGE_TITLE)
        .withSigner(SignerBuilder.newSignerWithEmail(SIGNER)
            .withFirstName(prop.getProperty("FORENAME"))
            .withLastName("Signer_one")
            .withCompany("One")
            .withCustomId("robin")
            )
        .withDocument(DocumentBuilder.newDocumentWithName(DOCUMENT_ONE[0])
        	.fromFile(DOCUMENT_ONE[1])
            .enableExtraction()
            .withExtractionType(ExtractionType.TEXT_TAGS)
            )
        .withEmailMessage(PACKAGE_TITLE)
        .build();
    
    PackageId packageId = eslClient.createPackageOneStep(pkg);
    eslClient.sendPackage(packageId);
    System.out.println("{\n" + packageId + "\n}");
    
    System.out.println("Link for Signer 1:\n" + eslClient.getPackageService().getSigningUrl(packageId, "robin"));
//    Signer signer = SignerBuilder.newSignerWithEmail("john.cyclist.mcguinness+attachment@gmail.com")
//    		.withFirstName(prop.getProperty("FORENAME"))
//            .withLastName("Attacher")
//            .withCustomId("attaching")
//            .withAttachmentRequirement(newAttachmentRequirementWithName("zeta")
//                    .withDescription("DESCRIPTION")
//                    .isRequiredAttachment()
//                    .build())
//            .build();
//
//
// DocumentPackage superDuperPackage = newPackageNamed(getPackageName())
//            .describedAs("This is a package created using the eSignLive SDK")
//            .withSigner(signer)
//            .withDocument(DocumentBuilder.newDocumentWithName("test document")
//                    .fromStream(documentInputStream1, DocumentType.PDF)
//                    .withSignature(SignatureBuilder.signatureFor(email1)
//                            .build())
//                    .build())
//            .build();
//
//    packageId = eslClient.createAndSendPackage(superDuperPackage);
//
//    retrievedPackage = eslClient.getPackage(packageId);
//    signerAttachments = retrievedPackage.getSigner(email1).getAttachmentRequirements();
//    signerAtt = signerAttachments.get(0);
//
//    byte[] attachmentForSignerFileContent = new StreamDocumentSource(attachmentInputStream).content();
//    eslClient.uploadAttachment(packageId, signerAtt.getId(), ATTACHMENT_FILE_NAME,
//            attachmentForSignerFileContent, SIGNER1_ID);
//    signerAttachmentFileSize = attachmentForSignerFileContent.length;
//
//    retrievedPackage = eslClient.getPackage(packageId);
//    signerAttachments = retrievedPackage.getSigner(email1).getAttachmentRequirements();
//    signerAtt = signerAttachments.get(0);
//
//    filesAfterUpload = signerAtt.getFiles();
//    AttachmentFile attachmentFile = filesAfterUpload.get(0);
//
//    // Download signer attachment file
//    DownloadedFile downloadedAttachment = eslClient.getAttachmentRequirementService().downloadAttachmentFile(packageId, signerAtt.getId(), attachmentFile.getId());
//    Files.saveTo(downloadedAttachment.getContents(), downloadedAttachment.getFilename());
//    downloadedAttachmentFile = new File(downloadedAttachment.getFilename());
//
//    eslClient.deleteAttachmentFile(packageId, signerAtt.getId(), attachmentFile.getId(), SIGNER1_ID);
//
//    retrievedPackage = eslClient.getPackage(packageId);
//    signerAttachments = retrievedPackage.getSigner(email1).getAttachmentRequirements();
//    signerAtt = signerAttachments.get(0);
//
//    filesAfterDelete = signerAtt.getFiles();
    }


    public static void getSignerLink(String aSigner, String whom) {
      System.out.println("Link for " + whom + ":\n" + aSigner);
    }  
  
  public static Properties readPropertiesFile(String fileName) throws IOException
    {
    FileInputStream fis = null;
    Properties prop = null;

    try
      {
      fis = new FileInputStream(fileName);
      prop = new Properties();
      prop.load(fis);
      }
    catch(FileNotFoundException fnfe)
      {
      fnfe.printStackTrace();
      }
    catch(IOException ioe)
      {
      ioe.printStackTrace();
      }
    finally
      {
      fis.close();
      }

    return prop;
  	}
  }