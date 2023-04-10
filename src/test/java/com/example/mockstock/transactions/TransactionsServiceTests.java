package com.example.mockstock.transactions;

import com.example.mockstock.portfolios.Portfolios;
import com.example.mockstock.portfolios.PortfoliosService;
import com.example.mockstock.stocks.StockService;
import com.example.mockstock.stocks.Stocks;
import com.example.mockstock.users.User;
import com.example.mockstock.users.UserNotFound;
import com.example.mockstock.users.UserRepository;
import com.example.mockstock.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TransactionsServiceTests {
    @Mock
    private TransactionsRepository transactionsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StockService stockService;

    @Mock
    private UserService userService;

    @Mock
    private PortfoliosService portfoliosService;

    @InjectMocks
    private TransactionsService transactionsService;

    private User testUser;
    private Transactions testTransaction;

    @BeforeEach
    void setUp() {
        testUser = new User("John Doe", "johndoe@gmail.com", "password", 1000.0);
        testTransaction = new Transactions("AAPL", "buy", 5, testUser);
    }

    @Test
    void getTransactions() {
        when(transactionsRepository.findByUserId(testUser.getId())).thenReturn(Collections.singletonList(testTransaction));

        assertEquals(1, transactionsService.getTransactions(testUser.getId()).size());
        assertEquals(testTransaction, transactionsService.getTransactions(testUser.getId()).get(0));
    }

    @Test
    void postTransactions_buy_success() throws Exception {
        testUser.setBalance(1000.0);
        Stocks stock = new Stocks("AAPL", "Apple Inc.", 150.0, 2.0);
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(stockService.getQuote(testTransaction.getStockSymbol())).thenReturn(stock);

        ArgumentCaptor<Double> balanceCaptor = ArgumentCaptor.forClass(Double.class);
        when(userService.updateUser(eq(testUser.getId()), any(Double.class))).thenReturn(testUser);

        transactionsService.postTransactions(testTransaction, testUser.getId());

        verify(userService).updateUser(eq(testUser.getId()), balanceCaptor.capture());
        assertEquals(1000.0 - (testTransaction.getQuantity() * stock.getPrice()), balanceCaptor.getValue());
    }

    @Test
    void postTransactions_buy_insufficientBalance() throws Exception{
        testUser.setBalance(100.0);
        Stocks stock = new Stocks("AAPL", "Apple Inc.", 150.0, 2.0);
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(stockService.getQuote(testTransaction.getStockSymbol())).thenReturn(stock);

        assertThrows(InsufficientBalanceException.class, () -> transactionsService.postTransactions(testTransaction, testUser.getId()));
    }
    @Test
    void postTransactions_userNotFound() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> transactionsService.postTransactions(testTransaction, testUser.getId()));
    }
}
