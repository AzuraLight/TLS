# SSL Server / Client Examples

이 프로젝트는 SSL/TLS 프로토콜을 사용하여 안전한 데이터 통신을 구현하는 Java 예제를 포함합니다.

## 기능

- SSL 서버  
- SSL 클라이언트

## 사용 방법

### SSL 서버

`SSLServer` 클래스는 SSL 통신을 위한 서버를 실행하고, 클라이언트로부터 수신한 메세지를 읽고 에코를 전송합니다.

```java
    try (SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(port)) {
        // ...
    }
```

### SSL 클라이언트

`SSLClient` 클래스는 SSL 통신을 위해 SSL/TLS 버전과 암호화 스위트 설정하고, 사용자로부터 메세지를 입력 받고 이를 전송하고
서버로 부터 응답을 받습니다.

```java
    try (SSLSocket socket = (SSLSocket) ssf.createSocket(host, port)) {
        socket.setEnabledProtocols(new String[] {"TLSv1.2"});
        socket.setEnabledCipherSuites(new String[] {"TLS_RSA_WITH_AES_128_GCM_SHA256"});
        // ...
    }
```

### 설정 관리

`ConfigUtil` 클래스는 프로젝트 설정을 관리합니다. config.properties 파일로부터 필요한 설정을 로드합니다.

```java
ConfigUtil configUtil = ConfigUtil.getInstance();
```

## 설치

이 프로젝트를 사용하기 위해서는 Java가 필요합니다.

keytool을 사용하여 키와 인증서를 발급합니다.

config.properties 파일에 필요한 설정과 생성한 키에 대한 경로 정보를 추가합니다.

## 라이센스

이 프로젝트는 MIT 라이센스 하에 배포됩니다.
