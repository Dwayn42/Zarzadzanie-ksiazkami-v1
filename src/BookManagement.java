import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;

public class BookManagement extends JFrame {
    static class Book {
        String title, author, genre;
        int year;

        Book(String title, String author, int year, String genre) {
            this.title = title;
            this.author = author;
            this.year = year;
            this.genre = genre;
        }

        public String toString() {
            return title + " (" + author + ", " + year + ")";
        }
    }

    private ArrayList<Book> books;
    private DefaultListModel<String> listModel;
    private JList<String> bookList;
    private JTextField searchField, titleField, authorField, yearField, genreField;
    private JComboBox<String> sortComboBox;

    public BookManagement() {
        setTitle("Zarządzanie książkami");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        books = new ArrayList<>();
        loadBooks();

        listModel = new DefaultListModel<>();
        bookList = new JList<>(listModel);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        updateBookList();

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Szukaj");
        searchButton.addActionListener(e -> searchBooks());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Wyszukaj książkę:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel addPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        addPanel.setBorder(BorderFactory.createTitledBorder("Dodaj nową książkę"));
        titleField = new JTextField();
        authorField = new JTextField();
        yearField = new JTextField();
        genreField = new JTextField();
        addPanel.add(new JLabel("Tytuł:"));
        addPanel.add(titleField);
        addPanel.add(new JLabel("Autor:"));
        addPanel.add(authorField);
        addPanel.add(new JLabel("Rok:"));
        addPanel.add(yearField);
        addPanel.add(new JLabel("Gatunek:"));
        addPanel.add(genreField);
        JButton addButton = new JButton("Dodaj");
        addButton.addActionListener(e -> addBook());
        addPanel.add(addButton);

        String[] sortOptions = {"Tytuł", "Autor", "Rok", "Gatunek"};
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.addActionListener(e -> sortBooks((String) sortComboBox.getSelectedItem()));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JScrollPane(bookList), BorderLayout.CENTER);
        leftPanel.add(sortComboBox, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(searchPanel, BorderLayout.NORTH);
        rightPanel.add(addPanel, BorderLayout.CENTER);

        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    private void loadBooks() {
        books.add(new Book("Harry Potter i Kamień Filozoficzny", "J.K. Rowling", 1997, "Fantasy"));
        books.add(new Book("Władca Pierścieni: Drużyna Pierścienia", "J.R.R. Tolkien", 1954, "Fantasy"));
        books.add(new Book("Wielki Gatsby", "F. Scott Fitzgerald", 1925, "Fikcja"));
        books.add(new Book("Sto lat samotności", "Gabriel Garcia Marquez", 1967, "Magiczny Realizm"));
        books.add(new Book("Mistrz i Małgorzata", "Michaił Bułhakow", 1967, "Satira"));
        books.add(new Book("Zbrodnia i kara", "Fiodor Dostojewski", 1866, "Fikcja"));
        books.add(new Book("Anna Karenina", "Lew Tołstoj", 1877, "Fikcja"));
        books.add(new Book("Łaskawe", "Jonathan Littell", 2006, "Historyczna Fikcja"));
        books.add(new Book("Malowany ptak", "Jerzy Kosiński", 1965, "Fikcja"));
        books.add(new Book("Lolita", "Vladimir Nabokov", 1955, "Fikcja"));
        books.add(new Book("Mechaniczna pomarańcza", "Anthony Burgess", 1962, "Science Fiction"));
        books.add(new Book("Duma i uprzedzenie", "Jane Austen", 1813, "Romans"));
        books.add(new Book("Złodziejka książek", "Markus Zusak", 2005, "Historyczna Fikcja"));
        books.add(new Book("Opowieść podręcznej", "Margaret Atwood", 1985, "Dystopia"));
        books.add(new Book("1984", "George Orwell", 1949, "Dystopia"));
        books.add(new Book("Paragraf 22", "Joseph Heller", 1961, "Satira"));
        books.add(new Book("Lot nad kukułczym gniazdem", "Ken Kesey", 1962, "Fikcja"));
        books.add(new Book("Cień wiatru", "Carlos Ruiz Zafón", 2001, "Mystery"));
        books.add(new Book("Dziewczyna z tatuażem", "Stieg Larsson", 2005, "Thriller"));
        books.add(new Book("Wywiad z wampirem", "Anne Rice", 1976, "Horror"));
    }

    private void updateBookList() {
        listModel.clear();
        for (Book book : books) {
            listModel.addElement(book.toString());
        }
    }

    private void searchBooks() {
        String query = searchField.getText().toLowerCase();
        listModel.clear();
        for (Book book : books) {
            if (book.title.toLowerCase().contains(query) || book.author.toLowerCase().contains(query) || book.genre.toLowerCase().contains(query)) {
                listModel.addElement(book.toString());
            }
        }
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        int year;
        try {
            year = Integer.parseInt(yearField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Rok musi być liczbą", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String genre = genreField.getText();
        books.add(new Book(title, author, year, genre));
        updateBookList();
        clearFields();
    }

    private void sortBooks(String criterion) {
        switch (criterion) {
            case "Tytuł":
                Collections.sort(books, Comparator.comparing(book -> book.title));
                break;
            case "Autor":
                Collections.sort(books, Comparator.comparing(book -> book.author));
                break;
            case "Rok":
                Collections.sort(books, Comparator.comparingInt(book -> book.year));
                break;
            case "Gatunek":
                Collections.sort(books, Comparator.comparing(book -> book.genre));
                break;
        }
        updateBookList();
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        genreField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookManagement app = new BookManagement();
            app.setVisible(true);
        });
    }
}
