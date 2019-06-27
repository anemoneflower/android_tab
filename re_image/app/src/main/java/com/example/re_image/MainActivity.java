package com.example.re_image;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgMain;
    private Button btnCamera, btnAlbum;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_FROM_CAMERA = 3;

    private String mCurrentPath;
    private Uri imageURI;
    private Uri photoURI, albumURI;

    //권한체크를 위한 메세지
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static final int MULTIPLE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 갤러리에 폴더 추가  */
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/madcamp");
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        checkPermissions();
        initView();
    }

    //권한체크용 함수 퍼미션리스트를 받아서 각각의 권한을 부여받았는지 체크
    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //권한이 없을때 띄워주는 함수
    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    //버튼활성화
    private void initView() {
        imgMain = findViewById(R.id.img_test);
        btnCamera = findViewById(R.id.btn_camera);
        btnAlbum = findViewById(R.id.btn_album);

        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
    }

    //버튼 눌렀을 경우
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_album:
                goToAlbum();
                break;
            case R.id.btn_camera:
                takePhoto();
                break;
        }
    }

    //앨범에서 사진을 고를 때
    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    //사진을 찍을때 - content와 file 경로의 차이를 확실히 파악
    private void takePhoto() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(MainActivity.this, "com.example.re_image.provider", photoFile);
                    imageURI = providerURI;
                    //인텐트에 전달할때는 content로 구성된 uri를 보내야한다
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, PICK_FROM_CAMERA);
                }
            }
        } else {
            Toast.makeText(MainActivity.this, "공간 접근 불가.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //새로운 파일 이름 만들기
    private File createImageFile() throws IOException {
        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(mDate);
        String imageFileName = "madcamp_" + timeStamp + ".PNG";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/madcamp");

        //이미 madcamp 폴더는 존재하므로 예외처리는 따로 안함
        imageFile = new File(storageDir, imageFileName);
        mCurrentPath = imageFile.getAbsolutePath();

        return imageFile;
    }

    //각 경우별로 함수 어떻게 돌릴지
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {
            if (data == null) {
                return;
            }
            try {
                File albumFile = null;
                albumFile = createImageFile();
                photoURI = data.getData();
                albumURI = Uri.fromFile(albumFile);
                cropImage();
            } catch (IOException e) {
                Toast.makeText(this, "앨범에서 부르기가 실패되었습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (requestCode == PICK_FROM_CAMERA) {
            gallery_refresh();
            //cropImage();
            imgMain.setImageURI(imageURI);

        } else if (requestCode == CROP_FROM_CAMERA) {
            gallery_refresh();
            imgMain.setImageURI(albumURI);
        }

    }

    //갤러리 리프레시 해서 목록을 띄워줌
    private void gallery_refresh() {
        Intent scan_intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPath);
        Uri conURI = Uri.fromFile(f);
        scan_intent.setData(conURI);
        sendBroadcast(scan_intent);
        Toast.makeText(this, "저장에 성공하였습니다다.", Toast.LENGTH_SHORT).show();
    }

    //앨범에서 사진을 불러와 crop할때 우선 자르기만 적용.
    public void cropImage() {
        Intent crop_intent = new Intent("com.android.camera.action.CROP");
        crop_intent.setDataAndType(photoURI, "image/*");
        crop_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        crop_intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        Toast.makeText(this, "용량이 큰 사진의 경우 시간이 오래 걸릴 수 있습니다.", Toast.LENGTH_SHORT).show();

        crop_intent.putExtra("crop", "true");
        crop_intent.putExtra("outputX", 90);
        crop_intent.putExtra("outputY", 90);
        crop_intent.putExtra("aspectX", 1);
        crop_intent.putExtra("aspectY", 1);
        crop_intent.putExtra("scale", true);
        crop_intent.putExtra("return-data", true);
        crop_intent.putExtra("output", albumURI);

        startActivityForResult(crop_intent, CROP_FROM_CAMERA);
    }
}