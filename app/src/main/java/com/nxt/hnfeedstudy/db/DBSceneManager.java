package com.nxt.hnfeedstudy.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.nxt.hnfeedstudy.R;
import com.nxt.hnfeedstudy.bean.SceneTestInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 考试题题库
 */
public class DBSceneManager {
	private final int BUFFER_SIZE = 1024;
	public static final String DB_NAME = "scene.db";//数据库名称
	public static final String PACKAGE_NAME = "com.nxt.hnfeedstudy";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME;
	private SQLiteDatabase database;
	private Context context;
	private File file = null;
	private static DBSceneManager instance = null;
	private String tableName = "AIS_PLUGIN_SCENEDIRE";//表名
	private String myItem = "";// 审核的标题
    private int item_index = 0;
	private  Map<String,String> myMap = new HashMap<>();



	public DBSceneManager(Context context) {
		this.context = context;
	}

	public static DBSceneManager getInstance(Context context) {
		if (instance != null) {
			instance = null;
		}
		instance = new DBSceneManager(context);
		return instance;
	}

	public void openDatabase() {
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}

	public SQLiteDatabase getDatabase() {
		return this.database;
	}

	private SQLiteDatabase openDatabase(String dbfile) {
		try {
			file = new File(dbfile);
				if (!file.exists()) {
				InputStream is = context.getResources().openRawResource(
						R.raw.scene);
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
					fos.flush();
				}
				fos.close();
				is.close();
			}
			database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
			return database;
		} catch (FileNotFoundException e) {
			Log.e("cc", "File not found");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("cc", "IO exception");
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("cc", "exception " + e.toString());
		}
		return null;
	}

	public void closeDatabase() {
		if (this.database != null)
			this.database.close();
	}

	/**
	 * 删除数据库文件
	 */
	public void deleteFile() {
		File file = new File(DB_PATH + "/" + DB_NAME);
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
			Log.i("deleteFile", file.getName() + "成功删除！！");
		} else {
			Log.i("deleteFile", file.getName() + "不存在！！！");
		}
	}


	/*
	模糊查询
	 */
//	public LocalCityInfo getCityByName(String cityName) {
//		openDatabase();
//		LocalCityInfo city = null;
//		String sql = "select * from AIS_PLUGIN_QUESTIONINFO where city like '%"+cityName+"%'";
//		Cursor cursor = database.rawQuery(sql, null);
//		while (cursor.moveToNext()) {
//			String id = cursor.getString(cursor.getColumnIndex("cityid"));
//			String name = cursor.getString(cursor.getColumnIndex("city"));
//			city = new LocalCityInfo(id,name);
//		}
//		closeDatabase();
//		return city;
//	}
	
	/*根据id查 本条信息*/
	public SceneTestInfo getInfoId(String infoId) {
		openDatabase();
		SceneTestInfo info = null;
		String sql = "select * from AIS_PLUGIN_SCENEDIRE where ID = "+infoId ;
		Cursor cursor = database.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			info = getDetailInfo(cursor);
		}
		closeDatabase();
		return info;
	}


	/**
	 * 审核饲料的类型
	 * 1：浓缩饲料、配合饲料、精料补充料   2：添加剂预混合饲料   3：饲料添加剂  4：混合型饲料添加剂  5：单一饲料
	 * @param questionType
	 * @return
     */
	public ArrayList<SceneTestInfo> getListSceneInfo(int questionType) {
		openDatabase();
		getListSceneInfo(questionType,0);
		ArrayList<SceneTestInfo> listInfos = new ArrayList<SceneTestInfo>();
		String sql = "select * from AIS_PLUGIN_SCENEDIRE where TYPE = ? "+" ORDER BY SEQ , SERIANUMBER";
		Cursor cursor = database.rawQuery(sql, new  String []{questionType+""} );
		while (cursor.moveToNext()) {

			SceneTestInfo info =   getDetailInfo(cursor);
			String mPid = info.getPid();
			if(mPid != null && !"0".equals(mPid)){
			info.setItem(myMap.get(mPid));
			listInfos.add(info);
			}
		}
		closeDatabase();
		item_index = 0;
		return listInfos;
	}

    /*
    先获取所有的标题
     */
	public void getListSceneInfo(int questionType,int pid) {
		String sql = "select * from AIS_PLUGIN_SCENEDIRE where TYPE = ? and PID = ?"+" ORDER BY SEQ , SERIANUMBER";
		Cursor cursor = database.rawQuery(sql, new  String []{questionType+"",pid+""} );
		while (cursor.moveToNext()) {
			SceneTestInfo info =   getDetailInfo(cursor);
			String mItem = info.getItem();
			if(mItem != null && !"null".equals(mItem) &&!"".equals(mItem)){
//				item_index++;
					myMap.put(info.getId(),info.getSeriaNumber()+" "+info.getItem());
			}
		}
	}
	private SceneTestInfo getDetailInfo(Cursor cursor) {
		SceneTestInfo info = new SceneTestInfo();
		info.setId(cursor.getString(cursor.getColumnIndex("ID")));
		info.setPid(cursor.getString(cursor.getColumnIndex("PID")));
		info.setItem(cursor.getString(cursor.getColumnIndex("ITEM")));
		info.setSeriaNumber(cursor.getString(cursor.getColumnIndex("SERIANUMBER")));
		info.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));
		info.setProcess(cursor.getString(cursor.getColumnIndex("PROCESS")));
		info.setRequire(cursor.getString(cursor.getColumnIndex("REQUIRE")));
		info.setPhotograph(cursor.getString(cursor.getColumnIndex("PHOTOGRAPH")));
		info.setHint(cursor.getString(cursor.getColumnIndex("HINT")));
		info.setType(cursor.getInt(cursor.getColumnIndex("TYPE")));
		info.setIndate(cursor.getString(cursor.getColumnIndex("INDATE")));
		info.setModifyDate(cursor.getString(cursor.getColumnIndex("MODIFYDATE")));
		info.setUserName(cursor.getString(cursor.getColumnIndex("USERNAME")));
		info.setAuditrole(cursor.getString(cursor.getColumnIndex("AUDITROLE")));
		return info;
	}

}