package com.clouway.jdbc.threetalble;

import com.clouway.jdbc.threetable.*;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class listGetTest {

    AddressRepository addressRepo = AddressRepository.getInstance(Demo.getConnection());
    UserRepository userRepo = UserRepository.getInstance(Demo.getConnection());
    ContactRepository contactRepo = ContactRepository.getInstance(Demo.getConnection());

    @Test
    public void getAllUsers(){

        userRepo.createUserTable();
        List<User> userList = userRepo.getAll();
        assertThat(userList.get(0).getId(), is(1));
        assertThat(userList.get(0).getAddressId(), is(1));

    }

    @Test
    public void getAllAddresses(){

        addressRepo.createAddressTable();
        List<Address> addressList = addressRepo.getAll();
        assertThat(addressList.get(0).getId(), is(1));
        assertThat(addressList.get(0).getStreet(), is("Iglika"));

    }

    @Test
    public void getAllContacts(){

        contactRepo.createContactTable();
        List<Contact> contactList = contactRepo.getAll();
        assertThat(contactList.get(0).getId(), is(1));
        assertThat(contactList.get(0).getUserId(), is(1));

    }


}
