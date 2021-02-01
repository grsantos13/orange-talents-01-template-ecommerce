package br.com.zup.ecommerce.product.question;

public class QuestionResponse implements Comparable<QuestionResponse> {

    private String title;
    private String interestedPerson;

    public QuestionResponse(Question question) {
        this.title = question.getTitle();
        this.interestedPerson = question.getInterestedPerson().getLogin();
    }

    public String getTitle() {
        return title;
    }

    public String getInterestedPerson() {
        return interestedPerson;
    }

    @Override
    public int compareTo(QuestionResponse o) {
        return this.title.compareTo(o.title);
    }
}
