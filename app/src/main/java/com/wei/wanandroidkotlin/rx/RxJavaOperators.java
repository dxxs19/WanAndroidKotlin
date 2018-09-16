package com.wei.wanandroidkotlin.rx;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class RxJavaOperators {
    private final static String TAG = "RxJavaOperators";
    private static Observer<String> observer = new Observer<String>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            Log.e(TAG, "结果：" + s);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    private static Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            Log.d(TAG, "emit 1");
            emitter.onNext(1);
            Thread.sleep(1000);

            Log.d(TAG, "emit 2");
            emitter.onNext(2);
            Thread.sleep(1000);

            Log.d(TAG, "emit 3");
            emitter.onNext(3);
            Thread.sleep(1000);

            Log.d(TAG, "emit 4");
            emitter.onNext(4);
            Thread.sleep(1000);

            Log.d(TAG, "emit complete1");
            emitter.onComplete();
        }
    }).subscribeOn(Schedulers.io());

    private static Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            Log.d(TAG, "emit A");
            emitter.onNext("A");
            Thread.sleep(1000);

            Log.d(TAG, "emit B");
            emitter.onNext("B");
            Thread.sleep(1000);

            Log.d(TAG, "emit C");
            emitter.onNext("C");
            Thread.sleep(1000);

            Log.d(TAG, "emit complete2");
            emitter.onComplete();
        }
    }).subscribeOn(Schedulers.io());

    public static void testFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(100);
                emitter.onNext(83);
                emitter.onNext(24);
                emitter.onNext(33);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> strs = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    strs.add(integer + "");
                }
                return Observable.fromIterable(strs).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(observer);
    }

    public static void testZip() {
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(observer);
    }
}
