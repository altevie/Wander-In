package altevie.wanderin.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.estimote.indoorsdk_module.cloud.LocationPosition;

import java.util.ArrayList;

import altevie.wanderin.R;

public class PathView extends View {

    private Path mPath = new Path();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int viewHigh;
    private int viewWidth;
    private double centerX;
    private double centerY;
    private double locationWidth = 12.1;
    private double locationHeight = 16.1;
    private Double scale;
    private int mLocationPadding = 10;

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        updateDrawParams();
        drawPath(canvas);
    }

    private void drawPath(Canvas canvas){
        if(this.mPath != null) {
            canvas.drawPath(this.mPath, this.mPaint );
        }
    }

    public void setPath(ArrayList<LocationPosition> lp){
        updateDrawParams();
        this.mPath.reset();
        int j = 0;
        while(lp.size() > j){
            this.mPath.moveTo(realXtoViewX(lp.get(j).getX()),realYtoViewY(lp.get(j).getY()));
            Log.i("MOVE_TO", realXtoViewX(lp.get(j).getX()).toString()+"|"+realYtoViewY(lp.get(j).getY()).toString());
            this.mPath.lineTo(realXtoViewX(lp.get(j+1).getX()),realYtoViewY(lp.get(j+1).getY()));
            Log.i("LINE_TO", realXtoViewX(lp.get(j+1).getX()).toString()+"|"+realYtoViewY(lp.get(j+1).getY()).toString());
            j += 2;
        }

        this.mPaint.setColor(getResources().getColor(R.color.GreenYellow));
        this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaint.setStrokeWidth(30f);
        invalidate();
    }

    public void unsetPath(){
        this.mPath.reset();
        invalidate();
    }

    private void updateDrawParams(){
        this.viewWidth = getWidth() - (getPaddingLeft() + getPaddingRight());
        this.viewHigh = getHeight() - (getPaddingTop() + getPaddingBottom());
        this.centerX = getPaddingLeft() + this.viewWidth / 2.0;
        this.centerY = getPaddingTop() + this.viewHigh / 2.0;
        this.scale = findScaleForViewOrientation(mLocationPadding);
    }

    private Double findScaleForViewOrientation(int thresholdPercentage){
        double thresholdX = this.viewWidth * thresholdPercentage / 100;
        double scaleX = (this.viewWidth - thresholdX) / this.locationWidth;
        double thresholdY = this.viewHigh * thresholdPercentage / 100;
        double scaleY = (this.viewHigh - thresholdY) / this.locationHeight;
        return Math.min(scaleX, scaleY);
    }

    private Float realXtoViewX(Double x){
        double vectorToCenterX = this.centerX - this.locationWidth / 2 * this.scale;
        Double res = x * this.scale + vectorToCenterX;
        return res.floatValue();
    }

    private Float realYtoViewY(Double y){
        double vectorToCenterY = this.centerY - this.locationHeight / 2 * this.scale;
        Double res = this.viewHigh - (y * this.scale + vectorToCenterY);
        return res.floatValue();
    }
}
