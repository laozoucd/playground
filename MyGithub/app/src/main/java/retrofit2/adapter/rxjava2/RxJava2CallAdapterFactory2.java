package retrofit2.adapter.rxjava2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.annotations.Nullable;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RxJava2CallAdapterFactory2 extends CallAdapter.Factory {
    /**
     * Returns an instance which creates synchronous observables that do not operate on any scheduler
     * by default.
     */
    public static RxJava2CallAdapterFactory2 create() {
        return new RxJava2CallAdapterFactory2(null,null, false);
    }

    /**
     * Returns an instance which creates asynchronous observables. Applying
     * {@link Observable#subscribeOn} has no effect on stream types created by this factory.
     */
    public static RxJava2CallAdapterFactory2 createAsync() {
        return new RxJava2CallAdapterFactory2(null,null, true);
    }

    /**
     * Returns an instance which creates synchronous observables that
     * {@linkplain Observable#subscribeOn(Scheduler) subscribe on} {@code scheduler} by default.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static RxJava2CallAdapterFactory2 createWithScheduler(Scheduler subscribeScheduler,Scheduler observerScheduler) {
        if (subscribeScheduler == null || observerScheduler == null) throw new NullPointerException("scheduler == null");
        return new RxJava2CallAdapterFactory2(subscribeScheduler, observerScheduler, false);
    }

    private final @Nullable
    Scheduler subscribeScheduler;
    Scheduler observerScheduler;
    private final boolean isAsync;

    private RxJava2CallAdapterFactory2(@Nullable Scheduler subscribeScheduler,@Nullable Scheduler observerScheduler, boolean isAsync) {
        this.subscribeScheduler = subscribeScheduler;
        this.observerScheduler = observerScheduler;
        this.isAsync = isAsync;
    }

    @Override
    public @Nullable
    CallAdapter<?, ?> get(
            Type returnType, Annotation[] annotations, Retrofit retrofit) {
        //获取最外面的类型   List<? extends Runnable> 返回 List.class
        Class<?> rawType = getRawType(returnType);

        if (rawType == Completable.class) {
            // Completable is not parameterized (which is what the rest of this method deals with) so it
            // can only be created with a single configuration.
            return new RxJava2CallAdapter2(Void.class, subscribeScheduler, isAsync, false, true, false, false,
                    false, true);
        }

        boolean isFlowable = rawType == Flowable.class;
        boolean isSingle = rawType == Single.class;
        boolean isMaybe = rawType == Maybe.class;
        if (rawType != Observable.class && !isFlowable && !isSingle && !isMaybe) {
            return null;
        }

        boolean isResult = false;
        boolean isBody = false;
        Type responseType;
        //判断是否是泛型
        if (!(returnType instanceof ParameterizedType)) {
            String name = isFlowable ? "Flowable"
                    : isSingle ? "Single"
                    : isMaybe ? "Maybe" : "Observable";
            throw new IllegalStateException(name + " return type must be parameterized"
                    + " as " + name + "<Foo> or " + name + "<? extends Foo>");
        }

        //获取到第一个泛型参数
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);

        //获取最外面的类型
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType == Response.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("Response must be parameterized"
                        + " as Response<Foo> or Response<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
        } else if (rawObservableType == Result.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("Result must be parameterized"
                        + " as Result<Foo> or Result<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
            isResult = true;
        } else {
            responseType = observableType;
            isBody = true;
        }

        return new RxJava2CallAdapter2(responseType, subscribeScheduler, observerScheduler, isAsync, isResult, isBody, isFlowable,
                isSingle, isMaybe, false);
    }
}
