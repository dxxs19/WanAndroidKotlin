package com.wei.wanandroidkotlin.rx;


import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class RxJavaOperators {
    private final static String TAG = "RxJavaOperators";
    private static Observer observer = new CusObserver<String>();

    static class CusObserver<T> implements Observer<T>, Disposable {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(T t) {
            Log.e(TAG, "结果：" + t);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }
    }
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

    public static void testFlowable() {
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        Subscriber<Integer> downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e(TAG, "onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: " + t);
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };

        upstream.doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Exception {
                Log.e(TAG, "ready to receive data");
            }
        }).subscribe(downstream);
    }

    private static Subscription subscription;

    public static void testInterval() {
        Flowable.interval(1, TimeUnit.MICROSECONDS)
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.e(TAG, "onSubscribe");
                        subscription = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "onNext: " + aLong);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: " + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    public static void testFilter() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(100);
                emitter.onNext(83);
                emitter.onNext(24);
                emitter.onNext(33);
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer > 30;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, integer + "");
            }
        });
    }

    public static void testFlatMap() {
        Observable.just(1)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return getObservable(integer);
                    }
                }).subscribe(observer);
    }

    private static ObservableSource<String> getObservable(Integer integer) {
        return Observable.just("I'm come from 'getObservable' method!");
    }

    public static void testMap() {
        Observable.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return null;
                    }
                }).subscribe(observer);

        List list = new ArrayList();
    }

    public static void testZip() {
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(observer);
    }

    static String[] strs = {"1bc", "lajdl", "0212"};
    public static void testCreate() {
        Observable<String> stringObservable = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just(strs[0]);
            }
        });

        strs[0] = "abcdefg";
        stringObservable.subscribe(new CusObserver<>());
    }

}
