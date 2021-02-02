package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.product.feature.FeatureResponse;
import br.com.zup.ecommerce.product.question.QuestionResponse;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class ProductDetailsResponse {

    private Set<String> imageLinks;
    private String name;
    private BigDecimal value;
    private int availableAmount;
    private Set<FeatureResponse> features;
    private String description;
    private double averageScore;
    private int totalOpinion;
    private Set<Map<String, String>> opinions;
    private SortedSet<QuestionResponse> questions;

    public ProductDetailsResponse(Product product) {
        this.imageLinks = product.mapImages(image -> image.getLink());
        this.name = product.getName();
        this.value = product.getValue();
        this.availableAmount = product.getAvailableAmount();
        this.features = product.mapFeatures(FeatureResponse::new);
        this.description = product.getDescription();

        Opinions opinions = product.getOpinions();
        this.opinions = opinions.mapOpinions(opinion -> {
            return Map.of("title", opinion.getTitle(), "description", opinion.getDescription());
        });

        this.averageScore = opinions.opinionsAverage();
        this.totalOpinion = opinions.totalOpinions();
        this.questions = product.mapQuestions(QuestionResponse::new);
    }

    public Set<String> getImageLinks() {
        return imageLinks;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public Set<FeatureResponse> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public int getTotalOpinion() {
        return totalOpinion;
    }

    public Set<Map<String,String>> getOpinions() {
        return opinions;
    }

    public SortedSet<QuestionResponse> getQuestions() {
        return questions;
    }
}
