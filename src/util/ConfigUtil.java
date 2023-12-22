package util;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * ConfigUtil 클래스는 애플리케이션의 구성 정보를 로드하는 유틸리티 클래스입니다.
 * 이 클래스는 'config/config.properties' 파일에서 구성 정보를 읽어오며,
 * 해당 정보는 SSL 연결 설정 및 기타 필요한 설정 정보에 사용됩니다.
 * Properties 클래스를 사용하여 파일 내용을 읽어오고, 필요한 구성 정보를
 * 쉽게 검색할 수 있도록 메소드를 제공합니다. ConfigUtil의 목적은
 * 애플리케이션의 설정을 중앙화하고, 코드 내에서 설정값을 쉽게 접근할 수 있도록
 * 하는 데 있습니다.
 * 또한 싱글톤 패턴을 활용하여 자원의 사용을 최적화 하려고 하였습니다.
 */
public class ConfigUtil {

    private static final ConfigUtil instance = new ConfigUtil();
    private Properties properties;

    private ConfigUtil() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("config/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigUtil getInstance() {
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}