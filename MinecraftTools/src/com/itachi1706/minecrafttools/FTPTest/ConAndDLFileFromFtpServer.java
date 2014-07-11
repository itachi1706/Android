package com.itachi1706.minecrafttools.ftptest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ConAndDLFileFromFtpServer implements OnClickListener{
	
	private TextView tv;
	private Activity mActivity;
	
	public ConAndDLFileFromFtpServer(TextView viewer, Activity act){
		tv = viewer;
		mActivity = act;
	}

	@Override
	public void onClick(View v) {
		// Connect to the FTP Server and List the files
		String username = "androidapp";
		String password = "ftp";
		String serverAddr = "itachi1706.cloudapp.net";
		int ftpPort = 21;
		FTPClient ftp = new FTPClient();
		StringBuilder builder = new StringBuilder();
		File extSdFileTmp = mActivity.getExternalFilesDir(null);	//The folder
		File extSdFilePath = new File(extSdFileTmp.getAbsolutePath() + "/res");	//Root Resource Folder
		try {
			//Connecting to the FTP Client
			ftp.connect(serverAddr, ftpPort);
			ftp.enterLocalPassiveMode();
			ftp.login(username, password);
			
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			FTPFile[] files = ftp.listFiles();
			builder.append("Storage Location: " + extSdFilePath.getAbsolutePath() + "\n");
			
			//Delete files already existing
			File tmpFileDeleting = new File(extSdFilePath.getAbsolutePath() + System.currentTimeMillis());
			extSdFilePath.renameTo(tmpFileDeleting);
			FileUtils.deleteDirectory(tmpFileDeleting);
			builder.append("External Files Deleted!\n");
			builder.append("Downloading files from server...\n");
			extSdFilePath.mkdir();
			for (FTPFile file : files){
				if (file.isDirectory()){
					//Create the folder
					File tmpDir = new File(extSdFilePath.getAbsolutePath() + "/" + file.getName());
					tmpDir.mkdir();
					builder.append("Folder Created: " + file.getName() + "\n");
				} else {
					//Loop through and download file
					OutputStream output;
					output = new FileOutputStream(extSdFilePath.getAbsolutePath() + "/" + file.getName());
					ftp.retrieveFile(file.getName(), output);
					output.close();
					builder.append("File Retrived and Saved locally: " + file.getName() + "\n");
				}
			}
			ftp.logout();
			ftp.disconnect();
		} catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e){
	        e.printStackTrace();
	    }
		tv.setText(builder.toString());
	}
}