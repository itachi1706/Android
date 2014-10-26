package com.itachi1706.minecrafttools;

import java.io.File;
import java.util.ArrayList;

import com.itachi1706.minecrafttools.Database.McItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class McItemListAdapter extends ArrayAdapter<McItem>{
	
	// declaring our ArrayList of items
		private ArrayList<McItem> objects;
		private Context act;
		
		/* here we must override the constructor for ArrayAdapter
		* the only variable we care about now is ArrayList<Item> objects,
		* because it is the list of objects we want to display.
		*/
		public McItemListAdapter(Context context, int textViewResourceId, ArrayList<McItem> objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
			this.act = context;
		}
		
		/*
		 * we are overriding the getView method here - this is what defines how each
		 * list item will look.
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			// assign the view we are converting to a local variable
			View v = convertView;

			// first check to see if the view is null. if so, we have to inflate it.
			// to inflate it basically means to render, or show, the view.
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.listview_mc_item_row, parent, false);
			}

			/*
			 * Recall that the variable position is sent in as an argument to this method.
			 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
			 * iterates through the list we sent it)
			 * 
			 * Therefore, i refers to the current Item object.
			 */
			McItem i = objects.get(position);
			
			File extSdFileTmp = act.getExternalFilesDir(null);	//The folder
			File extSdFilePath = new File(extSdFileTmp.getAbsolutePath() + "/res");	//Root Resource Folder

			if (i != null) {

				// This is how you obtain a reference to the TextViews.
				// These TextViews are created in the XML files we defined.

				TextView itemName = (TextView) v.findViewById(R.id.tvItem);	
				TextView itemID = (TextView) v.findViewById(R.id.tvItemID);	
				ImageView image = (ImageView) v.findViewById(R.id.ivItem);	
				TextView itemIDName = (TextView) v.findViewById(R.id.tvItemIDName);

				// check to see if each individual textview is null.
				// if not, assign some text!
				//Server Name
				if (itemName != null){
					itemName.setText(i.getName());
				}
				if (itemID != null){
					if (i.getSubId() != 0){
						itemID.setText(i.getId() + ":" + i.getSubId());
					} else {
						itemID.setText(i.getId() + "");
					}
				}
				if (itemIDName != null){
					itemIDName.setText(i.getMinecraftIdName());
				}
				if (image != null){
					File imgFile = new File(extSdFilePath.getAbsolutePath() + File.separator + i.getImageURL());
					if (imgFile.exists()){
						
					} else {
						//Use unknown texture
						imgFile = new File(extSdFilePath.getAbsolutePath() + File.separator + "missingtexture.png");
						
					}
					image.setImageDrawable(Drawable.createFromPath(imgFile.getAbsolutePath()));
					scaleImage(image, 150);
				}
				
			}
			return v;

		}
		
		@SuppressWarnings("deprecation")
		private void scaleImage(ImageView view, int boundBoxInDp)
		{
		    // Get the ImageView and its bitmap
		    Drawable drawing = view.getDrawable();
		    Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

		    // Get current dimensions
		    int width = bitmap.getWidth();
		    int height = bitmap.getHeight();

		    // Determine how much to scale: the dimension requiring less scaling is
		    // closer to the its side. This way the image always stays inside your
		    // bounding box AND either x/y axis touches it.
		    float xScale = ((float) boundBoxInDp) / width;
		    float yScale = ((float) boundBoxInDp) / height;
		    float scale = (xScale <= yScale) ? xScale : yScale;

		    // Create a matrix for the scaling and add the scaling data
		    Matrix matrix = new Matrix();
		    matrix.postScale(scale, scale);

		    // Create a new bitmap and convert it to a format understood by the ImageView
		    Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		    BitmapDrawable result = new BitmapDrawable(scaledBitmap);
		    width = scaledBitmap.getWidth();
		    height = scaledBitmap.getHeight();

		    // Apply the scaled bitmap
		    view.setImageDrawable(result);

		    // Now change ImageView's dimensions to match the scaled image
		    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		    params.width = width;
		    params.height = height;
		    view.setLayoutParams(params);
		}

		@SuppressWarnings("unused")
		private int dpToPx(int dp)
		{
		    float density = act.getResources().getDisplayMetrics().density;
		    return Math.round((float)dp * density);
		}

}
