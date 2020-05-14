package me.ely

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.runApplication
// import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import java.net.InetAddress
import java.net.UnknownHostException

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2020/5/13
 */
@EnableScheduling
@EntityScan("me.ely.model")
// @EnableFeignClients(basePackages = ["me.ely.client"])
@EnableJpaRepositories("me.ely.repository")
@SpringBootApplication(
        scanBasePackages = ["me.ely"]
        // ,exclude = [ MongoAutoConfiguration::class, MongoDataAutoConfiguration::class ]
)
class AccountApplication(val env: Environment) : InitializingBean {

    override fun afterPropertiesSet() {

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val applicationContext = runApplication<AccountApplication>(*args) {
                this.addListeners(ApplicationPidFileWriter())
            }
            logApplicationStartup(applicationContext.environment)
        }

        @JvmStatic
        private fun logApplicationStartup(env: Environment) {
            val log = LoggerFactory.getLogger(AccountApplication::class.java)

            var protocol = "http"
            if (env.getProperty("server.ssl.key-store") != null) {
                protocol = "https"
            }
            val serverPort = env.getProperty("server.port") ?: 8080
            var contextPath = env.getProperty("server.servlet.context-path")
            if (contextPath.isNullOrBlank()) {
                contextPath = "/"
            }
            var hostAddress = "localhost"
            try {
                hostAddress = InetAddress.getLocalHost().hostAddress
            } catch (e: UnknownHostException) {
                log.warn("The host name could not be determined, using `localhost` as fallback")
            }
            log.info(
                    "\n----------------------------------------------------------\n\t" +
                            "Application '{}' is running! Access URLs:\n\t" +
                            "Local: \t\t{}://localhost:{}{}\n\t" +
                            "External: \t{}://{}:{}{}\n\t" +
                            "Profile(s): \t{}\n----------------------------------------------------------",
                    env.getProperty("spring.application.name"),
                    protocol,
                    serverPort,
                    contextPath,
                    protocol,
                    hostAddress,
                    serverPort,
                    contextPath,
                    env.activeProfiles
            )
        }
    }
}


