package com.alan.framework.rx.schedule;

import com.alan.framework.rx.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Mouse on 2019/4/16.
 */
public class RxSchedule {

    public static <T> Disposable schedule(final RxScheduleRunnable<T> scheduleRunnable) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) {
                T t1 = scheduleRunnable.scheduleOnThread();
                e.onNext(t1);
                e.onComplete();
            }
        }).compose(RxSchedulers.<T>threadMain()).doOnNext(new Consumer<T>() {
            @Override
            public void accept(T t) {
                scheduleRunnable.scheduleOnMainThread(t);
            }
        }).onErrorReturn(new Function<Throwable, T>() {
            @Override
            public T apply(Throwable throwable) {
                return scheduleRunnable.onError(throwable);
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() {
                scheduleRunnable.onCompleteOnMainThread();
            }
        }).subscribe();
    }
}
