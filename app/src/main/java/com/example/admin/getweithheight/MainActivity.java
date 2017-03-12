package com.example.admin.getweithheight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    //获取控件的宽高的demo
    //http://blog.csdn.net/nailsoul/article/details/25909313



    ImageView im;
    LinearLayout ll;
    ViewSize vs = new ViewSize();

    boolean measure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = (LinearLayout) findViewById(R.id.ll);
        getSize1();

        getSize4();
    }

    //第二种获取控件的宽高的方法,在这里调用
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        getSize2();

        if(hasFocus && !measure){
            measure=true;
            getSize5();
        }
        super.onWindowFocusChanged(hasFocus);
    }

    //方法3
    @Override
    protected void onResume() {

        getSize3();
        getSize4();

        super.onResume();
    }

    //1.在该View的事件回调里使用  这时候该view已经被显示即被添加到DecorView上  如点击事件  触摸事件   焦点事件等
    ViewSize getSize1() {
        im = (ImageView) findViewById(R.id.iv);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vs.height = v.getHeight();
                vs.width = v.getWidth();
                Log.d("view de height,width", "方法1" + v.getHeight() + "" + v.getWidth());
            }
        });
        return vs;
    }


    //2.在activity被显示出来时即添加到了DecorView上时获取宽和高如onWindowFocusChanged() 回调方法
    ViewSize getSize2() {
        im = (ImageView) findViewById(R.id.iv);
        vs.height = im.getHeight();
        vs.width = im.getWidth();
        Log.d("view 的 height,width", "方法2:控件高度:" + im.getHeight() + ".控件宽度:" + im.getWidth());
        return vs;
    }

    //3.或在onResume方法最后开线程300毫秒左右后获取宽和高   因为onResume执行完后300毫秒后 界面就显示出来了
    //当然地2种和地3种方法要保证获取宽高的view是在setContentView时设进去的View或它的子View
    ViewSize getSize3() {
        ll.postDelayed(new Runnable() {
            @Override
            public void run() {
                im = (ImageView) findViewById(R.id.iv);
                vs.height = im.getHeight();
                vs.width = im.getWidth();
                Log.d("view 的 height,width", "方法3:控件高度:" + im.getHeight() + ".控件宽度:" + im.getWidth());
            }
        }, 300);
        return vs;
    }

    //4.在onCreate()或onResume()等方法中需要获取宽高时使用getViewTreeObserver().addOnGlobalLayoutListener()
    // 来添为view加回调在回调里获得宽度或者高度获取完后让view删除该回调
    ViewSize getSize4() {
        ll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                im = (ImageView) findViewById(R.id.iv);
                vs.height = im.getHeight();
                vs.width = im.getWidth();
                Log.d("view 的 height,width", "方法4:控件高度:" + im.getHeight() + ".控件宽度:" + im.getWidth());
            }
        });
        return vs;
    }

    //5.在窗口焦点发生变化时获取宽高 onResume完后就会把界面渲染到窗口上 渲染完后将执行窗口焦点花生变化回调
    // 所以onResume到 onWindowFocusChanged为把界面渲染到窗口的时间
    ViewSize getSize5() {
        im = (ImageView) findViewById(R.id.iv);
        vs.height = im.getHeight();
        vs.width = im.getWidth();
        Log.d("view 的 height,width", "方法5:控件高度:" + im.getHeight() + ".控件宽度:" + im.getWidth());
        return vs;
    }
}
