package nxt.com.ssllibrary.mytools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import nxt.com.ssllibrary.R;


/**
 * Created by 三石磊 on 2017/1/11.
 */

public class GlideImgLoadeUtils {
    //加载网络图片
    public static void   setHttpImg(Context context, String url, ImageView iv_img){
        if((iv_img.getTag() == null) || (!iv_img.getTag().equals(url))){
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .dontAnimate()
                    .crossFade(1000)
                    .into(iv_img);
        }
        iv_img.setTag(url);
    }
    //加载本地图片
    public static void   setLocalImg(Context context, String url, ImageView iv_img){
        System.out.println("img++++"+url);

        if((iv_img.getTag() == null) || (!iv_img.getTag().equals(url))){

//            Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT)
//                    .centerCrop().placeholder(R.drawable.default_image)
//                    .into(iv_img);


            Glide.with(context)
                    .load(new File(url))
//                    .fitCenter()
//                    .asBitmap()
//                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .dontAnimate()
                    .crossFade(1000)
                    .into(iv_img);
        }
        iv_img.setTag(url);
    }

    //加载本地图片
    public static void   setLocalImgRound(Context context, String url, ImageView iv_img){

        if((iv_img.getTag() == null) || (!iv_img.getTag().equals(url))){

            Glide.with(context)
                    .load(new File(url))
                    .thumbnail(0.5f)
                    .error(R.drawable.default_image)
                    .dontAnimate()
                    .bitmapTransform(new GlideRoundTransform(context,10))
                    .crossFade(1000)
                    .into(iv_img);
        }
        iv_img.setTag(url);
    }


}
