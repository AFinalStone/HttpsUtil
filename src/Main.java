public class Main {

    static String url_Login = "http://192.168.1.200:9997";
//    static String url_Login = "http://www.pifaxt.com";
    public static void main(String[] args) {
        System.out.println("Hello World!");
       String result =  HttpUtil.httpGet(url_Login);
        System.out.println("请求结果"+result);
    }
}
