package com.ashwin.android.rxandroidbuttondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {
    private int count = 0;

    private PublishSubject<Integer> observable;
    private DisposableObserver<Integer> observer;

    private TextView counterTextView;
    private Button incrementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterTextView = (TextView) findViewById(R.id.counter_textview);

        observable = PublishSubject.create();

        observer = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                counterTextView.setText("Count: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("rxandroid-button-click", "Exception in observer", e);
            }

            @Override
            public void onComplete() {
                Log.w("rxandroid-button-click", "On-complete observer");
            }
        };

        incrementButton = (Button) findViewById(R.id.increment_button);
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                observable.onNext(count);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        observable.subscribe(observer);
    }

    @Override
    protected void onStop() {
        super.onStop();
        observer.dispose();
    }
}
