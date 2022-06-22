package com.pepcus.services;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pepcus.exception.ResourceNotFoundException;
import com.pepcus.models.Book;
import com.pepcus.models.User;
import com.pepcus.repositorys.BookRepository;
import com.pepcus.repositorys.UserRepository;
@Service
public class UserServices {
 


    @Autowired 
    private BookRepository bookRepository;
    @Autowired 
    private UserRepository userRepository;

    public User createUser(User user) {
      user.setRegistrationDate(new Date());
      return userRepository.save(user);
    }
    

//    public String issueBook(Integer userId, List <Book> books) {
//      String result = null;
//      User user = userRepository.findById(userId)
//          .orElseThrow(() -> new ResourceNotFoundException("Plsease Register yoursef first", "Id", userId));
//      List <Book> usersBook = user.getBooks();
  //
//      if (user.getDeActivatedOn() == null) {
//        for (Book book : books) {
//          if (bookRepository.existsById(book.getId())) {
//            Book availableBook = bookRepository.getById(book.getId());
//            if (availableBook.getIssuedOn() == null) {
//              if (usersBook.contains(availableBook)) {
//                result = "This Book is already issued : " + availableBook.getId();
//              } else {
//                //              availableBook.setUser(user);
//                availableBook.setIssuedOn(new Date());
//                usersBook.add(availableBook);
//                result = "Book is Successfully issued : " + availableBook.getId();
  //
//              }
//            } else {
//              result = "This book is already issued by another user : " + availableBook.getId();
//            }
//          } else {
//            result = "This book is not available in the library : " + book.getId();
//          }
//        }
//      } else {
//        throw new ResourceNotFoundException("Please Register yourself first", "Id", userId);
//      }
//      userRepository.save(user);
//      return result;
//    }

    public String issueBook(Integer userId, List <Book> books) {
      String result = null;
      if (isUserExists(userId)) {
        User existsUser = userRepository.getById(userId);
        if (checkStatus(userId)) {
          List <Book> usersBookList = existsUser.getBookList();
          for (Book book : books) {
            if (isBookExists(book.getId())) {
              Book availableBook = bookRepository.getById(book.getId());
              if (chekIssuedBooks(userId, availableBook)) {
                result = "This book is already issued by you";
              } else {
                book.setAddedOn(new Date());
                usersBookList.add(availableBook);
//                result = "Book is Successfully issued : " + availableBook.getId();
                result = "Book is successfully issued";
              }

            } else {
              result = "Book is not available in the Library";
            }
          }
          userRepository.save(existsUser);
        } else {
          result = "please Activate yourself";
        }

      } else {
        result = "User is not exists";
      }

      return result;
    }

//    public String deActivate(Integer userId) {
//      String result = null;
//      User user = userRepository.findById(userId)
//          .orElseThrow(() -> new ResourceNotFoundException("Plsease Register yoursef first", "Id", userId));
//      if (user.getBookList().isEmpty()) {
//        if (user.getDeactivateOn() == null) {
//          user.setDeactivateOn(new Date());
//          result = "You are successfully DeActivated : " + userId;
//        } else {
//          throw new ResourceNotFoundException("You are Already DeActivated", "Id", userId);
//        }
//      } else {
//        throw new ResourceNotFoundException("Please submit all your books first", "Id", userId);
//      }
//
//      userRepository.save(user);
//      return result;
//    }
  //................................Refactor code.........................................//    
    public String deActivated(Integer userId) {
      String result;
    
      if(isUserExist(userId)) {
        if(isUserDeActivate(userId)){
          if(isBookListExists(userId)) {
            result="Please submit all your books first";
          }else {
            result="You are successfully DeActivated ";
          }
        }else {
          result="You are Already DeActivated";
        }
      }else {
       result= "Plsease Register yourself first";
      }
      return result;
    }
//................................Refactor code.........................................//
    
    public Boolean isUserExist(int id){
      Boolean result ;
      if(userRepository.existsById(id)){
        result = true;
      }else{
        result = false;
      }
      return result;
    }
    public Boolean isBookListExists(int id){
      Boolean result ;
     User userContain= userRepository.getById(id);      
      if(userContain.getBookList() != null && !userContain.getBookList().isEmpty()){
        result = true;
      }else{
        result = false;
      }
      return result;
    
    }
    public Boolean isUserDeActivate(int id){
      Boolean result ;
      User user =userRepository.getById(id);
      if(user.getDeactivateOn() == null){
        user.setDeactivateOn(new Date());   
        userRepository.save(user);
        result = true;
      }else{
        result = false;
      }
      return result;
    }
    
//----------------------------------------------------------------------------------------------//    
    
    
    
    
    
    public String activateUser(Integer userId) {
      String result;
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("Plsease Register yourself first", "Id", userId));

      if (user.getDeactivateOn() != null) {
        user.setDeactivateOn(null);
        result = "You are successfully Activated : " + userId;
      } else {
        throw new ResourceNotFoundException("You are Already Activated", "Id", userId);
      }

      userRepository.save(user);
      return result;
    }

//    public String returnBook(Integer userId, List <Book> books) {
//      String result = null;
//      User user = userRepository.findById(userId)
//          .orElseThrow(() -> new ResourceNotFoundException("Plsease Register yoursef first", "Id", userId));
//      List <Book> usersBook = user.getBookList();
//
//      if (user.getDeactivateOn() == null) {
//        for (Book book : books) {
//          if (bookRepository.existsById(book.getId())) {
//            Book availableBook = bookRepository.getById(book.getId());
//            if (availableBook.getIssueOn() != null) {
//              if (usersBook.contains(availableBook)) {
//                //              availableBook.setUser(null);
//                availableBook.setReturnedOn(new Date());
//                availableBook.setAddedOn(null);
//                usersBook.remove(availableBook);
//                result = "Book is Successfully returned : " + availableBook.getId();
//
//              } else {
//                result = "This Book is not issued by you : " + availableBook.getId();
//              }
//            } else {
//              result = "Please provide a valid book : " + availableBook.getId();
//            }
//          } else {
//            result = "Please provide a valid book : " + book.getId();
//          }
//        }
//      } else {
//        throw new ResourceNotFoundException("Please Register yourself first", "Id", userId);
//      }
//      userRepository.saveAndFlush(user);
//      return result;
//    }

//    public ResponseEntity <Object> getAllUser() {
//      userDao userDao = new userDao();
//      List <User> userList = userDao.getUser();
//      if (userList != null) {
//        return ResponseEntity.status(HttpStatus.OK).body(userList);
//      } else {
//        List <User> lst = new ArrayList <>();
//        ResponseEntity <Object> response;
//        response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(lst);
//        return response;
//      }
//    }
  //
//    public String testDeactivation(){
//      userDao userDao = new userDao();
//      return userDao.deActivateUser(1);
//    }

  //==========================Refactored Code=====================================
    public Boolean checkStatus(int userId) {
      Boolean result;
      if (userRepository.existsById(userId)) {
        User user = userRepository.getById(userId);
        if (user.getDeactivateOn() != null) {
          result = false;
        } else {
          result = true;
        }
      } else {
        result = false;
      }

      return result;
    }

    public Boolean isUserExists(int id){
      Boolean result = false;
      if(userRepository.existsById(id)){
        result = true;
      }else{
        result = false;
      }
      return result;
    }

    public Boolean chekIssuedBooks(int userId, Book book){
      Boolean result = false;
      if(isUserExists(userId)){
        User user = userRepository.getById(userId);
        List<Book> bookList = user.getBookList();
        if(bookList.contains(book)){
          result = true;
        } else {
          result = false;
        }
      } else {
        result = false;
      }
      return result;
    }

    public Boolean isBookExists(int id){
      Boolean result = false;
      if(bookRepository.existsById(id)){
        result = true;
      }else{
        result = false;
      }
      return result;
    }



  }


