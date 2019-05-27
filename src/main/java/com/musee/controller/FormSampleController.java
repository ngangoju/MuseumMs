
package com.musee.controller;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import com.musee.dao.impl.ArtifactCategoryImpl;
import com.musee.dao.impl.ArtifactImpl;
import com.musee.dao.impl.DocumentsImpl;
import com.musee.dao.impl.UploadingFilesImpl;
import com.musee.dao.impl.UserImpl;
import com.musee.domain.Artifact;
import com.musee.domain.ArtifactCategory;
import com.musee.domain.Documents;
import com.musee.domain.UploadingFiles;
import com.musee.domain.Users;
import com.musee.dto.ArtifactDto;

import musee.common.DbConstant;
import musee.common.JSFBoundleProvider;
import musee.common.JSFMessagers;
import musee.common.SessionUtils;
import musee.common.UploadUtility;

@ManagedBean
@ViewScoped
public class FormSampleController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "FormSampleController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private Users users;
	
	/* class injection */
//	GenerateNotificationTemplete gen = new GenerateNotificationTemplete();
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	DocumentsImpl documentsImpl = new DocumentsImpl();
	UploadingFilesImpl uploadingFilesImpl = new UploadingFilesImpl();
	/* end class injection */
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	private Documents documents;
	private UploadingFiles uploadingFiles;
	ArtifactDto prdto= new ArtifactDto();
	private Users usersSession;
	Artifact prdt= new Artifact();
	ArtifactImpl prodImpl=new ArtifactImpl();
	ArtifactCategory prodCat= new ArtifactCategory();
	ArtifactCategoryImpl prodCatImpl= new ArtifactCategoryImpl();
	private List<UploadingFiles>filesUploaded= new ArrayList<UploadingFiles>();
	private DocumentsImpl docsImpl = new DocumentsImpl();
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		if (users == null) {
			users = new Users();
		}

		if (documents == null) {
			documents = new Documents();
		}
		if (uploadingFiles == null) {
			uploadingFiles = new UploadingFiles();
		}
//		try {
//			 menuGroupDetails=menuGroupImpl.getGenericListWithHQLParameter(new String[]
//			 {"genericStatus"},new Object[] {ACTIVE}, "MenuGroup", "menuGroupId asc");
//		} catch (Exception e) {
//			setValid(false);
//			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
//			LOGGER.info(e.getMessage());
//			e.printStackTrace();
//		}

	}

	public void fileUpload(FileUploadEvent event) {

		UploadUtility ut = new UploadUtility();
		String validationCode = "PROFILEIMAGE";
		try {
			documents = ut.fileUploadUtil(event, validationCode);

			// need to put exact users
			Users u = new Users();
			u.setUserId(1);
			uploadingFiles.setUser(u);
			uploadingFiles.setCrtdDtTime(timestamp);
			uploadingFiles.setGenericStatus(ACTIVE);
			uploadingFiles.setDocuments(documents);
			uploadingFiles.setCreatedBy(usersSession.getViewId());
			uploadingFilesImpl.saveIntable(uploadingFiles);

			LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("upload.message.success"));
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + "testing save methode ");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("unchecked")
	public void deleteExistCategoryImage(ArtifactCategory cat) throws Exception {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("/");
		LOGGER.info("Filse Reals Path::::" + realPath);
		if(null != cat) {
			filesUploaded=uploadingFilesImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "ArtifactCategory"},
					new Object[] { ACTIVE, cat},
					"UploadingFiles", "uploadId asc");
			
			for(UploadingFiles files:filesUploaded) {
				Documents doc = new Documents();
				doc = docsImpl.getModelWithMyHQL(new String[] { "DocId" },
						new Object[] { files.getDocuments().getDocId()}, " from Documents");
				final Path destination = Paths.get(realPath + FILELOCATION + doc.getSysFilename());
				LOGGER.info("Path::" + destination);
				File file = new File(destination.toString());
				uploadingFilesImpl.deleteIntable(files);
				docsImpl.deleteIntable(documents);
				LOGGER.info("Delete in db operation done!!!:");
				if (file.delete()) {
					System.out.println(file.getName() + " is deleted!");		
				} else {
					System.out.println("Delete operation is failed.");
					
				}
			}
			
		}
	}
	@SuppressWarnings("unchecked")
	public void deleteExistArtifactImage(Artifact Artifact) throws Exception {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("/");
		LOGGER.info("Filse Reals Path::::" + realPath);
		if(null != Artifact) {
			filesUploaded=uploadingFilesImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "Artifact"},
					new Object[] { ACTIVE, Artifact},
					"UploadingFiles", "uploadId asc");
			
			for(UploadingFiles files:filesUploaded) {
				Documents doc = new Documents();
				doc = docsImpl.getModelWithMyHQL(new String[] { "DocId" },
						new Object[] { files.getDocuments().getDocId()}, " from Documents");
				final Path destination = Paths.get(realPath + FILELOCATION + doc.getSysFilename());
				LOGGER.info("Path::" + destination);
				File file = new File(destination.toString());
				uploadingFilesImpl.deleteIntable(files);
				docsImpl.deleteIntable(documents);
				LOGGER.info("Delete in db operation done!!!:");
				if (file.delete()) {
					System.out.println(file.getName() + " is deleted!");		
				} else {
					System.out.println("Delete operation is failed.");
					
				}
			}
			
		}
	}
	@SuppressWarnings("unchecked")
	public void deleteExistImage() throws Exception {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("/");
		LOGGER.info("Filse Reals Path::::" + realPath);
		if(null != usersSession) {
			filesUploaded=uploadingFilesImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user"},
					new Object[] { ACTIVE, usersSession},
					"UploadingFiles", "uploadId asc");
			
			for(UploadingFiles files:filesUploaded) {
				Documents doc = new Documents();
				doc = docsImpl.getModelWithMyHQL(new String[] { "DocId" },
						new Object[] { files.getDocuments().getDocId()}, " from Documents");
				final Path destination = Paths.get(realPath + FILELOCATION + doc.getSysFilename());
				LOGGER.info("Path::" + destination);
				File file = new File(destination.toString());
				uploadingFilesImpl.deleteIntable(files);
				docsImpl.deleteIntable(documents);
				LOGGER.info("Delete in db operation done!!!:");
				if (file.delete()) {
					System.out.println(file.getName() + " is deleted!");		
				} else {
					System.out.println("Delete operation is failed.");
					
				}
			}
			
		}
	}
	public String uploadProfile(FileUploadEvent event) {

		try {
			if (null != usersSession) {
				UploadUtility ut = new UploadUtility();
				String validationCode = "PROFILEIMAGE";
				deleteExistImage();
				documents = ut.fileUploadUtilUsers(event, validationCode);
				uploadingFiles.setUser(usersSession);
				uploadingFiles.setCrtdDtTime(timestamp);
				uploadingFiles.setGenericStatus(ACTIVE);
				uploadingFiles.setDocuments(documents);
				uploadingFilesImpl.saveIntable(uploadingFiles);
				LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addInfoMessage(getProvider().getValue("upload.message.success"));
				/* addErrorMessage(getProvider().getValue("upload.message.success")); */
				return "/menu/EditProfile.xhtml?faces-redirect=true";
			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
			}
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + "testing profile upload methode ");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.userprofile.error"));
			e.printStackTrace();
		}
		return "/menu/EditProfile.xhtml?faces-redirect=true";
	}

//	public String ArtifactFilesUpload(FileUploadEvent event) {
//
//		try {
//			LOGGER.info("user info::"+usersSession.getViewId());
//			if (null != usersSession) {
//				
//				ArtifactController prdtControl= new ArtifactController();
//				prdto=prdtControl.saveArtifactFiles();
//				LOGGER.info("ACTIVITY INFO :::::::::::::::"+prdto.getArtifactId());
//				prdt=prodImpl.getModelWithMyHQL(new String[] { " ArtifactId" },
//						new Object[] { prdto.getArtifactId()}, "from Artifact");
//				deleteExistArtifactImage(prdt);
//				if(null!=prdt) {
//					UploadUtility ut = new UploadUtility();
//					String validationCode = "ArtifactImage";
//					documents = ut.fileUploadUtil(event, validationCode);
//						uploadingFiles.setArtifact(prdt);
//						uploadingFiles.setUser(usersSession);
//						uploadingFiles.setDocuments(documents);
//						uploadingFiles.setCreatedBy(usersSession.getViewId());
//						uploadingFiles.setGenericStatus(ACTIVE);
//						uploadingFiles.setCrtdDtTime(timestamp);
//						uploadingFilesImpl.saveIntable(uploadingFiles);
//						LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
//						prdt.setArtifactImage(event.getFile().getFileName());
//						prodImpl.UpdateArtifact(prdt);
//						LOGGER.info(":::::Artifact IMAGE NAME UPDATED!!::::::");
//						JSFMessagers.resetMessages();
//						setValid(true);
//						JSFMessagers.addInfoMessage(getProvider().getValue("com.server.side.Artifactfile.success"));
//						/*addErrorMessage(getProvider().getValue("upload.message.success"));*/
//				}
//					return null;
//				}else {
//					JSFMessagers.resetMessages();
//					setValid(false);
//					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
//				}		
//		} catch (Exception e) {
//			LOGGER.info(CLASSNAME + "testing profile upload methode ");
//			JSFMessagers.resetMessages();
//			setValid(false);
//			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.activityfile.error"));
//			e.printStackTrace();
//		}
//		return "";
//	}
//	public String ArtifactCategoryFilesUpload(FileUploadEvent event) {
//
//		try {
//			LOGGER.info("user info::"+usersSession.getViewId());
//			if (null != usersSession) {
//				
//				ArtifactCategoryController prdtCatControl= new ArtifactCategoryController();
//				prodCat=prdtCatControl.saveArtifactCategoryFiles();
//				LOGGER.info("CAT INFO :::::::::::::::"+prodCat.getArtifactCatid());
//				deleteExistCategoryImage(prodCat);
//				if(null!=prodCat) {
//					UploadUtility ut = new UploadUtility();
//					String validationCode = "ArtifactCategoryImage";
//					documents = ut.fileUploadUtil(event, validationCode);
//					LOGGER.info("CAT INFO :::::::::::::::"+prodCat.getArtifactCatid());
//						uploadingFiles.setArtifactCategory(prodCat);;
//						uploadingFiles.setUser(usersSession);
//						uploadingFiles.setDocuments(documents);
//						uploadingFiles.setCreatedBy(usersSession.getViewId());
//						uploadingFiles.setGenericStatus(ACTIVE);
//						uploadingFiles.setCrtdDtTime(timestamp);
//						uploadingFilesImpl.saveIntable(uploadingFiles);
//						LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
//						prodCat.setCategoryImage(event.getFile().getFileName());
//						prodCatImpl.UpdateArtifactCategory(prodCat);
//						LOGGER.info(":::::Artifact CATEGGORY IMAGE NAME UPDATED!!::::::");
//						JSFMessagers.resetMessages();
//						setValid(true);
//						JSFMessagers.addInfoMessage(getProvider().getValue("com.server.side.Artifactfile.success"));
//						/*addErrorMessage(getProvider().getValue("upload.message.success"));*/
//				}
//					return null;
//				}else {
//					JSFMessagers.resetMessages();
//					setValid(false);
//					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
//				}		
//		} catch (Exception e) {
//			LOGGER.info(CLASSNAME + "testing profile upload methode ");
//			JSFMessagers.resetMessages();
//			setValid(false);
//			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.activityfile.error"));
//			e.printStackTrace();
//		}
//		return "";
//	}
//	
	
//	@SuppressWarnings("unchecked")
//	public List<UploadingFiles> ArtifactCategoryImageDetails(){
//
//		try {
//
//			ArtifactCategoryController prdtCatControl= new ArtifactCategoryController();
//			prodCat=prdtCatControl.saveArtifactCategoryFiles();
//			LOGGER.info("CATEGRY INFO :::::::::::::::"+prodCat.getArtifactCatid());
//			return uploadingFilesImpl.getGenericListWithHQLParameter(new String[] { "genericStatus","ArtifactCategory" },
//					new Object[] { ACTIVE,prodCat}, "UploadingFiles","crtdDtTime desc");
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<UploadingFiles> ArtifactImageDetails(){
//
//		try {
//
//			ArtifactController prdtControl= new ArtifactController();
//			prdto=prdtControl.saveArtifactFiles();
//			Artifact Artifact= new Artifact();
//			Artifact=prodImpl.getArtifactById(prdto.getArtifactId(), "ArtifactId");
//			LOGGER.info("ACTIVITY INFO :::::::::::::::"+prdto.getArtifactId());
//			return uploadingFilesImpl.getGenericListWithHQLParameter(new String[] { "genericStatus","Artifact" },
//					new Object[] { ACTIVE,Artifact}, "UploadingFiles","crtdDtTime desc");
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public void downloadFile() {
//		UploadUtility ut = new UploadUtility();
//		try {
//			ut.downloadFileUtil();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public List<UploadingFiles> fileList() {

		try {
			return uploadingFilesImpl.getListWithHQL("from UploadingFiles");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	public void sendMailTest() throws AddressException, MessagingException {
//		/* sending content in a table example */
//		String name = "Mukamana";
//		String fname = "Eric";
//
//		String msg = "<p>Kindly refer to the  below status.</p>" + "<table width=\"50%\" border=\"5px\">\n"
//				+ "  <tbody>\n" + "	<tr>\n" + "      <td class=\"labelbold\">Fname</td>\n" + "      <td>\n" + "		  "
//				+ name + "\n" + "	  </td>\n" + "    </tr>\n" + "	<tr>\n"
//				+ "      <td class=\"labelbold\">Lname</td>\n" + "      <td>\n" + "		  " + fname + "\n"
//				+ "	  </td>\n"
//
//				+ "  </tbody>\n" + "</table>\n";
//		/* End send content in table sample */
//		gen.sendEmailNotification("sibo2540@gmail.com", "Sibo Emma", "Test Email", msg);
//		LOGGER.info("::: notidficatio sent   ");
//	}
//
//	public void sendUserMailTest(String useremail, String userfname, String userlname) throws AddressException, MessagingException {
//		/* sending content in a table example */
//		String name = "Mukamana";
//		String fname = "Eric";
//
//		String msg = "<p>Kindly refer to the  below status.</p>" + "<table width=\"50%\" border=\"5px\">\n"
//				+ "  <tbody>\n" + "	<tr>\n" + "      <td class=\"labelbold\">Fname</td>\n" + "      <td>\n" + "		  "
//				+ name + "\n" + "	  </td>\n" + "    </tr>\n" + "	<tr>\n"
//				+ "      <td class=\"labelbold\">Lname</td>\n" + "      <td>\n" + "		  " + fname + "\n"
//				+ "	  </td>\n"
//
//				+ "  </tbody>\n" + "</table>\n";
//		/* End send content in table sample */
//		gen.sendEmailNotification(useremail, userfname + " " + userlname, "Test Email", msg);
//		LOGGER.info("::: notidficatio sent   ");
//	}

	public void saveData() {
		LOGGER.info(CLASSNAME + "testing save methode ");
		JSFMessagers.resetMessages();
		setValid(false);
		JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));

	}

	public void changeSelectBox(String name) {

		LOGGER.info("Ajax is working with listener::::::" + name);
	}

	public String getCLASSNAME() {
		return CLASSNAME;
	}

	public void setCLASSNAME(String cLASSNAME) {
		CLASSNAME = cLASSNAME;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public UserImpl getUsersImpl() {
		return usersImpl;
	}

	public void setUsersImpl(UserImpl usersImpl) {
		this.usersImpl = usersImpl;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public DocumentsImpl getDocumentsImpl() {
		return documentsImpl;
	}

	public void setDocumentsImpl(DocumentsImpl documentsImpl) {
		this.documentsImpl = documentsImpl;
	}

	public UploadingFilesImpl getUploadingFilesImpl() {
		return uploadingFilesImpl;
	}

	public void setUploadingFilesImpl(UploadingFilesImpl uploadingFilesImpl) {
		this.uploadingFilesImpl = uploadingFilesImpl;
	}

	public Documents getDocuments() {
		return documents;
	}

	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

	public UploadingFiles getUploadingFiles() {
		return uploadingFiles;
	}

	public void setUploadingFiles(UploadingFiles uploadingFiles) {
		this.uploadingFiles = uploadingFiles;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public List<UploadingFiles> getFilesUploaded() {
		return filesUploaded;
	}
	
	public void setFilesUploaded(List<UploadingFiles> filesUploaded) {
		this.filesUploaded = filesUploaded;
	}

	public DocumentsImpl getDocsImpl() {
		return docsImpl;
	}

	public void setDocsImpl(DocumentsImpl docsImpl) {
		this.docsImpl = docsImpl;
	}

	public ArtifactDto getPrdto() {
		return prdto;
	}

	public void setPrdto(ArtifactDto prdto) {
		this.prdto = prdto;
	}

	public Artifact getPrdt() {
		return prdt;
	}

	public void setPrdt(Artifact prdt) {
		this.prdt = prdt;
	}

	public ArtifactImpl getProdImpl() {
		return prodImpl;
	}

	public void setProdImpl(ArtifactImpl prodImpl) {
		this.prodImpl = prodImpl;
	}

}
