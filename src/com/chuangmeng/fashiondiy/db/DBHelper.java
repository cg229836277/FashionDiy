package com.chuangmeng.fashiondiy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	// 如果有增加表，在oncreate 直接加
	// 在升级里面少于当前版本添加
	// 永远不要管以前的版本的表
	private static int version = 10;
	// 13版本对应4.0
	private static String name = "huihenduo_db";

	public DBHelper(Context context, CursorFactory factory) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		deleteTable(db);

		createTable(db);
	}

	private void deleteTable(SQLiteDatabase db) {
		db.execSQL("drop table if exists  tb_user");
	}

	private void createTable(SQLiteDatabase db) {
		// 用户表//1版本创建表
		db.execSQL("create table if not exists tb_user(" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "username varchar," + "uid varchar,"
				+ "password varchar)");

	}
}
