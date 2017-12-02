package cn.com.codeteenager.annotationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import cn.com.codeteenager.annotationlibrary.BindView;
import cn.com.codeteenager.annotationlibrary.CheckNet;
import cn.com.codeteenager.annotationlibrary.OnClick;
import cn.com.codeteenager.annotationlibrary.ViewUtils;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_click)
    private Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
    }

    @OnClick(R.id.btn_click)
    @CheckNet
    private void onClick() {
        int a = 2 / 0;
        Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
    }

}
