import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import util.ConfigUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * SSLClient 클래스는 SSL/TLS 프로토콜을 사용하여 SSL 서버에 연결하고
 * 데이터를 송수신하는 간단한 SSL 클라이언트를 구현합니다.
 */
public class SSLClient {

    private static ConfigUtil config;

    public static void main(String[] args) throws Exception {

        // config 정보 로드
        config = ConfigUtil.getInstance();
        String portStrt = config.getProperty("port");
        int port = Integer.parseInt(portStrt);
        String host = config.getProperty("host");

        // 신뢰 저장소 설정 추가
        String trustStore = config.getProperty("trustStore");
        String trustStorePassword = config.getProperty("trustStorePassword");
        System.setProperty("javax.net.ssl.trustStore", trustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

        // SSLSocketFactory를 사용하여 SSL 소켓을 생성
        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try (SSLSocket socket = (SSLSocket) ssf.createSocket(host, port)) {
            // SSL/TLS 버전과 암호화 스위트 설정
            socket.setEnabledProtocols(new String[] { "TLSv1.2" });
            // TLS_<키교환알고리즘>_WITH_<암호화알고리즘>_<메시지인증코드알고리즘>
            socket.setEnabledCipherSuites(new String[] { "TLS_RSA_WITH_AES_128_GCM_SHA256" });

            // 소켓의 출력 스트림을 얻어 서버에게 데이터를 보낼 PrintWriter 생성
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    // 소켓의 입력 스트림을 얻어 서버로부터 데이터를 받을 BufferedReader 생성
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // 표준 입력(콘솔)을 통해 사용자 입력을 받을 BufferedReader 생성
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

                // 사용자 입력을 읽고 서버에 전송, 서버로부터의 응답을 받음
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    out.println(userInput);
                    System.out.println("Server response: " + in.readLine());
                }
            }
        }
    }
}
