package ykim81.cs.brown.edu.mydsender;


import java.util.List;

import android.nfc.NdefRecord;
import android.nfc.NfcEvent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;
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

public class MainActivity extends Activity
        implements NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback{

  private static final String TAG = MainActivity.class.getName();

  private boolean vegetarian;

  protected NfcAdapter nfcAdapter;
  protected PendingIntent nfcPendingIntent;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    vegetarian = false;

    // initialize NFC
    nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    nfcPendingIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    if(nfcAdapter != null) {
      //This will refer back to createNdefMessage for what it will send
      nfcAdapter.setNdefPushMessageCallback(this, this);

      //This will be called if the message is sent successfully
      nfcAdapter.setOnNdefPushCompleteCallback(this, this);
      Toast.makeText(this, "NFC available!",
              Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "NFC not available on this device",
              Toast.LENGTH_SHORT).show();
    }
  }

  public void toggleVegetarian(View view) {
    ToggleButton myButton = (ToggleButton) findViewById(R.id.toggleButton);
    if (myButton.isChecked()) {
      vegetarian = true;
      System.out.println("Vegetarian is yes");
    } else {
      vegetarian = false;
      System.out.println("Vegetarian is no");
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
      Toast.makeText(this, "NFC intent!!!!",
              Toast.LENGTH_SHORT).show();
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

  @Override
  public NdefMessage createNdefMessage(NfcEvent event) {
    //This will be called when another NFC capable device is detected.
    //We'll write the createRecords() method in just a moment
    Toast.makeText(this, "NFC detected!!!!",
            Toast.LENGTH_SHORT).show();
    NdefRecord[] recordsToAttach = createRecords();
    //When creating an NdefMessage we need to provide an NdefRecord[]
    return new NdefMessage(recordsToAttach);
  }

  @Override
  public void onNdefPushComplete(NfcEvent event) {
    //This is called when the system detects that our NdefMessage was
    //Successfully sent.
    Toast.makeText(this, "NFC info sent!!",
            Toast.LENGTH_SHORT).show();
  }

  public NdefRecord[] createRecords() {

    NdefRecord[] records = new NdefRecord[1];

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
      // API is not high enough.
      records[0] = new NdefRecord(
              NdefRecord.TNF_WELL_KNOWN,
              NdefRecord.RTD_TEXT,
              new byte[0],
              new byte[]{(byte) (vegetarian ? 1 : 0)});
    } else {
      // API is high enough that we can use createMime, which is preferred.
      records[0] = NdefRecord.createMime("text/plain",
              new byte[]{(byte) (vegetarian ? 1 : 0)});
    }
    return records;
  }
}
