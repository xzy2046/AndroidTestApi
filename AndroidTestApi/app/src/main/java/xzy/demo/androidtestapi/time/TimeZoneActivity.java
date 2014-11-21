/*
 * Copyright (C) 2014 zhengyang.xu xuzhengyang.cn@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package xzy.demo.androidtestapi.time;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.TimeZone;

import xzy.demo.androidtestapi.R;

/**
 * @see ./base/core/tests/coretests/src/android/util/TimeUtilsTest.java
 */
public class TimeZoneActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<String>{

    private TextView mLocalTime;

    private TextView mShangHaiTime;

    private TextView mTokyoTime;

    private TextView mNetworkTime;

    private TextView mGpsTime;

    private TextView mDiffTime;

    //主要城市，国家对应表
    String[] world = new String[]{
            "ad", "Europe/Andorra",
            "ae", "Asia/Dubai",
            "af", "Asia/Kabul",
            "ag", "America/Antigua",
            "ai", "America/Anguilla",
            "al", "Europe/Tirane",
            "am", "Asia/Yerevan",
            "an", "America/Curacao",
            "ao", "Africa/Luanda",
            "aq", "Antarctica/McMurdo",
            "aq", "Antarctica/DumontDUrville",
            "aq", "Antarctica/Casey",
            "aq", "Antarctica/Davis",
            "aq", "Antarctica/Mawson",
            "aq", "Antarctica/Syowa",
            "aq", "Antarctica/Rothera",
            "aq", "Antarctica/Palmer",
            "ar", "America/Argentina/Buenos_Aires",
            "as", "Pacific/Pago_Pago",
            "at", "Europe/Vienna",
            "au", "Australia/Sydney",
            "au", "Australia/Adelaide",
            "au", "Australia/Perth",
            "au", "Australia/Eucla",
            "aw", "America/Aruba",
            "ax", "Europe/Mariehamn",
            "az", "Asia/Baku",
            "ba", "Europe/Sarajevo",
            "bb", "America/Barbados",
            "bd", "Asia/Dhaka",
            "be", "Europe/Brussels",
            "bf", "Africa/Ouagadougou",
            "bg", "Europe/Sofia",
            "bh", "Asia/Bahrain",
            "bi", "Africa/Bujumbura",
            "bj", "Africa/Porto-Novo",
            "bm", "Atlantic/Bermuda",
            "bn", "Asia/Brunei",
            "bo", "America/La_Paz",
            "br", "America/Noronha",
            "br", "America/Sao_Paulo",
            "br", "America/Manaus",
            "bs", "America/Nassau",
            "bt", "Asia/Thimphu",
            "bw", "Africa/Gaborone",
            "by", "Europe/Minsk",
            "bz", "America/Belize",
            "ca", "America/St_Johns",
            "ca", "America/Halifax",
            "ca", "America/Toronto",
            "ca", "America/Winnipeg",
            "ca", "America/Edmonton",
            "ca", "America/Vancouver",
            "cc", "Indian/Cocos",
            "cd", "Africa/Lubumbashi",
            "cd", "Africa/Kinshasa",
            "cf", "Africa/Bangui",
            "cg", "Africa/Brazzaville",
            "ch", "Europe/Zurich",
            "ci", "Africa/Abidjan",
            "ck", "Pacific/Rarotonga",
            "cl", "America/Santiago",
            "cl", "Pacific/Easter",
            "cm", "Africa/Douala",
            "cn", "Asia/Shanghai",
            "co", "America/Bogota",
            "cr", "America/Costa_Rica",
            "cu", "America/Havana",
            "cv", "Atlantic/Cape_Verde",
            "cx", "Indian/Christmas",
            "cy", "Asia/Nicosia",
            "cz", "Europe/Prague",
            "de", "Europe/Berlin",
            "dj", "Africa/Djibouti",
            "dk", "Europe/Copenhagen",
            "dm", "America/Dominica",
            "do", "America/Santo_Domingo",
            "dz", "Africa/Algiers",
            "ec", "America/Guayaquil",
            "ec", "Pacific/Galapagos",
            "ee", "Europe/Tallinn",
            "eg", "Africa/Cairo",
            "eh", "Africa/El_Aaiun",
            "er", "Africa/Asmara",
            "es", "Europe/Madrid",
            "es", "Atlantic/Canary",
            "et", "Africa/Addis_Ababa",
            "fi", "Europe/Helsinki",
            "fj", "Pacific/Fiji",
            "fk", "Atlantic/Stanley",
            "fm", "Pacific/Ponape",
            "fm", "Pacific/Truk",
            "fo", "Atlantic/Faroe",
            "fr", "Europe/Paris",
            "ga", "Africa/Libreville",
            "gb", "Europe/London",
            "gd", "America/Grenada",
            "ge", "Asia/Tbilisi",
            "gf", "America/Cayenne",
            "gg", "Europe/Guernsey",
            "gh", "Africa/Accra",
            "gi", "Europe/Gibraltar",
            "gl", "America/Danmarkshavn",
            "gl", "America/Scoresbysund",
            "gl", "America/Godthab",
            "gl", "America/Thule",
            "gm", "Africa/Banjul",
            "gn", "Africa/Conakry",
            "gp", "America/Guadeloupe",
            "gq", "Africa/Malabo",
            "gr", "Europe/Athens",
            "gs", "Atlantic/South_Georgia",
            "gt", "America/Guatemala",
            "gu", "Pacific/Guam",
            "gw", "Africa/Bissau",
            "gy", "America/Guyana",
            "hk", "Asia/Hong_Kong",
            "hn", "America/Tegucigalpa",
            "hr", "Europe/Zagreb",
            "ht", "America/Port-au-Prince",
            "hu", "Europe/Budapest",
            "id", "Asia/Jayapura",
            "id", "Asia/Makassar",
            "id", "Asia/Jakarta",
            "ie", "Europe/Dublin",
            "il", "Asia/Jerusalem",
            "im", "Europe/Isle_of_Man",
            "in", "Asia/Calcutta",
            "io", "Indian/Chagos",
            "iq", "Asia/Baghdad",
            "ir", "Asia/Tehran",
            "is", "Atlantic/Reykjavik",
            "it", "Europe/Rome",
            "je", "Europe/Jersey",
            "jm", "America/Jamaica",
            "jo", "Asia/Amman",
            "jp", "Asia/Tokyo",
            "ke", "Africa/Nairobi",
            "kg", "Asia/Bishkek",
            "kh", "Asia/Phnom_Penh",
            "ki", "Pacific/Kiritimati",
            "ki", "Pacific/Enderbury",
            "ki", "Pacific/Tarawa",
            "km", "Indian/Comoro",
            "kn", "America/St_Kitts",
            "kp", "Asia/Pyongyang",
            "kr", "Asia/Seoul",
            "kw", "Asia/Kuwait",
            "ky", "America/Cayman",
            "kz", "Asia/Almaty",
            "kz", "Asia/Aqtau",
            "la", "Asia/Vientiane",
            "lb", "Asia/Beirut",
            "lc", "America/St_Lucia",
            "li", "Europe/Vaduz",
            "lk", "Asia/Colombo",
            "lr", "Africa/Monrovia",
            "ls", "Africa/Maseru",
            "lt", "Europe/Vilnius",
            "lu", "Europe/Luxembourg",
            "lv", "Europe/Riga",
            "ly", "Africa/Tripoli",
            "ma", "Africa/Casablanca",
            "mc", "Europe/Monaco",
            "md", "Europe/Chisinau",
            "me", "Europe/Podgorica",
            "mg", "Indian/Antananarivo",
            "mh", "Pacific/Majuro",
            "mk", "Europe/Skopje",
            "ml", "Africa/Bamako",
            "mm", "Asia/Rangoon",
            "mn", "Asia/Choibalsan",
            "mn", "Asia/Hovd",
            "mo", "Asia/Macau",
            "mp", "Pacific/Saipan",
            "mq", "America/Martinique",
            "mr", "Africa/Nouakchott",
            "ms", "America/Montserrat",
            "mt", "Europe/Malta",
            "mu", "Indian/Mauritius",
            "mv", "Indian/Maldives",
            "mw", "Africa/Blantyre",
            "mx", "America/Mexico_City",
            "mx", "America/Chihuahua",
            "mx", "America/Tijuana",
            "my", "Asia/Kuala_Lumpur",
            "mz", "Africa/Maputo",
            "na", "Africa/Windhoek",
            "nc", "Pacific/Noumea",
            "ne", "Africa/Niamey",
            "nf", "Pacific/Norfolk",
            "ng", "Africa/Lagos",
            "ni", "America/Managua",
            "nl", "Europe/Amsterdam",
            "no", "Europe/Oslo",
            "np", "Asia/Katmandu",
            "nr", "Pacific/Nauru",
            "nu", "Pacific/Niue",
            "nz", "Pacific/Auckland",
            "nz", "Pacific/Chatham",
            "om", "Asia/Muscat",
            "pa", "America/Panama",
            "pe", "America/Lima",
            "pf", "Pacific/Gambier",
            "pf", "Pacific/Marquesas",
            "pf", "Pacific/Tahiti",
            "pg", "Pacific/Port_Moresby",
            "ph", "Asia/Manila",
            "pk", "Asia/Karachi",
            "pl", "Europe/Warsaw",
            "pm", "America/Miquelon",
            "pn", "Pacific/Pitcairn",
            "pr", "America/Puerto_Rico",
            "ps", "Asia/Gaza",
            "pt", "Europe/Lisbon",
            "pt", "Atlantic/Azores",
            "pw", "Pacific/Palau",
            "py", "America/Asuncion",
            "qa", "Asia/Qatar",
            "re", "Indian/Reunion",
            "ro", "Europe/Bucharest",
            "rs", "Europe/Belgrade",
            "ru", "Asia/Kamchatka",
            "ru", "Asia/Magadan",
            "ru", "Asia/Vladivostok",
            "ru", "Asia/Yakutsk",
            "ru", "Asia/Irkutsk",
            "ru", "Asia/Krasnoyarsk",
            "ru", "Asia/Novosibirsk",
            "ru", "Asia/Yekaterinburg",
            "ru", "Europe/Samara",
            "ru", "Europe/Moscow",
            "ru", "Europe/Kaliningrad",
            "rw", "Africa/Kigali",
            "sa", "Asia/Riyadh",
            "sb", "Pacific/Guadalcanal",
            "sc", "Indian/Mahe",
            "sd", "Africa/Khartoum",
            "se", "Europe/Stockholm",
            "sg", "Asia/Singapore",
            "sh", "Atlantic/St_Helena",
            "si", "Europe/Ljubljana",
            "sj", "Arctic/Longyearbyen",
            "sk", "Europe/Bratislava",
            "sl", "Africa/Freetown",
            "sm", "Europe/San_Marino",
            "sn", "Africa/Dakar",
            "so", "Africa/Mogadishu",
            "sr", "America/Paramaribo",
            "st", "Africa/Sao_Tome",
            "sv", "America/El_Salvador",
            "sy", "Asia/Damascus",
            "sz", "Africa/Mbabane",
            "tc", "America/Grand_Turk",
            "td", "Africa/Ndjamena",
            "tf", "Indian/Kerguelen",
            "tg", "Africa/Lome",
            "th", "Asia/Bangkok",
            "tj", "Asia/Dushanbe",
            "tk", "Pacific/Fakaofo",
            "tl", "Asia/Dili",
            "tm", "Asia/Ashgabat",
            "tn", "Africa/Tunis",
            "to", "Pacific/Tongatapu",
            "tr", "Europe/Istanbul",
            "tt", "America/Port_of_Spain",
            "tv", "Pacific/Funafuti",
            "tw", "Asia/Taipei",
            "tz", "Africa/Dar_es_Salaam",
            "ua", "Europe/Kiev",
            "ug", "Africa/Kampala",
            "um", "Pacific/Wake",
            "um", "Pacific/Johnston",
            "um", "Pacific/Midway",
            "us", "America/New_York",
            "us", "America/Chicago",
            "us", "America/Denver",
            "us", "America/Los_Angeles",
            "us", "America/Anchorage",
            "us", "Pacific/Honolulu",
            "uy", "America/Montevideo",
            "uz", "Asia/Tashkent",
            "va", "Europe/Vatican",
            "vc", "America/St_Vincent",
            "ve", "America/Caracas",
            "vg", "America/Tortola",
            "vi", "America/St_Thomas",
            "vn", "Asia/Saigon",
            "vu", "Pacific/Efate",
            "wf", "Pacific/Wallis",
            "ws", "Pacific/Apia",
            "ye", "Asia/Aden",
            "yt", "Indian/Mayotte",
            "za", "Africa/Johannesburg",
            "zm", "Africa/Lusaka",
            "zw", "Africa/Harare",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_zone);
        initViews();
        getLoaderManager().initLoader(0, null, this);
//        getLoaderManager().getLoader(0).startLoading();


    }

    private void initViews() {
        mLocalTime = (TextView) findViewById(R.id.local_time_content);
        mShangHaiTime = (TextView) findViewById(R.id.shanghai_time_content);
        mTokyoTime = (TextView) findViewById(R.id.tokyo_time_content);
        mGpsTime = (TextView) findViewById(R.id.gps_time_content);
        mNetworkTime = (TextView) findViewById(R.id.network_time_content);
        mDiffTime = (TextView) findViewById(R.id.diff_time_content);
    }

    public void refreshTime(View view) {
        Time local = new Time();
        local.set(System.currentTimeMillis());
        mLocalTime.setText(local.format2445());

        Time shanghai = new Time("Asia/Shanghai");
        shanghai.set(System.currentTimeMillis());
        mShangHaiTime.setText(shanghai.format2445());

        Time tokyo = new Time("Asia/Tokyo");
        tokyo.set(System.currentTimeMillis());
        mTokyoTime.setText(tokyo.format2445());

        getLoaderManager().getLoader(0).startLoading();

        getGPSTime();

        mDiffTime.setText(calTimeOffset(new Date())); //本机当前时期和北京时区的差值，可以通过这个值算出和网络时间和本机北京时间的差值的差值。
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.time_zone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getGPSTime() {
        LocationManager locMan = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                long time = location.getTime();
                Date date = new Date(time);
                mGpsTime.setText(date.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private String calTimeOffset(Date date) {

        long timezoneDiff = TimeZone.getDefault().getOffset(date.getTime()) - TimeZone
                .getTimeZone("GMT+8").getOffset(date.getTime());
        return String.valueOf(timezoneDiff);
    }

    //use for netTime loader
    @Override
    public android.content.Loader<String> onCreateLoader(int id, Bundle args) {
        Log.i("xzy", "onCreateLoader");
        return new NetTimeLoader(this);
    }

    @Override
    public void onLoadFinished(android.content.Loader<String> loader, String data) {
        Log.i("xzy", "onLoadFinished");
        mNetworkTime.setText(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<String> loader) {
        Log.i("xzy", "onLoaderReset ");
    }


    private static class NetTimeLoader extends AsyncTaskLoader<String> {

        public NetTimeLoader(Context context) {
            super(context);
        }

        @Override
        public String loadInBackground() {
            Log.i("xzy", "loadInBackground");
            URL url = null;
            try {
                url = new URL("http://www.baidu.com");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            URLConnection uc = null;
            try {
                uc = url.openConnection();
                uc.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            long ld = uc.getDate();
            Date date = new Date(ld);
            Log.i("xzy", "Net Time is : " + date.getHours() + "时" + date.getMinutes() + "分" + date.getSeconds() + "秒");
            return date.toString();
        }

        @Override
        protected String onLoadInBackground() {
            Log.i("xzy", "onLoadInBackground");
            return super.onLoadInBackground();
        }

        @Override
        protected void onStartLoading() {
            Log.i("xzy", "onStartLoading");
            forceLoad();
            super.onStartLoading();
        }
    }
}
