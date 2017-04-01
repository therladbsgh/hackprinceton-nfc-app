package ykim81.cs.brown.edu.mydsender;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity
        implements NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback{

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
      //This will refer back to createNdefMessage for what it will send
      mNfcAdapter.setNdefPushMessageCallback(this, this);

      //This will be called if the message is sent successfully
      mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
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

  @Override
  public NdefMessage createNdefMessage(NfcEvent event) {
    //This will be called when another NFC capable device is detected.
    //We'll write the createRecords() method in just a moment
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
