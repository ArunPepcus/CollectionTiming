package com.pepcus.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.pepcus.exception.ResourceNotFoundException;
import com.pepcus.models.Book;
import com.pepcus.models.User;
import com.pepcus.repositorys.BookRepository;
import com.pepcus.repositorys.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @InjectMocks
  UserService userService;

  @Mock
  UserRepository userRepository;
  @Mock
  BookRepository bookRepository;
  
  @Spy
  List<Book> existingBook =new ArrayList<Book>();

  @Test
  public void testAddUser() {
    User user = new User();
    user.setName("Arun");
    user.setBookList(null);
    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
    User response = userService.addUser(user);
    Assert.assertNotNull(response);
    Assert.assertNotNull(response.getRegistrationDate());
    Assert.assertEquals(response.getName(), user.getName());
  }

  @Test
  public void testGetAllUsers() {
    User user1 = new User();
    user1.setId(1);
    user1.setName("Martin Bingel");
    user1.setDeactivateOn(new Date());
    user1.setRegistrationDate(new Date());
    user1.setBookList(null);

    User user2 = new User();
    user2.setId(2);
    user2.setName("Arun soni");
    user2.setDeactivateOn(new Date());
    user2.setRegistrationDate(new Date());
    user2.setBookList(null);
    List<User> userList = new ArrayList<>();
    userList.add(user1);
    userList.add(user2);

    Mockito.when(userRepository.findAll()).thenReturn(userList);
    List<User> response = userService.getAllUsers(userList);
    System.out.println(response);
    Assert.assertNotNull(response);
    assertThat(response).isEqualTo(userList);

  }

  @Test
  public void testIssueBooksSuccess() {
    List<Book> books = new ArrayList<>();
    User user = new User();
    user.setDeactivateOn(new Date());
    user.setBookList(books);
    Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
    String response = userService.issueBook(1, books);
    System.out.println("response : " + response);

    Assert.assertNotNull(response);
    Assert.assertEquals(response, "Please register yourself first then issue book ");

  }

  /**
   * Book is already issued by another use
   */
  @Test
  public void testIssueBooksSuccessNested() {
    Book book = new Book();
    book.setId(1);
    book.setIssueOn(new Date());
    List<Book> books = new ArrayList<>();
    books.add(book);
    Book bookcontain = new Book();
    bookcontain.setId(1);
    User user = new User();
    user.setDeactivateOn(null);
    user.setBookList(books);
    Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
    Mockito.when(bookRepository.existsById(book.getId())).thenReturn(true);
    Mockito.when(bookRepository.getById(book.getId())).thenReturn(book);
    String response = userService.issueBook(1, books);
    System.out.println("response : " + response);

    Assert.assertNotNull(response);
    Assert.assertEquals(response, " book is already issued by another user....... ");

  }

  /**
   * added book by user
   */
  @Test
  public void testIssueBooks_IssueSuccessTested2() {

    Book book = new Book();
    book.setId(1);
    book.setIssueOn(null);

    List<Book> books = new ArrayList<>();
    books.add(book);

    User user = new User();
    user.setDeactivateOn(null);
    user.setBookList(books);

    Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
    //Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
    Mockito.when(bookRepository.existsById(Mockito.anyInt())).thenReturn(true);
    Mockito.when(bookRepository.existsById(book.getId())).thenReturn(true);
    Mockito.when(bookRepository.getById(book.getId())).thenReturn(book);
   // Mockito.when(existingBook(book.getIssueOn())).thenReturn(true);
   // Mockito.when(bookcontain.setIssueOn()).thenReturn();
    String response = userService.issueBook(1,books);
    System.out.println("response : " + response);
    Assert.assertNotNull(response);
    Assert.assertEquals(response, "Successfully added!.........");
  }

  /**
   * Success case with no book available
   */
  @Test
  public void testIssueBooks_Success() {
    Book book = new Book();
    book.setId(1);
    List<Book> books = new ArrayList<>();
    books.add(book);
    User user = new User();
    user.setBookList(books);
    Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
    Mockito.when(bookRepository.existsById(Mockito.anyInt())).thenReturn(false);
    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
    String response = userService.issueBook(1, books);
    System.out.println("response : " + response);

    Assert.assertNotNull(response);
    Assert.assertEquals(response, "this book in not available in the library");
  }

  /**
   * Resource Not found Exception case
   */
  @Test
  public void testIssueBooks_Exception() {
    try {
      Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
      String response = userService.issueBook(1, new ArrayList<>());
    } catch (Exception e) {
      Assert.assertTrue(e instanceof ResourceNotFoundException);
    }
  }

}
