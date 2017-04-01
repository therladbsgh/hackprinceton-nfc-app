package ykim81.cs.brown.edu.mydreceiver;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity
        implements NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback {

  private boolean vegetarian;
  private NfcAdapter mNfcAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    vegetarian = false;

    //Check if NFC is available on device
    mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    if(mNfcAdapter != null) {
      //Handle some NFC initialization here
      handleNfcIntent(getIntent());
    } else {
      Toast.makeText(this, "NFC not available on this device",
              Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public NdefMessage createNdefMessage(NfcEvent event) {
    return null;
  }

  @Override
  public void onNdefPushComplete(NfcEvent event) {

  }

  @Override
  public void onResume() {
    super.onResume();
    handleNfcIntent(getIntent());
  }

  private void handleNfcIntent(Intent NfcIntent) {
    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
      Parcelable[] receivedArray =
              NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
      Toast.makeText(this, "Received NFC!",
              Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onNewIntent(Intent intent) {
    handleNfcIntent(intent);
  }
}
