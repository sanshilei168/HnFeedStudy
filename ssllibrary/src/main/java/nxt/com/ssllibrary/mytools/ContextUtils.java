package nxt.com.ssllibrary.mytools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by sysadminl on 2015/11/10.
 */
public class ContextUtils {
    private static LayoutInflater inflater;
    public static View inflate(Context context, int res){
        if(inflater==null) {
             inflater = LayoutInflater.from(context);
        }
        return inflater.inflate(res,null);
    }

}
