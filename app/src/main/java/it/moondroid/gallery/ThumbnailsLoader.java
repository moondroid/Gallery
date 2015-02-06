package it.moondroid.gallery;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by marco.granatiero on 06/02/2015.
 */
public class ThumbnailsLoader extends CursorLoader {

    private final Uri thumbUri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
    private final Uri sourceUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private Context mContext;

    public ThumbnailsLoader(Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    public Cursor loadInBackground() {

        // get all thumbnails
        Cursor cursorThumbnails = mContext.getContentResolver().query(thumbUri,
                new String[] { MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA },
                "", null,
                MediaStore.Images.Thumbnails.IMAGE_ID + " DESC");

        // get all images
        Cursor cursorImages = mContext.getContentResolver().query(sourceUri,
                new String[] { MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME },
                "", null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC");

        // create a custom cursor to mix both
        MatrixCursor cursorThumbImages
                = new MatrixCursor(new String[] { MediaStore.Images.Media._ID,
                MediaStore.Images.Thumbnails.DATA,
                MediaStore.Images.Media.DISPLAY_NAME});

        // join both cursor if match is found
        String thumbId, thumbData, displayName;
        boolean foundCover = false;

        if(cursorThumbnails.moveToFirst()) {
            do {
                thumbId = cursorThumbnails.getString(cursorThumbnails.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));
                thumbData = cursorThumbnails.getString(cursorThumbnails.getColumnIndex(MediaStore.Images.Thumbnails.DATA));

                if(cursorImages.moveToFirst()) {
                    foundCover = false;
                    do {
                        String imageId = cursorImages.getString(cursorImages.getColumnIndex(MediaStore.Images.Media._ID));

                        if(thumbId.equals(imageId)) {
                            foundCover = true;

                            displayName = cursorImages.getString(cursorImages.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));

                            cursorThumbImages.addRow(new Object[]{thumbId, thumbData, displayName});
                            break;
                        }

                    } while (cursorImages.moveToNext());
                }

                if(!foundCover) {
                    displayName = "";
                    cursorThumbImages.addRow(new Object[]{thumbId, thumbData, displayName});
                }

            } while (cursorThumbnails.moveToNext());
        }

        // return new custom cursor
        return cursorThumbImages;

    }
}
