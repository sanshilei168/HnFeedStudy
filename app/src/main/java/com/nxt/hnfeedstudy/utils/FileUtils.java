package com.nxt.hnfeedstudy.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.nxt.hnfeedstudy.MyConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class FileUtils {

	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/" + MyConstant.FILE_NAME + "/";

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	public static void saveBitmap(Bitmap bm, String picName) {
		try {
			File f = new File(picName);
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 70, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String fileName) {
		File file = new File(SDPATH + fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete();
			else if (file.isDirectory())
				deleteDir();
		}
		dir.delete();
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	public static String getRootFilePath() {
		if (hasSDCard()) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + MyConstant.FILE_NAME + "/");
			if (!file.exists()) {
				file.mkdirs();
			}
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/" + MyConstant.FILE_NAME + "/";
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
		}
	}

	public static String getImgFilePath() {
		if (hasSDCard()) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + MyConstant.FILE_NAME + "/");
			if (!file.exists()) {
				file.mkdirs();
			}
			File voiceFile = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + MyConstant.FILE_NAME + "/image/");
			if (!voiceFile.exists()) {
				voiceFile.mkdirs();
			}
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/" + MyConstant.FILE_NAME + "/image/";
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/";
		}
	}

	public static String getVoiceFilePath() {
		if (hasSDCard()) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + MyConstant.FILE_NAME + "/");
			if (!file.exists()) {
				file.mkdirs();
			}
			File voiceFile = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + MyConstant.FILE_NAME + "/voice/");
			if (!voiceFile.exists()) {
				voiceFile.mkdirs();
			}
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/" + MyConstant.FILE_NAME + "/voice/";
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/";
		}
	}
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };
	            String path = getDataColumn(context, contentUri, selection, selectionArgs);
	            return path;
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {

	        // Return the remote address
	        if (isGooglePhotosUri(uri))
	            return uri.getLastPathSegment();

	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}
	
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
	
	public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * 删除数据库文件
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				// 设置属性:让文件可执行，可读，可写
				file.setExecutable(true, false);
				file.setReadable(true, false);
				file.setWritable(true, false);
				file.delete(); // delete()方法
			} else if (file.isDirectory()) { // 否则如果它是一个目录


				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
//					this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					if (files[i].isFile()){
						File photoFile = new File(files[i].getPath());
						photoFile.delete();
					}
				}
			}
			file.setExecutable(true, false);
			file.setReadable(true, false);
			file.setWritable(true, false);
			file.delete();
//			Log.i("deleteFile", file.getName() + "成功删除！！");
		} else {
//			Log.i("deleteFile", file.getName() + "不存在！！！");
		}
	}

}
