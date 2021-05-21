package com.siwoo.simpletalker;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class SimpleTalkerApplication {
	
	public static void main(String[] args) {
		System.out.println(new SystemEnvSnapshot());
		SpringApplication.run(SimpleTalkerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner talkToKube(@Autowired OkHttpClient httpClient) {
		return (args) -> {
			KubernetesClient client = new DefaultKubernetesClient();
			PodList pods = client.pods().inNamespace("default").list();
			pods.getItems().stream().forEach(System.out::println);
		};
	}

	@SneakyThrows
	private String getToken(String tokenPath) {
		return String.join("", Files.readAllLines(Paths.get(tokenPath)));
	}

	@Bean
	public OkHttpClient httpClient() {
		return new OkHttpClient.Builder()
				.readTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(10, TimeUnit.SECONDS)
				.connectTimeout(10, TimeUnit.SECONDS)
				.build();
	}

	@Data
	private static class SystemEnvSnapshot {
		public static final String KUBERNETES_SERVICE_HOSTNAME = "https://kubernetes";
		public static final String KUBERNETES_CA_CRT = "/var/run/secrets/kubernetes.io/serviceaccount/ca.crt";
		private static final String SECRET_PATH = "var/run/secrets/kubernetes.io/serviceaccount";
		private final String KUBERNETES_SERVICE_HOST;
		private final int KUBERNETES_SERVICE_PORT;
		private final int KUBERNETES_SERVICE_PORT_HTTPS;
		private final String NAMESPACE;
		private final String TOKEN;

		public SystemEnvSnapshot()  {
			try {
				KUBERNETES_SERVICE_HOST = getSystemEnv("KUBERNETES_SERVICE_HOST");
				KUBERNETES_SERVICE_PORT = Integer.parseInt(getSystemEnv("KUBERNETES_SERVICE_PORT"));
				KUBERNETES_SERVICE_PORT_HTTPS = Integer.parseInt(getSystemEnv("KUBERNETES_SERVICE_PORT_HTTPS"));
				NAMESPACE = String.join("", Files.readAllLines(Paths.get(SECRET_PATH + "/namespace")));
				TOKEN = String.join("", Files.readAllLines(Paths.get(SECRET_PATH + "/token")));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public String getSystemEnv(String name) {
			return System.getenv(name);
		}
		
	}
}
