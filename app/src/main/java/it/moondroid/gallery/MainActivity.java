package it.moondroid.gallery;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private static final int GALLERY_LOADER_ID = 1;
    private static final int THUMB_LOADER_ID = 2;

    //define source of MediaStore.Images.Media, internal or external storage
    final Uri sourceUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    final Uri thumbUri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;

    SimpleCursorAdapter mySimpleCursorAdapter;

    GridView myGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGridView = (GridView) findViewById(R.id.gridview);


        /*
         * Initializes the CursorLoader. The URL_LOADER value is eventually passed
         * to onCreateLoader().
         */
        getLoaderManager().initLoader(THUMB_LOADER_ID, null, this);

        /** Create an adapter for the gridview */
        /** This adapter defines the data and the layout for the grid view */
//        mySimpleCursorAdapter = new SimpleCursorAdapter(
//                this,
//                R.layout.grid_item_image,
//                null,
//                new String[] { MediaStore.Images.Thumbnails.DATA} , //MediaStore.Images.Thumbnails._ID
//                new int[] { R.id.thumb},
//                0
//        );
        mySimpleCursorAdapter = new ThumbnailsAdapter(
                this,
                R.layout.grid_item_image,
                null
        );

        myGridView.setAdapter(mySimpleCursorAdapter);
        myGridView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    * Callback that's invoked when the system has initialized the Loader and
    * is ready to start the query. This usually happens when initLoader() is
    * called. The loaderID argument contains the ID value passed to the
    * initLoader() call.
    */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /*
     * Takes action based on the ID of the Loader that's being created
     */
        switch (id) {
            case GALLERY_LOADER_ID:
                // Returns a new CursorLoader
                return new CursorLoader(
                        this,            // Parent activity context
                        sourceUri,       // Table to query
                        null,            // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        MediaStore.Images.Media.DATE_TAKEN + " DESC"  // Default sort order
                );
            case THUMB_LOADER_ID:
                return new ThumbnailsLoader(this);
            default:
                // An invalid id was passed in
                return null;
        }
    }

    /*
    * Defines the callback that CursorLoader calls
    * when it's finished its query
    */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        /*
         * Moves the query results into the adapter, causing the
         * ListView fronting this adapter to re-display
         */
        mySimpleCursorAdapter.changeCursor(data);
    }

    /*
     * Invoked when the CursorLoader is being reset. For example, this is
     * called if the data in the provider changes and the Cursor becomes stale.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        /*
         * Clears out the adapter's reference to the Cursor.
         * This prevents memory leaks.
         */
        mySimpleCursorAdapter.changeCursor(null);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /** Getting the cursor object corresponds to the clicked item */
        Cursor cursor = (Cursor ) parent.getItemAtPosition(position);

        /** Getting the image_id from the cursor */
        /** image_id of the thumbnail is same as the original image id */
        String imageId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        String imageName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
        Toast.makeText(MainActivity.this, imageName, Toast.LENGTH_SHORT).show();

        /** Creating a bundle object to pass the image_id to the ImageDialog */
//        Bundle b = new Bundle();
//
//        /** Storing image_id in the bundle object */
//        b.putString(MediaStore.Images.Thumbnails.IMAGE_ID, imageId);
//
//        /** Instantiating ImageDialog, which displays the clicked image */
//        ImageDialog img = new ImageDialog();
//
//        /** Setting the bundle object to the ImageDialog */
//        img.setArguments(b);
//
//        /** Opening ImageDialog */
//        img.show(getSupportFragmentManager(), "IMAGEDIALOG");

        /** Setting uri to the original image files stored in external storage device */
//        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//        /** Creating a cursor loader from the uri corresponds to mID **/
//        CursorLoader cLoader= new CursorLoader(this, uri, null, MediaStore.Images.Media._ID + "=" + imageId , null, null);
//        Cursor imageCursor = cLoader.loadInBackground();
//        if (imageCursor != null && imageCursor.getCount() > 0) {
//            imageCursor.moveToFirst();
//            String imageName = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
//            Toast.makeText(MainActivity.this, imageName, Toast.LENGTH_SHORT).show();
//            imageCursor.close();
//        }

    }
}
