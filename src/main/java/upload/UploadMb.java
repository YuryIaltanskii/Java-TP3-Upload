package upload;

import java.io.*;
import java.sql.*;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import upload.Labcon;

public class UploadMb {
	
	private UploadedFile uploadedFile;
	public UploadedFile getUploadedFile() {
	return uploadedFile;
}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Blob getDbImage() {
		return dbImage;
	}
	
	public void setDbImage(Blob dbImage) {
		this.dbImage = dbImage;
	}
	
	private String fileName;
	private Blob dbImage;
	public String upload() {

		String result="";
		Connection con = null;
		try {
				File imgfile = new File(uploadedFile.getName());
				System.out.println("Name: " + imgfile.getName());
				FileInputStream fin = new FileInputStream(imgfile);
				Labcon lc = new Labcon();
				con = lc.getLocalConnection();
				PreparedStatement pre = con.prepareStatement("insert into upload_image (IMAGE,Image_name,image_length) values(?,?,?)");
				pre.setBinaryStream(1,fin,(int)imgfile.length());
				pre.setString(2, imgfile.getName());
				pre.setLong(3, uploadedFile.getSize());
				pre.executeUpdate();
				System.out.println("Inserting Successfully!");
				pre.close();
		} catch (Exception ioe) {
			System.out.println("Exception-File Upload."+ioe.getMessage());			
		}
		return result;
	}

}