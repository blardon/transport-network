package de.fhkiel.advancedjava;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableNeo4jRepositories
@EnableTransactionManagement
public class Neo4jConfiguration {

    @Bean
    @Profile("!test")
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(), "de.fhkiel.advancedjava.model");
    }

    @Bean
    @Profile("!test")
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .credentials("neo4j", "1234").uri("bolt://localhost:7687").build();
    }

    @Bean
    @Profile("!test")
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

    @Bean
    @Profile("test")
    public org.neo4j.ogm.config.Configuration configurationTest() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .credentials("neo4j", "4321").uri("bolt://localhost:7688").build();
    }

    @Bean("sessionFactory")
    @Profile("test")
    public SessionFactory sessionFactoryTest() {
        return new SessionFactory(configurationTest(), "de.fhkiel.advancedjava.model");
    }

    @Bean("transactionManager")
    @Profile("test")
    public Neo4jTransactionManager transactionManagerTest() {
        return new Neo4jTransactionManager(sessionFactoryTest());
    }


}
