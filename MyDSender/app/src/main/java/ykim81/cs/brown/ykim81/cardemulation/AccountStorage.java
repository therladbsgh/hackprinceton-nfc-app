/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ykim81.cs.brown.ykim81.cardemulation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Utility class for persisting account numbers to disk.
 *
 * <p>The default SharedPreferences instance is used as the backing storage. Values are cached
 * in memory for performance.
 *
 * <p>This class is thread-safe.
 */
public class AccountStorage {

    private static final String PREF_ID = "-1";
    private static final String DEFAULT_ID = ""
            + (int) Math.random() * 100000000;
    private static String sId = null;

    private static final String PREF_NAME = "Placeholder";
    private static final String DEFAULT_NAME = "Bob";
    private static String sName = null;

    private static final String PREF_BLIND = "F";
    private static final String DEFAULT_BLIND = "F";
    private static String sBlind = null;

    private static final String PREF_DEAF = "F";
    private static final String DEFAULT_DEAF = "F";
    private static String sDeaf = null;

    private static final String PREF_ACCOUNT_NUMBER = "account_number";
    private static final String DEFAULT_ACCOUNT_NUMBER = "00000000";
    private static final String TAG = "AccountStorage";
    private static String sAccount = null;
    private static final Object sAccountLock = new Object();

    public static void setId(Context c, String s) {
        synchronized(sAccountLock) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_ID, s).commit();
            sId = s;
        }
    }

    public static String getId(Context c) {
        synchronized (sAccountLock) {
            if (sId == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String name = prefs.getString(PREF_ID, DEFAULT_ID);
                sId = name;
            }
            return sId;
        }
    }

    public static void setName(Context c, String s) {
        synchronized(sAccountLock) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_NAME, s).commit();
            sName = s;
        }
    }

    public static String getName(Context c) {
        synchronized (sAccountLock) {
            if (sName == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String name = prefs.getString(PREF_NAME, DEFAULT_NAME);
                sName = name;
            }
            return sName;
        }
    }

    public static void setBlind(Context c, String s) {
        synchronized(sAccountLock) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_BLIND, s).commit();
            sBlind = s;
        }
    }

    public static String getBlind(Context c) {
        synchronized (sAccountLock) {
            if (sBlind == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String blind = prefs.getString(PREF_BLIND, DEFAULT_BLIND);
                sBlind = blind;
            }
            return sBlind;
        }
    }

    public static void setDeaf(Context c, String s) {
        synchronized(sAccountLock) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_DEAF, s).commit();
            sDeaf = s;
        }
    }

    public static String getDeaf(Context c) {
        synchronized (sAccountLock) {
            if (sDeaf == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String deaf = prefs.getString(PREF_DEAF, DEFAULT_DEAF);
                sDeaf = deaf;
            }
            return sDeaf;
        }
    }
}
