import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpUtil {

//    public static String httpGet(String httpUrl) {
//
//        String result = "";
//        DefaultHttpClient httpclient = new DefaultHttpClient();// 创建http客户端
//        HttpGet httpget = new HttpGet(httpUrl);
//        HttpResponse response = null;
//        HttpParams params = httpclient.getParams(); // 计算网络超时用
//        HttpConnectionParams.setConnectionTimeout(params, 15 * 1000);
//        HttpConnectionParams.setSoTimeout(params, 20 * 1000);
//
//        try {
//            response = httpclient.execute(httpget);
//            HttpEntity entity = response.getEntity();// 得到http的内容
//            response.getStatusLine().getStatusCode();// 得到http的状态返回值
//            result = EntityUtils.toString(response.getEntity());// 得到具体的返回值，一般是xml文件
//            entity.consumeContent();// 如果entity不为空，则释放内存空间
//            httpclient.getCookieStore();// 得到cookis
//            httpclient.getConnectionManager().shutdown();// 关闭http客户端
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    /**
     * 从网络获取json数据,(String byte[})
     * Get请求获取数据
     * @param httpUrl
     * @return
     */
    public static String httpGet(String httpUrl){
        try {
            URL url = new URL(httpUrl.trim());
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //设置请求头的一些参数内容
            urlConnection.setRequestProperty("Accept-Language","zh-CN");
            urlConnection.setRequestProperty("Accept-Encoding","gzip, deflate");
            urlConnection.setRequestProperty("Connection","keep-alive");
            urlConnection.setRequestProperty("Cookie","LOGINUSER_LASTLOGINIP=val=J9cpjv2h5UzqRxqSFhPxZ7G%2bE8aC0EHL4ZgDPKZdiIQ%3d; LOGINUSER_ENDTIME=val=e0%2fD%2bxbwkULTdUfiANArZA%3d%3d");

            //设置请求超时时间,毫秒级
            urlConnection.setConnectTimeout(10000);

            //如果连接中的 UseCaches 标志为 true，则允许连接使用任何可用的缓存。如果为 false，则忽略缓存。默认值来自 DefaultUseCaches，它默认为 true
            urlConnection.setUseCaches(true);

            //设置请求方式
            urlConnection.setRequestMethod("GET");

            //URL 连接可用于输入或输出。如果打算使用 URL 连接进行输出，则将 DoOutput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 false。
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            if(200 == urlConnection.getResponseCode()){
                //得到输入流
                InputStream is =urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while(-1 != (len = is.read(buffer))){
                    baos.write(buffer,0,len);
                    baos.flush();
                }
                return baos.toString("utf-8");
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String post(String requestUrl, String accessToken, String params) throws Exception {
        String generalUrl = requestUrl + "?access_token=" + accessToken;
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(params);
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.out.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        if (requestUrl.contains("nlp"))
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
        else
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        System.out.println("result:" + result);
        return result;
    }

        public static String httpPost(String httpUrl, String data) {


            return httpUrl;
        }


//    public static String httpPost(String httpUrl, String data) {
//        String result = "";
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(httpUrl);
//        // httpclient.setCookieStore(DataDefine.mCookieStore);
//
//        HttpParams params = httpclient.getParams(); // 计算网络超时用
//        HttpConnectionParams.setConnectionTimeout(params, 15 * 1000);
//        HttpConnectionParams.setSoTimeout(params, 20 * 1000);
//        httpPost.setHeader("Content-Type", "text/xml");
//        StringEntity httpPostEntity;
//
//        try {
//            httpPostEntity = new StringEntity(data, "UTF-8");
//            httpPost.setEntity(httpPostEntity);
//            HttpResponse response = httpclient.execute(httpPost);
//            HttpEntity entity = response.getEntity();// 得到http的内容
//            response.getStatusLine().getStatusCode();// 得到http的状态返回值
//            result = EntityUtils.toString(response.getEntity());// 得到具体的返回值，一般是xml文件
//            entity.consumeContent();// 如果entity不为空，则释放内存空间
//            httpclient.getCookieStore();// 得到cookis
//            httpclient.getConnectionManager().shutdown();// 关闭http客户端
//        } catch (Exception e) {
//            e.printStackTrace();
//        }// base64是经过编码的字符串，可以理解为字符串
//        // StringEntity httpPostEntity = new StringEntity("UTF-8");
//        return result;
//    }
}