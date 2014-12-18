package com.crowdhealth.forms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 10/12/14
 * Time: 3:10 AM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistogramForm {

    @JsonProperty
    List<CategoryCountForm> user_health_chart;

    @JsonProperty
    List<CategoryCountForm> user_health_pie;

    @JsonProperty
    List<CategoryCountForm> tweet_health_chart;

    @JsonProperty
    List<CategoryCountForm> tweet_health_pie;

    @JsonProperty
    List<CategoryCountForm> user_nutrition_chart;

    @JsonProperty
    List<CategoryCountForm> user_nutrition_pie;

    @JsonProperty
    List<CategoryCountForm> tweet_nutrition_chart;

    @JsonProperty
    List<CategoryCountForm> tweet_nutrition_pie;

    @JsonProperty
    List<CategoryCountForm> user_count;

    @JsonProperty
    List<CategoryCountForm> tweet_count;

//    public HistogramForm(List<CategoryCountForm> tweet_health_chart) {
//        this.user_health_chart = new ArrayList<CategoryCountForm>();
//        this.tweet_health_chart = tweet_health_chart;
//        this.user_nutrition_chart = new ArrayList<CategoryCountForm>();
//    }

    public HistogramForm(List<CategoryCountForm> user_health_chart, List<CategoryCountForm> tweet_health_chart, List<CategoryCountForm> user_nutrition_chart, List<CategoryCountForm> tweet_nutrition_chart) {
        this.user_health_chart = user_health_chart;
        this.tweet_health_chart = tweet_health_chart;
        this.user_nutrition_chart = user_nutrition_chart;
        this.tweet_nutrition_chart = tweet_nutrition_chart;
    }

    public HistogramForm(List<CategoryCountForm> user_health_chart, List<CategoryCountForm> user_health_pie, List<CategoryCountForm> tweet_health_chart, List<CategoryCountForm> tweet_health_pie, List<CategoryCountForm> user_nutrition_chart, List<CategoryCountForm> user_nutrition_pie, List<CategoryCountForm> tweet_nutrition_chart, List<CategoryCountForm> tweet_nutrition_pie, List<CategoryCountForm> user_count, List<CategoryCountForm> tweet_count) {
        this.user_health_chart = user_health_chart;
        this.user_health_pie = user_health_pie;
        this.tweet_health_chart = tweet_health_chart;
        this.tweet_health_pie = tweet_health_pie;
        this.user_nutrition_chart = user_nutrition_chart;
        this.user_nutrition_pie = user_nutrition_pie;
        this.tweet_nutrition_chart = tweet_nutrition_chart;
        this.tweet_nutrition_pie = tweet_nutrition_pie;
        this.user_count = user_count;
        this.tweet_count = tweet_count;
    }

    public List<CategoryCountForm> getUser_health_chart() {
        return user_health_chart;
    }

    public void setUser_health_chart(List<CategoryCountForm> user_health_chart) {
        this.user_health_chart = user_health_chart;
    }

    public List<CategoryCountForm> getTweet_health_chart() {
        return tweet_health_chart;
    }

    public void setTweet_health_chart(List<CategoryCountForm> tweet_health_chart) {
        this.tweet_health_chart = tweet_health_chart;
    }

    public List<CategoryCountForm> getUser_nutrition_chart() {
        return user_nutrition_chart;
    }

    public void setUser_nutrition_chart(List<CategoryCountForm> user_nutrition_chart) {
        this.user_nutrition_chart = user_nutrition_chart;
    }


    public List<CategoryCountForm> getUser_health_pie() {
        return user_health_pie;
    }

    public void setUser_health_pie(List<CategoryCountForm> user_health_pie) {
        this.user_health_pie = user_health_pie;
    }

    public List<CategoryCountForm> getTweet_health_pie() {
        return tweet_health_pie;
    }

    public void setTweet_health_pie(List<CategoryCountForm> tweet_health_pie) {
        this.tweet_health_pie = tweet_health_pie;
    }

    public List<CategoryCountForm> getUser_nutrition_pie() {
        return user_nutrition_pie;
    }

    public void setUser_nutrition_pie(List<CategoryCountForm> user_nutrition_pie) {
        this.user_nutrition_pie = user_nutrition_pie;
    }

    public List<CategoryCountForm> getTweet_nutrition_chart() {
        return tweet_nutrition_chart;
    }

    public void setTweet_nutrition_chart(List<CategoryCountForm> tweet_nutrition_chart) {
        this.tweet_nutrition_chart = tweet_nutrition_chart;
    }

    public List<CategoryCountForm> getTweet_nutrition_pie() {
        return tweet_nutrition_pie;
    }

    public void setTweet_nutrition_pie(List<CategoryCountForm> tweet_nutrition_pie) {
        this.tweet_nutrition_pie = tweet_nutrition_pie;
    }
}
