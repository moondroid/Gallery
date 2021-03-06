package it.moondroid.gallery;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by marco.granatiero on 06/02/2015.
 */
public class ThumbnailsAdapter extends SimpleCursorAdapter {

    private Context context;
    private int layout;
    private String[] from = new String[] { MediaStore.Images.Thumbnails.DATA};
    private int[] to = new int[] { R.id.thumb};

    public ThumbnailsAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c, new String[0], new int[0], 0);
        this.layout = layout;
        this.context = context;
    }

    public ThumbnailsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layout, parent, false);

        setData(v, cursor);

        return v;
    }

    @Override
    public void bindView(View v, Context context, Cursor cursor) {
        setData(v, cursor);
    }


    private void setData(View v, Cursor cursor){

        String thumbPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
        String imageName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));

        TextView name_text = (TextView) v.findViewById(R.id.img_name);
        name_text.setText(imageName);

        ImageView thumbV = (ImageView) v.findViewById(R.id.thumb);
        thumbV.setImageBitmap(BitmapFactory.decodeFile(thumbPath));
    }

}
