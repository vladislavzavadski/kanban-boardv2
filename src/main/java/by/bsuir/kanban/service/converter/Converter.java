package by.bsuir.kanban.service.converter;

public interface Converter <R, T> {
    R convert(T t);
}
