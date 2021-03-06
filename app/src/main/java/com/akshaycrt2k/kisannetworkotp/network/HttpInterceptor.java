package com.akshaycrt2k.kisannetworkotp.network;

import com.akshaycrt2k.kisannetworkotp.utility.Utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class HttpInterceptor implements Interceptor
{
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String TAG = HttpInterceptor.class.getSimpleName();
    private final Logger logger;
    private final int RETRY_COUNT;
    private volatile Level level = Level.NONE;

    public HttpInterceptor() {
        this(0);
    }

    public HttpInterceptor(int retryCount) {
        this(Logger.DEFAULT,retryCount);
    }

    public HttpInterceptor(Logger logger,int retryCount) {
        this.logger = logger;
        this.RETRY_COUNT = retryCount;
    }

    private static String protocol(Protocol protocol)
    {
        return protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
    }

    private static String requestPath(HttpUrl url)
    {
        String path = url.encodedPath();
        String query = url.encodedQuery();
        return query != null ? (path + '?' + query) : path;
    }

    /** Change the level at which this interceptor logs. */
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        long startNsRequest = System.nanoTime();
        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage =
                "--> REQUEST START " + String.valueOf(request.url())+ ' ' + request.method() + ' ' /*+ requestPath(request.httpUrl()) + ' '*/ + protocol(protocol);
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        logger.log(requestStartMessage);

        if (logHeaders) {
            Headers headers = request.headers();
            if(null!=headers)
            {
                logger.log("Headers START -->");
                for(int i = 0, count = headers.size(); i < count; i++)
                {
                    logger.log(headers.name(i) + ": " + headers.value(i));
                }
                logger.log("<-- Headers END");
            }

            String endMessage = "--> REQUEST END " + request.method();
            if (logBody && hasRequestBody) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    contentType.charset(UTF8);
                }

                logger.log("");
                logger.log(buffer.readString(charset));

                long tookMsRequest = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNsRequest);
                endMessage += " (" + requestBody.contentLength() + "-byte body) (" + tookMsRequest + "ms)";
            }
            logger.log(endMessage);
        }

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        int retryCount = 0;
        while(!response.isSuccessful()){
            if(RETRY_COUNT <= retryCount)
                break;
            retryCount++;
            logger.log("Retry "+retryCount);
            response = chain.proceed(request);
        }

        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        logger.log("<-- RESPONSE START " + protocol(response.protocol()) + ' ' + response.code() + ' '
                + response.message() + " (" + tookMs + "ms"
                + (!logHeaders ? ", " + responseBody.contentLength() + "-byte body" : "") + ')');

        if (logHeaders) {
            Headers headers = response.headers();
            if(null!=headers)
            {
                logger.log("Headers START -->");
                for(int i = 0, count = headers.size(); i < count; i++)
                {
                    logger.log(headers.name(i) + ": " + headers.value(i));
                }
                logger.log("<-- Headers START");
            }

            String endMessage = "<-- RESPONSE END HTTP";
            if (logBody) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (responseBody.contentLength() != 0) {
                    logger.log("");
                    logger.log(buffer.clone().readString(charset));
                }

                logger.log("response Code >> "+response.code());
                logger.log("response SuccessFul >> "+response.isSuccessful());

                endMessage += " (" + buffer.size() + "-byte body)";
            }
            logger.log(endMessage);
        }

        return response;
    }

    public enum Level
    {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1 (3-byte body)
         *
         * <-- HTTP/1.1 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END GET
         *
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger
    {
        /**
         * A {@link Logger} defaults output appropriate for the current platform.
         */
        Logger DEFAULT = new Logger()
        {
            @Override
            public void log(String message)
            {
                Utils.showLog(TAG, message);
//                Utils.writeAppendExternalFile(NrfsiApp.getInstance().getApplicationContext(), null, "Response.txt", message);

            }
        };

        void log(String message);
    }
}
