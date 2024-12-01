package io.artur.java.graphql.dgsintro.book;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class BookDataFetcher {

    private final List<Book> books = new ArrayList<>(List.of(
            new Book("1984", "George Orwell", 2020),
            new Book("Crime and Punishment", "Fyodor Dostoevsky", 2018),
            new Book("Hamlet", "William Shakespeare", 2001),
            new Book("The Odyssey", "Homer", 1995),
            new Book("The Stranger", "Albert Camus", 2011),
            new Book("The Trial", "Franz Kafka", 2017)
    ));

    @DgsQuery
    public List<Book> booksByTitle(@InputArgument String titleFilter) {
        if (titleFilter == null) {
            return books;
        }
        return books.stream()
                .filter(book -> book.title().contains(titleFilter))
                .toList();
    }

    @DgsQuery
    public List<Book> booksByAuthor(@InputArgument String authorFilter) {
        if (authorFilter == null) {
            return books;
        }
        return books.stream()
                .filter(book -> book.author().contains(authorFilter))
                .toList();
    }

    @DgsMutation
    public List<Book> addBook(@InputArgument String title, @InputArgument String author, @InputArgument Integer publishYear) {
        books.add(new Book(title, author, publishYear));

        List<Book> result = books.stream()
                .filter(book -> book.title().contains(title))
                .toList();

        return Optional.of(result).orElse(Collections.emptyList());
    }
}
