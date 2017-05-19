package iut.fr.monappeloffre.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(iut.fr.monappeloffre.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Provider.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Provider.class.getName() + ".quotePS", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Provider.class.getName() + ".providerEligibilityPROS", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Provider.class.getName() + ".providerativityPROVIDERS", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Provider.class.getName() + ".registrationPROVIDERS", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Customer.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Customer.class.getName() + ".projectCS", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Project.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Project.class.getName() + ".quotePRS", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Project.class.getName() + ".providerEligibilityPS", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Project.class.getName() + ".projectactivityPROJECTS", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Project.class.getName() + ".projectpicPROJETS", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.ProjectPic.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Activity.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Activity.class.getName() + ".providerActivityACTIVITIES", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Activity.class.getName() + ".projectActivityACTIVITIES", jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Registration.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.ProviderEligibility.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Quote.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.Review.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.ProjectActivity.class.getName(), jcacheConfiguration);
            cm.createCache(iut.fr.monappeloffre.domain.ProviderActivity.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
