package com.pepcus.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.pepcus.models.Book;
import com.pepcus.models.User;
import com.pepcus.repositorys.BookRepository;
import com.pepcus.repositorys.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServicesTest {

  @InjectMocks
  private UserServices userService;
  @Mock
  private UserServices userService1;
  @Mock
  private UserRepository userRepository;
  @Mock
  private BookRepository bookRepository;

  List<Book> requestedBooks = new ArrayList<>();
  Book book = new Book();
  User user = new User();
  List<Book> books = new ArrayList<>();

  @Ignore
  @Test
  public void createUser() {
    user.setId(1);
    user.setDeactivateOn(null);
    user.setName("Shubham");
    user.setBookList(books);

    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
    User expected;
    expected = userService.createUser(user);
    System.out.println(expected);
    Assert.assertEquals(expected.getId(), user.getId());
  }

  @Ignore
  @Test
  public void User_Not_Exist() {
    book.setId(1);
    book.setName("Maths");
    book.setId(2);
    book.setName("chemis");
    requestedBooks.add(book);

    user.setId(1);
    user.setDeactivateOn(null);
    user.setName("Shubham");
    user.setBookList(requestedBooks);

    Mockito.when(userService.isUserExists(Mockito.anyInt())).thenReturn(false);
    String result = "User is not exists";
    String expected = userService.issueBook(1, requestedBooks);
    System.out.println(expected);
    Assert.assertEquals(result, expected);
  }

  @Ignore
  @Test
  public void User_Not_Activated() {
    book.setId(1);
    book.setName("Maths");
    book.setId(2);
    book.setName("chemis");
    requestedBooks.add(book);

    user.setId(1);
    user.setDeactivateOn(new Date());
    user.setName("Shubham");
    user.setBookList(requestedBooks);
    Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(user);
    Mockito.when(userService.isUserExists(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userService.checkStatus(Mockito.anyInt())).thenReturn(false);

    String expected = userService.issueBook(1, requestedBooks);
    System.out.println(expected);
    String result = "please Activate yourself";
    Assert.assertEquals(result, expected);
  }

  @Ignore
  @Test
  public void Book_Not_Available() {

    book.setId(1);
    book.setName("Maths");
    book.setId(2);
    book.setName("chemis");
    requestedBooks.add(book);

    user.setId(1);
    user.setDeactivateOn(null);
    user.setName("Shubham");
    user.setBookList(requestedBooks);
    Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(user);
    Mockito.when(bookRepository.getById(Mockito.anyInt())).thenReturn(book);
    Mockito.when(userService.checkStatus(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userService.isUserExists(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userService.isBookExists(Mockito.anyInt())).thenReturn(false);

    String expected = userService.issueBook(1, requestedBooks);
    System.out.println(expected);
    String result = "Book is not available in the Library";
    Assert.assertEquals(result, expected);
  }

  @Ignore
  @Test
  public void Book_is_available_another_user() {

    book.setId(1);
    book.setName("Maths");
    book.setId(2);
    book.setName("chemis");
    requestedBooks.add(book);

    user.setId(1);
    user.setDeactivateOn(null);
    user.setName("Shubham");
    user.setBookList(requestedBooks);
    Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(user);
    Mockito.when(bookRepository.getById(Mockito.anyInt())).thenReturn(book);
    Mockito.when(userService.checkStatus(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userService.isUserExists(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userService.isBookExists(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userService1.chekIssuedBooks(user.getId(), book)).thenReturn(true);
    String expected = userService.issueBook(1, requestedBooks);
    System.out.println(expected);
    String result = "This book is already issued by you";
    Assert.assertEquals(result, expected);
  }

  @Ignore
  @Test
  public void issueBook() {
    book.setId(1);
    book.setName("Maths");
    book.setId(2);
    book.setName("chemis");
    requestedBooks.add(book);

    user.setId(1);
    user.setDeactivateOn(null);
    user.setName("Shubham");
    user.setBookList(books);

    Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(user);
    Mockito.when(bookRepository.getById(Mockito.anyInt())).thenReturn(book);
    Mockito.when(userService.isUserExists(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userService.checkStatus(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userService.isBookExists(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userService1.chekIssuedBooks(user.getId(), book)).thenReturn(true);

    String result = "Book is successfully issued";
    String expected = userService.issueBook(1, requestedBooks);
    System.out.println(expected);
    Assert.assertEquals(result, expected);

  }

  // -------------------------------------------------------------------------------------//
 // @Ignore
  @Test
  public void User_Not_Exist_In_DeActivation_Case() {

    Mockito.when(userRepository.existsById(Mockito.anyInt())).thenReturn(false);
    String expected = userService.deActivated(2);
    String result = "Plsease Register yourself first";
    System.out.println(expected);
    Assert.assertEquals(result, expected);
  }

 // @Ignore
  @Test
  public void User_Exist_And_DeActivated() {
    book.setId(1);
    book.setName("Maths");
    requestedBooks.add(book);

    user.setId(1);
    user.setDeactivateOn(null);
    user.setName("arun");

    Mockito.when(userRepository.existsById(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(user);
    String expected = userService.deActivated(1);
    String result = "You are successfully DeActivated ";
    System.out.println(expected);
    Assert.assertEquals(result, expected);
  }

 // @Ignore
  @Test
  public void User_Exist_But_Already_DeActivated_DeActivation_Case() {
    book.setId(1);
    book.setName("Maths");
    requestedBooks.add(book);

    user.setId(1);
    user.setDeactivateOn(new Date());
    user.setName("arun");
    user.setBookList(null);

    Mockito.when(userRepository.existsById(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(user);
    //Mockito.when(userService1.isUserDeActivate(Mockito.anyInt())).thenReturn(false);
    String expected = userService.deActivated(1);
    String result = "You are Already DeActivated";
    System.out.println(expected);
    Assert.assertEquals(result, expected);
  }

 // @Ignore
  @Test
  public void User_Exist_And_Return_Book_DeActivation_Case() {
    book.setId(1);
    book.setName("Maths");
    requestedBooks.add(book);

    user.setId(1);
    user.setDeactivateOn(null);
    user.setName("arun");
    user.setBookList(requestedBooks);

    Mockito.when(userRepository.existsById(Mockito.anyInt())).thenReturn(true);
    Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(user);
    // Mockito.when(userService1.isUserDeActivate(Mockito.anyInt())).thenReturn(true);
    // Mockito.when(userService1.isBookListExists(Mockito.anyInt())).thenReturn(true);
    String expected = userService.deActivated(1);
    String result = "Please submit all your books first";
    System.out.println(expected);
    Assert.assertEquals(result, expected);
  }
}
