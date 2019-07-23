package com.example.caipuandroid.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import com.infoholdcity.baselibrary.BaseApplaction;


@Database(entities = {CategoryEntity.class, CollectEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    public abstract CaipuDao CaipuDao();

    public abstract CollectDao CollectDao();

    private static AppDatabase INSTANCE;
    private static final Object sLock = new Object();

    public static AppDatabase getInstance() {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(BaseApplaction.Companion.getContext(),
                        AppDatabase.class, "test.db")
                        .fallbackToDestructiveMigration() //强制升级 删除之前数据库数据
//                                .addMigrations(MIGRATION_1_2,MIGRATION_2_3,MIGRATION_3_4)//数据库升级
                        .build();

            }
            return INSTANCE;
        }
    }


    public static CaipuDao getCaipuDao() {
        return getInstance().CaipuDao();
    }

    public static CollectDao getCollectDao() {
        return getInstance().CollectDao();
    }

}
