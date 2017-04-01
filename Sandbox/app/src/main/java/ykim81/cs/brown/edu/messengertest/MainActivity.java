package ykim81.cs.brown.edu.messengertest;

import java.util.List;

import android.widget.Toast;
import org.ndeftools.Message;
import org.ndeftools.Record;
import org.ndeftools.externaltype.AndroidApplicationRecord;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

  private static final String TAG = MainActivity.class.getName();

  protected NfcAdapter nfcAdapter;
  protected PendingIntent nfcPendingIntent;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // initialize NFC
    nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    if(nfcAdapter == null) {
      Toast.makeText(this, "NFC not available on this device",
              Toast.LENGTH_SHORT).show();
    }
  }

  public void enableForegroundMode() {
    Log.d(TAG, "enableForegroundMode");

    IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED); // filter for all
    IntentFilter[] writeTagFilters = new IntentFilter[] {tagDetected};
    nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, writeTagFilters, null);
  }

  public void disableForegroundMode() {
    Log.d(TAG, "disableForegroundMode");

    nfcAdapter.disableForegroundDispatch(this);
  }

  @Override
  public void onNewIntent(Intent intent) {
    Log.d(TAG, "onNewIntent");

    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
      TextView textView = (TextView) findViewById(R.id.title);

      textView.setText("Hello NFC!");

      Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
      if (messages != null) {

        Log.d(TAG, "Found " + messages.length + " NDEF messages"); // is almost always just one

        vibrate(); // signal found messages :-)

        // parse to records
        for (int i = 0; i < messages.length; i++) {
          try {
            List<Record> records = new Message((NdefMessage)messages[i]);

            Log.d(TAG, "Found " + records.size() + " records in message " + i);

            for(int k = 0; k < records.size(); k++) {
              Log.d(TAG, " Record #" + k + " is of class " + records.get(k).getClass().getSimpleName());

              Record record = records.get(k);

              if(record instanceof AndroidApplicationRecord) {
                AndroidApplicationRecord aar = (AndroidApplicationRecord)record;
                Log.d(TAG, "Package is " + aar.getDomain() + " " + aar.getType());
              }

            }
          } catch (Exception e) {
            Log.e(TAG, "Problem parsing message", e);
          }

        }
      }
    } else {
      // ignore
    }
  }

  @Override
  protected void onResume() {
    Log.d(TAG, "onResume");

    super.onResume();

    if (nfcAdapter != null) {
      enableForegroundMode();
    }
  }

  @Override
  protected void onPause() {
    Log.d(TAG, "onPause");

    super.onPause();

    if (nfcAdapter != null) {
      disableForegroundMode();
    }
  }

  private void vibrate() {
    Log.d(TAG, "vibrate");

    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
    vibe.vibrate(500);
  }


}