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
    final Uri sourceUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private Context mContext;

    public ThumbnailsLoader(Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    public Cursor loadInBackground() {

        // get all album covers
        Cursor cursorThumbnails = mContext.getContentResolver().query(thumbUri,
                new String[] { MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA },
                "", null,
                MediaStore.Images.Thumbnails.IMAGE_ID + " DESC");

        // get all artists
        Cursor cursorImages = mContext.getContentResolver().query(sourceUri,
                new String[] { MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME },
                "", null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC");

        // create a custom cursor to mix both
        MatrixCursor cursorArtistCover
                = new MatrixCursor(new String[] { MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA,
                MediaStore.Images.Media.DISPLAY_NAME});

        return cursorThumbnails;
    }
}
