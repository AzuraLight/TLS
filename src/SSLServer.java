import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import util.ConfigUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * SSLServer 클래스는 SSL/TLS 프로토콜을 사용하여 클라이언트의 연결을 수락하고
 * 데이터를 송수신하는 간단한 SSL 서버를 구현합니다.
 */
public class SSLServer {

    private static ConfigUtil config;

    public static void main(String[] args) throws Exception {

        // config 정보 로드
        config = ConfigUtil.getInstance();
        String portStrt = config.getProperty("port");
        int port = Integer.parseInt(portStrt);
        String keyStore = config.getProperty("keyStore");
        String keyStorePassword = config.getProperty("keyStorePassword");
        String trustStore = config.getProperty("trustStore");
        String trustStorePassword = config.getProperty("trustStorePassword");

        // 시스템 속성 설정을 통한 SSL/TLS 구성
        System.setProperty("javax.net.ssl.keyStore", keyStore);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
        System.setProperty("javax.net.ssl.trustStore", trustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

        // SSLServerSocketFactory를 사용하여 SSL 서버 소켓을 생성
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try (SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(port)) {
            System.out.println("SSL Server Started");

            // 클라이언트 연결을 수락
            while (true) {
                try (SSLSocket socket = (SSLSocket) serverSocket.accept();
                        // 소켓의 출력 스트림을 얻어 클라이언트에게 데이터를 보낼 PrintWriter 생성
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        // 소켓의 입력 스트림을 얻어 클라이언트로부터 데이터를 받을 BufferedReader 생성
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    // 클라이언트로부터 수신한 메시지를 읽고 에코 전송
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("Received from client: " + line);
                        out.println("Echo: " + line);
                    }
                } catch (Exception e) {
                    System.out.println("Server exception: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
