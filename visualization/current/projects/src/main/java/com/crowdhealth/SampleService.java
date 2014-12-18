package com.crowdhealth;

import com.crowdhealth.health.SampleHealthCheck;
import com.crowdhealth.resources.SampleResource;

import com.crowdhealth.util.DataLoader;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SampleService extends Application<SampleServiceConfiguration> {

    public static void main(String... args) throws Exception {
        new SampleService().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void run(SampleServiceConfiguration configuration, Environment environment) throws Exception {
        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();
        environment.jersey().register(new SampleResource(template, defaultName));
        environment.healthChecks().register("template", new SampleHealthCheck(template));
    }

    /**
     * Configure aspects of the service required before the service is run,
     * like bundles, configuration source providers, etc.
     *
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<SampleServiceConfiguration> bootstrap) {

        DataLoader.loadAllNutritionTweets("../fullnutritiondata.csv");
        DataLoader.loadAllHealthTweets("../fullhealthdata.csv");
//        DataLoader.loadAllNutritionTweets("/var/www/intelliad/tweetLabels.csv", true);
//        DataLoader.loadAllNutritionTweets("/var/www/intelliad/userLabels.csv", false);
//        // loadAllAds("/home/mangat/userLabels.csv");
//        DataLoader.makeMapping();
    }
}
