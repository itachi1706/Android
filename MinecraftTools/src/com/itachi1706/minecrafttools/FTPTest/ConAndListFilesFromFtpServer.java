package com.itachi1706.minecrafttools.FTPTest;

import java.io.File;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ConAndListFilesFromFtpServer implements OnClickListener{
	
	private TextView tv;
	private Activity mActivity;
	
	public ConAndListFilesFromFtpServer(TextView viewer, Activity act){
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
		File extSdFile = mActivity.getExternalFilesDir(null);	//The folder
		try {
			//Connecting to the FTP Client
			ftp.connect(serverAddr, ftpPort);
			ftp.enterLocalPassiveMode();
			ftp.login(username, password);
			int reply = ftp.getReplyCode();
            //FTPReply stores a set of constants for FTP reply codes. 
            if (!FTPReply.isPositiveCompletion(reply))
            {
                ftp.disconnect();
                tv.setText("An error occured trying to login!");
                return;
            }
            
            //get system name
            builder.append("Remote system is " + ftp.getSystemType() + "\n");
			
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			FTPFile[] files = ftp.listFiles();
			builder.append("Storage Location: " + extSdFile.getAbsolutePath() + "\nFTP Server Files:\n");
			for (FTPFile file : files){
				builder.append(file.getName() + "     " + file.getRawListing() + "\n");
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
