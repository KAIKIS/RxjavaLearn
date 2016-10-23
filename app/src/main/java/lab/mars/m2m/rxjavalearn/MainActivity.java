package lab.mars.m2m.rxjavalearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Observable<Long> observable = interval();
        final Subscriber<Long> subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.i("test","complete.");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("test",e.getMessage());
            }

            @Override
            public void onNext(Long integer) {
                Log.i("test", ""+integer);
            }
        };

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               observable.subscribe(subscriber);
            }
        });


    }

    private Observable<Long> interval(){
        return Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Integer> createObserver(){
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 0; i < 5; i++){
                        int temp = new Random().nextInt(10);
                        if (temp > 8){
                            subscriber.onError(new Throwable(">8"));
                            break;
                        }else {
                            subscriber.onNext(temp);
                        }
                        if (i == 4) subscriber.onCompleted();
                    }
                }
            }
        });
    }

}
