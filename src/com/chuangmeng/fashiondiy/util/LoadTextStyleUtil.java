package com.chuangmeng.fashiondiy.util;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.chuangmeng.fashiondiy.base.FashionDiyApplication;

/**
 * 
 * author:陈刚
 * descript:加载字体
 */
@SuppressLint("NewApi") 
public class LoadTextStyleUtil {
	public void loadTextStyle(String textFontName, TextView textView) {
		if(StringUtil.isEmpty(textFontName) || textView == null){
			return;
		}
		Typeface typeFace = getTypefaceFromCache(textFontName);
		if (typeFace == null) {
			startAsyncTask(textFontName, textView);
		} else {
			loadCacheTypeface(textFontName, textView, typeFace);
		}
	}
	
	public void startAsyncTask(String resPath, TextView textView){
        if (cancelPotentialWork(resPath, textView)) {
            final TypefaceWorkerTask task = new TypefaceWorkerTask(textView);
//            Resources resource = FashionDiyApplication.getApplicationInstance().getResources();
            Typeface defaultFont = Typeface.DEFAULT;
            final AsyncDrawable asyncTypeFace = new AsyncDrawable(task);
            textView.setBackground(asyncTypeFace);
            textView.setTypeface(defaultFont);
            textView.setTag(resPath);
            task.execute(resPath);
        }
	}
	
	private void loadCacheTypeface(String resPath,TextView textView , Typeface cachetypeFace){
		textView.setVisibility(View.VISIBLE);
		cancelPotentialWork(resPath, textView);
		textView.setTag(resPath);
		textView.setTypeface(cachetypeFace);
	}

    public static class AsyncDrawable extends BitmapDrawable{
    	
    	private final WeakReference<TypefaceWorkerTask> typeFaceWorkerTaskReference;
    	
    	@SuppressWarnings("deprecation")
		public AsyncDrawable(TypefaceWorkerTask typeFace) {
    		typeFaceWorkerTaskReference = new WeakReference<TypefaceWorkerTask>(typeFace);
		}


        public TypefaceWorkerTask getTypefaceWorkerTask() {
            return typeFaceWorkerTaskReference.get();
        }
    }

	public static boolean cancelPotentialWork(String resSource,TextView textView) {
		TypefaceWorkerTask bitmapDownloaderTask = getTypefaceWorkerTask(textView);
		if (bitmapDownloaderTask != null) {
			String bitmapPath = bitmapDownloaderTask.resPath;
			if ((bitmapPath == null) || (!bitmapPath.equals((String) resSource))) {
				bitmapDownloaderTask.cancel(true);
			} else {
				// The same URL is already being downloaded.
				return false;
			}
		}
		return true;
	}

    private static TypefaceWorkerTask getTypefaceWorkerTask(TextView textView) {
       if (textView != null) {
           final Drawable drawable = textView.getBackground();
           if (drawable instanceof AsyncDrawable) {
               final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
               return asyncDrawable.getTypefaceWorkerTask();
           }
        }
        return null;
    }
    
    class TypefaceWorkerTask extends AsyncTask<String, Void, Typeface> {
    	private final WeakReference<TextView> textViewReference;
    	private String resPath;
    	private Context context;
    	
	    public TypefaceWorkerTask(TextView textView) {
	        // Use a WeakReference to ensure the ImageView can be garbage collected
	        textViewReference = new WeakReference<TextView>(textView);
	        context = FashionDiyApplication.getInstance().getApplicationContext();
	    }
        @Override
        protected void onPostExecute(Typeface typeFace) {
            if (isCancelled()) {
            	typeFace = null;
            }                      

            if (textViewReference != null && typeFace != null) {
                final TextView textView = textViewReference.get();
                final TypefaceWorkerTask TypefaceWorkerTask = getTypefaceWorkerTask(textView);
                if (this == TypefaceWorkerTask && textView != null) {
                	if(textView.getTag() instanceof String){
	                	addBitmapToCache(resPath, typeFace);//添加到缓存
	                	if(resPath.equals((String)textView.getTag())){
	                		textView.setTypeface(typeFace);
	                	}
                	}
                }
            }
        }

		@Override
		protected Typeface doInBackground(String... arg0) {
				resPath = arg0[0];
				Typeface textFontFace = Typeface.createFromAsset(context.getAssets(), resPath);
				if(textFontFace != null){
					return textFontFace;
				}
			return null;
		}
    }
    
    private static final int HARD_CACHE_CAPACITY = 10;
	 private static final int DELAY_BEFORE_PURGE = 10 * 1000; // in milliseconds

	   // Hard cache, with a fixed maximum capacity and a life duration
	   private final HashMap<String, Typeface> sHardTypeFaceCache =
	       new LinkedHashMap<String, Typeface>(HARD_CACHE_CAPACITY / 2, 0.75f, true) {
	       @Override
	       protected boolean removeEldestEntry(LinkedHashMap.Entry<String, Typeface> eldest) {
	           if (size() > HARD_CACHE_CAPACITY) {
	               // Entries push-out of hard reference cache are transferred to soft reference cache
	               sSoftTypeFaceCache.put(eldest.getKey(), new SoftReference<Typeface>(eldest.getValue()));
	               return true;
	           } else
	               return false;
	       }
	   };

	   // Soft cache for bitmaps kicked out of hard cache
	   private final static ConcurrentHashMap<String, SoftReference<Typeface>> sSoftTypeFaceCache =
	       new ConcurrentHashMap<String, SoftReference<Typeface>>(HARD_CACHE_CAPACITY / 2);
	   
	   /**
	    * Adds this bitmap to the cache.
	    * @param bitmap The newly downloaded bitmap.
	    */
	   private void addBitmapToCache(String url, Typeface typeFace) {
	       if (typeFace != null) {
	           synchronized (sHardTypeFaceCache) {
	               sHardTypeFaceCache.put(url, typeFace);
	           }
	       }
	   }
	   
	   /**
	    * @param url The URL of the image that will be retrieved from the cache.
	    * @return The cached bitmap or null if it was not found.
	    */
	   private Typeface getTypefaceFromCache(String url) {
	       // First try the hard reference cache
	       synchronized (sHardTypeFaceCache) {
	           final Typeface typeFace = sHardTypeFaceCache.get(url);
	           if (typeFace != null) {
	               // Bitmap found in hard cache
	               // Move element to first position, so that it is removed last
	               sHardTypeFaceCache.remove(url);
	               sHardTypeFaceCache.put(url, typeFace);
	               return typeFace;
	           }
	       }

	       // Then try the soft reference cache
	       SoftReference<Typeface> bitmapReference = sSoftTypeFaceCache.get(url);
	       if (bitmapReference != null) {
	           final Typeface typeface = bitmapReference.get();
	           if (typeface != null) {
	               // Bitmap found in soft cache
	               return typeface;
	           } else {
	               // Soft reference has been Garbage Collected
	               sSoftTypeFaceCache.remove(url);
	           }
	       }

	       return null;
	   }
	   
	   public void clearImageCache(){
		   if(sSoftTypeFaceCache != null && sSoftTypeFaceCache.size() > 0){
			   sSoftTypeFaceCache.clear();
		   }
		   if(sHardTypeFaceCache != null && sHardTypeFaceCache.size() > 0){
			   sHardTypeFaceCache.clear();
		   }
	   }
}
