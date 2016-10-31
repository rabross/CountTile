package com.rabross.android.counttile;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.preference.PreferenceManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

/**
 * Created by robertross on 31/10/2016.
 */

public class CountTileService extends TileService{

    private static final String KEY_COUNT = "count";

    private SharedPreferences mPrefs;

    @Override
    public void onClick() {
        super.onClick();

        int count  = mPrefs.getInt(KEY_COUNT, 0);
        Bitmap icon = drawTextToBitmap(String.valueOf(++count));

        Tile tile = getQsTile();
        tile.setIcon(Icon.createWithBitmap(icon));
        tile.setState(Tile.STATE_ACTIVE);
        tile.updateTile();

        icon.recycle();
        mPrefs.edit().putInt(KEY_COUNT, count).apply();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private Bitmap drawTextToBitmap(String gText) {
        Bitmap bitmap = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);

        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);

        canvas.drawText(gText,
                (bitmap.getWidth() - bounds.width())/2,
                (bitmap.getHeight() + bounds.height())/2,
                paint);

        return bitmap;
    }
}
