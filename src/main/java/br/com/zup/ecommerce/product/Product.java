package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.category.Category;
import br.com.zup.ecommerce.product.feature.Feature;
import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import br.com.zup.ecommerce.product.image.Image;
import br.com.zup.ecommerce.product.opinion.Opinion;
import br.com.zup.ecommerce.product.question.Question;
import br.com.zup.ecommerce.user.User;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal value;

    @NotNull
    @Positive
    @Column(nullable = false)
    private int availableAmount;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    @Size(min = 3)
    private Set<Feature> features = new HashSet<>();

    @NotBlank
    @Size(max = 1000)
    @Column(nullable = false)
    private String description;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @NotNull
    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private User owner;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<Image> images = new HashSet<>();

    @OrderBy("title asc")
    @OneToMany(mappedBy = "product")
    private SortedSet<Question> questions = new TreeSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Opinion> opinions = new HashSet<>();

    @Deprecated
    public Product() {
    }

    public Product(@NotBlank String name,
                   @NotNull @Positive BigDecimal value,
                   @NotNull @Positive int availableAmount,
                   @Valid @NotNull @Size(min = 3) Set<NewFeatureRequest> features,
                   @NotBlank @Size(max = 1000) String description,
                   @Valid @NotNull Category category,
                   @Valid @NotNull User owner) {
        this.name = name;
        this.value = value;
        this.availableAmount = availableAmount;
        this.features.addAll(features.stream()
                                     .map(f -> f.toModel(this))
                                     .collect(Collectors.toSet()));
        this.description = description;
        this.category = category;
        this.owner = owner;
        Assert.isTrue(this.features.size() >= 3, "The products must have three or more features.");
    }

    public Long getId() {
        return id;
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

    public Set<Feature> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public User getOwner() {
        return owner;
    }

    public Set<Image> getImages() {
        return images;
    }

    public SortedSet<Question> getQuestions() {
        return questions;
    }

    public Opinions getOpinions() {
        return new Opinions(this.opinions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean belongsTo(User user) {
        return this.owner.equals(user);
    }

    public void addImages(Set<String> uploadList) {
        Set<Image> images = uploadList.stream()
                .map(image -> new Image(this, image))
                .collect(Collectors.toSet());

        Assert.isTrue(uploadList.size() > 0, "Upload list must have at least 1 string.");

        this.images.addAll(images);
    }

    public <T> Set<T> mapImages(Function<Image, T> mappingFunction) {
        return this.images.stream()
                .map(mappingFunction)
                .collect(Collectors.toSet());
    }

    public <T> Set<T> mapFeatures(Function<Feature, T> mappingFunction){
        return this.features.stream()
                .map(mappingFunction)
                .collect(Collectors.toSet());
    }

    public <T extends Comparable<T>> SortedSet<T> mapQuestions(Function<Question, T> mappingFunction) {
        return this.questions.stream()
                .map(mappingFunction)
                .collect(Collectors.toCollection(TreeSet :: new));
    }
}
