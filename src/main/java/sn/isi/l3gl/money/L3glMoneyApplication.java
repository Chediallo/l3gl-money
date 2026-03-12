package sn.isi.l3gl.money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class L3glMoneyApplication {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(false);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeClientInfo(true);
        return filter;
    }

	public static void main(String[] args) {
		SpringApplication.run(L3glMoneyApplication.class, args);
	}

}
