package ua.goit.goitnotes.service.validators;

public interface Validator<T> {

    boolean validate(T entity);

}
