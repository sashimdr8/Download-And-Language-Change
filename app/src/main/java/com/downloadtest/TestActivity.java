package com.downloadtest;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.downloadtest.databinding.ActivityTestBinding;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import static com.downloadtest.MainActivity.getRootDirPath;

/**
 * Created by brain on 1/10/18.
 */

public class TestActivity extends AppCompatActivity {

    private ActivityTestBinding binding;
    int downloadId;
    DownloadRequest downloadRequest;
    String dirPath;
    String filename;
    private ThinDownloadManager downloadManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        dirPath = getRootDirPath(getApplicationContext());
        filename = "url54.mp4";
        downloadManager = new ThinDownloadManager();
        init();


        binding.btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile();
            }
        });

    }

    void init() {
        Uri downloadUri = Uri
                .parse("https://d1r3nsl6bers2.cloudfront.net/en/tte/w1_phb_tenzin_01_hd.mp4");
        Uri destinationUri = Uri.parse(this.getExternalCacheDir().toString() +  filename);
        downloadRequest = new DownloadRequest(downloadUri)
//                .addCustomHeader("Auth-Token", "YourTokenApiKey")
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {
                        Toast.makeText(TestActivity.this,
                               "Success"+id,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                        Toast.makeText(TestActivity.this,
                               errorMessage,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress)
                    {
                        Toast.makeText(TestActivity.this,
                                String.valueOf(downlaodedBytes),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void downloadFile() {
        Toast.makeText(TestActivity.this,
                "Started",
                Toast.LENGTH_SHORT).show();
        int downloadId = downloadManager.add(downloadRequest);
    }
}
