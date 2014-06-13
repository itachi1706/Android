package com.itachi1706.minecrafttools.AsyncTasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.itachi1706.minecrafttools.R;
import com.itachi1706.minecrafttools.UpdateResources;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;


public class GetNewAppResources extends AsyncTask <FTPClient, Void, Boolean>{
	
	Activity mActivity;
	String serverAddr;
	int ftpPort;
	String username = "androidapp",password="ftp";
	private Exception e=null;
	String errorMsg="";
	NotificationManager notifyManager;
	NotificationCompat.Builder notifyBuilder;
	int notifyId=100;
	int start = 0;
	FTPFile currentFile;
	boolean manual=false;
	
	public GetNewAppResources(Activity activity, String serveraddress, int ftpPortNo, NotificationManager manager, NotificationCompat.Builder builder, boolean manua){
		this.mActivity=activity;
		this.serverAddr=serveraddress;
		this.ftpPort=ftpPortNo;
		this.notifyManager=manager;
		this.notifyBuilder=builder;
		this.manual=manua;
	}
	
	@Override
	protected Boolean doInBackground(FTPClient... ftpclients) {
		// 
		if (manual){
			mActivity.findViewById(R.id.pbUpdateRes).setVisibility(View.VISIBLE);
		}
		File extSdFileTmp = mActivity.getExternalFilesDir(null);	//The folder
		File extSdFilePath = new File(extSdFileTmp.getAbsolutePath() + "/res");	//Root Resource Folder
		FTPClient ftp = ftpclients[0];
		try {
			//Connecting to the FTP Client
			notifyBuilder.setContentText("Connecting to the server...");
			notifyManager.notify(notifyId, notifyBuilder.build());
			ftp.connect(serverAddr, ftpPort);
			notifyBuilder.setContentText("Connected! Logging in...");
			notifyManager.notify(notifyId, notifyBuilder.build());
			ftp.enterLocalPassiveMode();
			ftp.login(username, password);
			int reply = ftp.getReplyCode();
            //FTPReply stores a set of constants for FTP reply codes. 
			if (!FTPReply.isPositiveCompletion(reply))
            {
                ftp.disconnect();
                errorMsg = "An error occured trying to login!";
                return false;
            }
			
			notifyBuilder.setContentText("Logged in successful!");
			notifyManager.notify(notifyId, notifyBuilder.build());
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			FTPFile[] files = ftp.listFiles();
			
			notifyBuilder.setContentText("Deleting previous files...");
			notifyManager.notify(notifyId, notifyBuilder.build());
			//Delete files already existing
			File tmpFileDeleting = new File(extSdFilePath.getAbsolutePath() + System.currentTimeMillis());
			extSdFilePath.renameTo(tmpFileDeleting);
			FileUtils.deleteDirectory(tmpFileDeleting);
			extSdFilePath.mkdir();
			
			//Download new file
			notifyBuilder.setContentText("Downloading updated resource files...");
			notifyManager.notify(notifyId, notifyBuilder.build());
			start = 1;
			for (FTPFile file : files){
				if (file.isDirectory()){
					//Ignore folders
					continue;
				}
				//Loop through and download file
				OutputStream output;
				output = new FileOutputStream(extSdFilePath.getAbsolutePath() + "/" + file.getName());
				currentFile=file;
				notifyBuilder.setContentText("Downloading updated resource files " + file.getName() + " ...");
				publishProgress();
				notifyManager.notify(notifyId, notifyBuilder.build());
				ftp.retrieveFile(file.getName(), output);
				output.close();
			}
			ftp.logout();
			ftp.disconnect();
			start = 2;
			notifyBuilder.setContentText("Download completed!");
			notifyManager.notify(notifyId, notifyBuilder.build());
			publishProgress();
			return true;
		} catch (IOException e) {
			this.e=e;
			errorMsg="An error occured! (" + e.toString() + ")";
			e.printStackTrace();
	        return false;
	    } catch (Exception e){
	        this.e=e;
	        errorMsg="An error occured! (" + e.toString() + ")";
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	protected void onProgressUpdate(Void... progress) {
		if (manual){
			if (start == 1){
				UpdateResources.PlaceholderFragment.progressUpdate("Downloading updated resource files " + currentFile.getName() + " ...");
			} else if (start == 0){
				UpdateResources.PlaceholderFragment.progressUpdate("");
			} else {
				UpdateResources.PlaceholderFragment.progressUpdate("Download Complete! You can now leave this page by pressing the Back Button :D");
			}
		}
    }

	public void onPostExecute(Boolean response) {
		if (!response){
			//Error handling
			notifyBuilder.setContentText(errorMsg).setContentTitle("Error downloading resources!");
			notifyManager.notify(notifyId, notifyBuilder.build());
			Toast.makeText(mActivity.getApplication(), "An error occured. Check the notification for more info", Toast.LENGTH_LONG).show();
		} else if (e==null){
			//Success handling
			notifyBuilder.setContentText("Download complete!").setContentTitle("Resource Download Complete!");
			notifyManager.notify(notifyId, notifyBuilder.build());
			if (manual){
				mActivity.findViewById(R.id.pbUpdateRes).setVisibility(View.GONE);
			}
			Toast.makeText(mActivity.getApplication(), "Resources downloaded successfully", Toast.LENGTH_LONG).show();
		} else {
			notifyBuilder.setContentText("Something went wrong. Please report this to the dev!").setContentTitle("Unhandled Exception!");
			notifyManager.notify(notifyId, notifyBuilder.build());
			Toast.makeText(mActivity.getApplication(), "An error occured. Check the notification for more info", Toast.LENGTH_LONG).show();
		}
	}

	

}
