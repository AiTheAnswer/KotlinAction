package com.allen.core_net

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import java.util.*

/**
 * Created by Allen on 2018/12/2
 *
 * Retrofit 提供了三类注解(请求方法类注解(8个)、标记类注解(3个)、参数类注解)
 * 请求方法类注解有：GET、POST、PUT、DELETE、HEAD、PATCH、OPTIONS、HTTP(全部作用于方法)
 *    GET:用于发送一个get请求
 *        GET注解一般必须添加相对路径或绝对路径或者全路径，如果不想在GET注解后添加请求路径，
 *        则可以在方法的第一个参数中使用@URL注解添加请求路径
 *
 *    POST:用于发送一个Post请求
 *         POST请求和GET请求一样，可以添加请求路径，也可以在方法的第一个参数用@URL添加请求路径
 *
 * 标记类注解有：FormUrlEncoded、Multipart、Streaming(全部作用于方法)
 *    FormUrlEncoded:表明是一个表单请求(请求将具"application / x-www-form-urlencoded"MIME类型)
 *                    用于修饰Field(单个参数)和FieldMap(多个参数)
 *
 *    Multipart: 表示请求体是多部分的。每一个部分作为一个参数，且用Part注解声明
 *
 *    Streaming: 处理Response方法的相应体，代表响应的数据以流的形式返回，如果不使用它，
 *               则默认把全部数据加载到内存，所以下载大文件时需要加上这个注解。
 * 参数类注解有：Header、Headers、Body、Path、Filed、FiledMap、Query、QueryMap
 *              、Part、Url         （有点作用于方法，有的作用于方法的参数）
 *     Header:作用于方法的参数，用于添加请求头
 *            使用该注解定义的请求头可以为空，当为空时，会自动忽略。
 *            当传入一个List或者Array时，会拼接每一个非空的item到请求头中
 *            具有相同名称的请求头不会相互覆盖，而是会照样添加到请求头中
 *       eg:  @GET("/")
 *            Call<ResponseBody> foo(@Header("Accept-Language") String lang)
 *
 *     Headers:作用于方法，用于添加一个或多个请求头
 *       eg:  //添加一个请求头
 *           @Headers("Cache-Control: max-age=64000")
 *           @GET("/")
 *           //添加多个请求头
 *           @Headers({ "X-Foo:Bar",
 *                      "X-Ping:Pong"
 *                    })
 *           @GET("/")
 *
 *      Body:作用于方法的参数，使用该注解定义的参数不能为null
 *           当你发送一个Post或者put请求，但又不想作为请求参数或者表单的方式发送请求时
 *           ，使用该参数可以直接传入一个实体类，retrofit会通过convert
 *           把实体序列化(Json、xml、jackson...)后的结果作为请求体发送出去
 *        eg: @POST
 *            Call<ResponseBody> foo(@Body Repo repo);
 *
 *      Path:作用于方法的参数
 *           在URL路径段中替换指定的参数值，参数值不能为null
 *        eg:@GET("/image/{id})
 *           Call<ResponseBody> foo(@Path("id") int id)
 *           @GET("/image/{name})
 *           Call<ResponseBody> foo(@Path("name) String name)
 *
 *      Field:作用于方法的参数，用于发送一个表单请求(使用这个注解，必须使用FormUrlEncoded注解来表明以表单的形式上传)
 *            可以传入一个值，或者list、Array    拼接方式 name=丽丽&name=哈哈
 *        eg: @FormUrlEncoded
 *            @POST("/")
 *            Call<ResponseBody> foo(@Field("name") String name,@Field("age") int age);
 *            @FormUrlEncoded
 *            @POST
 *            Call<ResponseBody> foo(@Field("name) String... name)
 *
 *       FieldMap:作用于方法的参数
 *                用于发送一个表单请求，map中每一项的键和值都不能为空，否则会抛出非法参数异常
 *         eg: @FormUrlEncoded
 *             @POST("/list")
 *             Call<ResponseBody> foo(@FieldMap Map<String,String> params);
 *
 *       Query: 作用与方法的参数
 *              用于添加查询参数，即请求出参数
 *              使用该注解定义的参数,参数值可以为空,为空时,忽略该值,当传入一个List或array时,
 *              为每个非空item拼接请求键值对,所有的键是统一的,如: name=张三&name=李四&name=王五.
 *          eg: @GET("/name")
 *              Call<ResponseBody> foo(@Query(page) int page);
 *              @GET("/name")
 *              Call<ResponseBody> foo(@Query(name) String... name);
 *
 *        QueryMap:作用于方法的参数
 *                 以map的形式添加查询参数,即请求参数
 *                 map中每一项的键和值都不能为空,否则抛出IllegalArgumentException异常
 *           eg: @GET("/search")
 *               Call<ResponseBody> foo(@QueryMap Map<String,String> params);
 *
 *         Part: 作用于方法的参数,用于定义Multipart请求的每个part
 *               使用该注解定义的参数,参数值可以为空,为空时,则忽略
 *               使用该注解定义的参数类型有以下3种方式可选:
 *                 1, 如果类型是okhttp3.MultipartBody.Part，内容将被直接使用。
 *                                       省略part中的名称,即 @Part MultipartBody.Part part
 *                 2, 如果类型是RequestBody，那么该值将直接与其内容类型一起使用。
 *                                在注释中提供part名称（例如，@Part（“foo”）RequestBody foo）。
 *                 3, 其他对象类型将通过使用转换器转换为适当的格式。 在注释中提供part名称
 *                                        （例如，@Part（“foo”）Image photo）。
 *            eg:@Multipart
 *                @POST("/")
 *                Call<ResponseBody> example(
 *                      @Part("description") String description,
 *                      @Part(value = "image", encoding = "8-bit") RequestBody image);
 *
 *         2.4 PartMap注解:
 *                   作用于方法的参数,以map的方式定义Multipart请求的每个part
 *                        map中每一项的键和值都不能为空,否则抛出IllegalArgumentException异常
 *                   使用该注解定义的参数类型有以下2种方式可选:
 *                   1, 如果类型是RequestBody，那么该值将直接与其内容类型一起使用。
 *                    2, 其他对象类型将通过使用转换器转换为适当的格式。
 *               示例:
 *                   @Multipart
 *                  @POST("/upload")
 *                  Call<ResponseBody> upload(@Part("file") RequestBody file,
 *                                   @PartMap Map<String, RequestBody> params);
 *
 */
interface RestService {


    @GET
    fun get(@Url url:String,@QueryMap params: HashMap<String, Any>): Observable<String>

    @POST
    fun post(@Url url:String,@FieldMap params:HashMap<String,Any>):Observable<String>

    @POST
    fun postArw(@Part photo:MultipartBody.Part,@Part("description") file:MultipartBody)
}