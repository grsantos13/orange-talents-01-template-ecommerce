package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.product.opinion.Opinion;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Opinions {
    private Set<Opinion> opinions;

    public Opinions(Set<Opinion> opinions) {
        this.opinions = opinions;
    }

    public <T> Set<T> mapOpinions(Function<Opinion, T> mappingFunction) {
        return this.opinions.stream()
                .map(mappingFunction)
                .collect(Collectors.toSet());
    }


    public double opinionsAverage() {
        return this.opinions.stream()
                .mapToInt(o -> o.getScore())
                .average()
                .orElse(0);
    }


    public int totalOpinions() {
        return this.opinions.size();
    }

}
