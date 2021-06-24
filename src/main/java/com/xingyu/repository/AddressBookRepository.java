package com.xingyu.repository;

import com.xingyu.domain.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
    @Query("SELECT book FROM AddressBook book WHERE book.user.username = ?1")
    List<AddressBook> findByUser(String username);
}
