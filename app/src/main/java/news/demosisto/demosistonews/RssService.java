package news.demosisto.demosistonews;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class RssService extends IntentService {
    private SharedPreferences pref;
	public static final String ITEMS = "items";
	public static final String RECEIVER = "receiver";

	public RssService() {
		super("RssService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(Constants.TAG, "Service started");
		List<RssItem> rssItems = null;
        pref =  getSharedPreferences(getString(R.string.pref_key),1);
        //String x = pref.getAll();
        Log.v("pref_before_lastPush", pref.getString(getString(R.string.pref_str),"Error!"));
		String RSS_LINK = pref.getString(getString(R.string.pref_str),"Error!");
		try {
			RssParser parser = new RssParser();
			rssItems = parser.parse(getInputStream(RSS_LINK));
		} catch (XmlPullParserException e) {
			Log.w(e.getMessage(), e);
		} catch (IOException e) {
			Log.w(e.getMessage(), e);
		}
		Bundle bundle = new Bundle();
		bundle.putSerializable(ITEMS, (Serializable) rssItems);
		ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
		receiver.send(0, bundle);
	}

	public InputStream getInputStream(String link) {
		try {
			URL url = new URL(link);
			return url.openConnection().getInputStream();
		} catch (IOException e) {
			Log.w(Constants.TAG, "Exception while retrieving the input stream", e);
			return null;
		}
	}
}
