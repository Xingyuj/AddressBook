package com.xingyu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xingyu.domain.AddressBook;
import com.xingyu.domain.Customer;
import com.xingyu.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
public class AddressBookTest {
    private static final String API_URL = "/api/v1/books";
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private Principal principal;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        User user = new User();
        user.setId(101L);
        user.setUsername("admin");
        user.setPassword("password");
        principal = new UsernamePasswordAuthenticationToken(user, "password", null);
        SecurityContextHolder.getContext().setAuthentication((Authentication) principal);
        this.mockMvc = builder.build();
    }

    @Test
    public void testListAddressBook() throws Exception {
        mockMvc.perform(get(API_URL).principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void testGetAddressBookById() throws Exception {
        mockMvc.perform(get(API_URL + "/201"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(201)));
    }

    @Test
    public void testCreateAddressBook() throws Exception {
        AddressBook book = new AddressBook();
        book.setCustomer(Collections.singletonList(new Customer("sample", "123456")));
        mockMvc.perform(post(API_URL).principal(principal)
                .contentType("application/json")
                .content(getContentStringFromInput(book, "id"))
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer", notNullValue()));
    }

    @Test
    public void testUpdateAddressBook() throws Exception {
        AddressBook book = new AddressBook();
        book.setId(201L);
        book.setCustomer(Collections.singletonList(new Customer("Trump", "0466123456")));
        mockMvc.perform(put(API_URL).principal(principal)
                .contentType("application/json")
                .content(getContentStringFromInput(book))
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer", notNullValue()));
    }

    public static String getContentStringFromInput(Object input, String... fieldsToRemove) throws JsonProcessingException {
        JsonNode node = mapper.convertValue(input, JsonNode.class);
        if (node instanceof ObjectNode) {
            ObjectNode objNode = (ObjectNode) node;
            Arrays.stream(fieldsToRemove)
                    .filter(objNode::has)
                    .forEach(objNode::remove);
        }
        return mapper.writeValueAsString(node);
    }
}
