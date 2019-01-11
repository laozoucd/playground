package retrofit2.adapter.rxjava2;

import android.util.Log;

import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;

final class RxJava2CallAdapter2<R> implements CallAdapter<R, Object> {
    private final Type responseType;
    private final @Nullable
    Scheduler subscribeScheduler;
    Scheduler observerScheduler;
    private final boolean isAsync;
    private final boolean isResult;
    private final boolean isBody;
    private final boolean isFlowable;
    private final boolean isSingle;
    private final boolean isMaybe;
    private final boolean isCompletable;

    RxJava2CallAdapter2(Type responseType, @Nullable Scheduler scheduler, boolean isAsync,
                       boolean isResult, boolean isBody, boolean isFlowable, boolean isSingle, boolean isMaybe,
                       boolean isCompletable) {
        this.responseType = responseType;
        this.subscribeScheduler = scheduler;
        this.isAsync = isAsync;
        this.isResult = isResult;
        this.isBody = isBody;
        this.isFlowable = isFlowable;
        this.isSingle = isSingle;
        this.isMaybe = isMaybe;
        this.isCompletable = isCompletable;
    }

    RxJava2CallAdapter2(Type responseType, @Nullable Scheduler subscribeScheduler,@Nullable Scheduler observerScheduler, boolean isAsync,
                        boolean isResult, boolean isBody, boolean isFlowable, boolean isSingle, boolean isMaybe,
                        boolean isCompletable) {
        this.responseType = responseType;
        this.subscribeScheduler = subscribeScheduler;
        this.observerScheduler = observerScheduler;
        this.isAsync = isAsync;
        this.isResult = isResult;
        this.isBody = isBody;
        this.isFlowable = isFlowable;
        this.isSingle = isSingle;
        this.isMaybe = isMaybe;
        this.isCompletable = isCompletable;
    }

    @Override public Type responseType() {
        return responseType;
    }

    @Override public Object adapt(Call<R> call) {
        Observable<Response<R>> responseObservable = isAsync
                ? new CallEnqueueObservable<>(call)
                : new CallExecuteObservable<>(call);

        Observable<?> observable;
        if (isResult) {
            observable = new ResultObservable<>(responseObservable);
        } else if (isBody) {
            observable = new BodyObservable<>(responseObservable);
        } else {
            observable = responseObservable;
        }

        if (subscribeScheduler != null) {
            observable = observable.subscribeOn(subscribeScheduler);
        }

        if (observerScheduler != null) {
            observable = observable.observeOn(observerScheduler);
        }

        if (isFlowable) {
            return observable.toFlowable(BackpressureStrategy.LATEST);
        }
        if (isSingle) {
            return observable.singleOrError();
        }
        if (isMaybe) {
            return observable.singleElement();
        }
        if (isCompletable) {
            return observable.ignoreElements();
        }

        observable = observable.compose(new ObservableTransformer<Object, Object>() {
            @Override
            public ObservableSource<Object> apply(Observable<Object> upstream) {
                return upstream.doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("zzh", "hook error");
                    }
                });
            }
        });

        return RxJavaPlugins.onAssembly(observable);

    }
}

