package iband.grademojo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.util.List;

public class FileUploadActivity extends AppCompatActivity implements View.OnClickListener {

    Button upload, attach;
    TextView FileUr;
    boolean isFile = false;

    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);
        attach = (Button) findViewById(R.id.AttachBtn);
        upload = (Button) findViewById(R.id.uploadBtn);
        FileUr = (TextView) findViewById(R.id.fileUri);
        attach.setOnClickListener(this);
        upload.setOnClickListener(this);
        upload.setClickable(false);
        PrefManager.initialise(this);


        if (!PrefManager.getPath().equals("")) {
            setPath(PrefManager.getPath());
            FileUr.setText(getPath());
            attach.setText("remove");
            upload.setClickable(true);
            isFile = true;
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.AttachBtn:

                if (isFile) {
                    FileUr.setText("");
                    attach.setText("ATTACH");
                    upload.setClickable(false);
                    isFile = false;


                } else {
                    getFilePath();

                }
                break;

            case R.id.uploadBtn:

                if (getPath().equals("")) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "File Path is null", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    uploadMultipart(FileUploadActivity.this);
                }
                break;

        }
    }

    public void uploadMultipart(final Context context) {
        try {
            String uploadId =
                    new MultipartUploadRequest(context, URL_HUB.file_upload)
                            // starting from 3.1+, you can also use content:// URI string instead of absolute file
                            .addFileToUpload(getPath(), "attached_file")
                            .addParameter("access_token", PrefManager.getAuthToken())
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .startUpload();
            Toast.makeText(context, "UpLoad id " + uploadId, Toast.LENGTH_SHORT).show();
            setPath("");
            FileUr.setText("");

        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }

    private void getFilePath() {
        Intent i = new Intent(FileUploadActivity.this, FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        i.setType("image/*|application/pdf ");
        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        startActivityForResult(i, 111);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
            List<Uri> files = Utils.getSelectedFilesFromResult(data);
            for (Uri uri : files) {
                File file = Utils.getFileForUri(uri);
                long i = file.length();
                //15 mb
                if (i > 15728640) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "File size should not exceed 15 mb", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    ;

                } else {

                    FileUr.setText(file.getAbsolutePath());
                    upload.setClickable(true);
                    // Do something with the result...
                    attach.setText("Remove");
                    isFile = true;
                    setPath(file.getAbsolutePath());
                }
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (FileUr.getText().toString().equals("")) {
            PrefManager.setPath("");

            finish();
        } else {
            PrefManager.setPath(getPath());
            finish();
        }
    }
}
