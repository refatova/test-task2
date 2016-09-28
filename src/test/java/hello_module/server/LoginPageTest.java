package hello_module.server;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.mockito.Mockito.doReturn;

/**
 * Created by Saniye on 28.09.16.
 */


public class LoginPageTest extends Assert {
    @Test
    public void defaultLocaleTest() {
        Locale china = new Locale("zh");
        Locale.setDefault(china);
        ResourceBundle labels = ResourceBundle.getBundle("bundle.MyBundle", china);

        assertEquals("Good morning", labels.getString(LoginServiceIntfImpl.MORNING_MESSAGE));
        assertEquals("Good day", labels.getString(LoginServiceIntfImpl.DAY_MESSAGE));
        assertEquals("Good evening", labels.getString(LoginServiceIntfImpl.EVENING_MESSAGE));
        assertEquals("Good night", labels.getString(LoginServiceIntfImpl.NIGHT_MESSAGE));
    }


    @Test
    public void morningGreetingTest() {
        LoginServiceIntfImpl m = new LoginServiceIntfImpl();
        assertEquals(LoginServiceIntfImpl.MORNING_MESSAGE, m.choseGreetingMessage(6));
        assertFalse(LoginServiceIntfImpl.MORNING_MESSAGE.equals(m.choseGreetingMessage(9)));
    }

    @Test
    public void dayGreetingTest() {
        LoginServiceIntfImpl m = new LoginServiceIntfImpl();
        assertEquals(LoginServiceIntfImpl.DAY_MESSAGE, m.choseGreetingMessage(9));
        assertFalse(LoginServiceIntfImpl.DAY_MESSAGE.equals(m.choseGreetingMessage(19)));
    }

    @Test
    public void eveningGreetingTest() {
        LoginServiceIntfImpl m = new LoginServiceIntfImpl();
        assertEquals(LoginServiceIntfImpl.EVENING_MESSAGE, m.choseGreetingMessage(19));
        assertFalse(LoginServiceIntfImpl.EVENING_MESSAGE.equals(m.choseGreetingMessage(23)));
    }

    @Test
    public void nightGreetingTest() {
        LoginServiceIntfImpl m = new LoginServiceIntfImpl();
        assertEquals(LoginServiceIntfImpl.NIGHT_MESSAGE, m.choseGreetingMessage(23));
        assertTrue(LoginServiceIntfImpl.NIGHT_MESSAGE.equals(m.choseGreetingMessage(5)));
        assertFalse("6 a.m isn't included to night time", LoginServiceIntfImpl.NIGHT_MESSAGE.equals(m.choseGreetingMessage(6)));
    }


    @Test
    public void findUserTest() {
        LoginServiceIntfImpl m = new LoginServiceIntfImpl();
        assertTrue("Иван".equals(m.findUser("ivan", "secret")));
    }

    @Test
    public void findInvalidUser() {
        LoginServiceIntfImpl m = new LoginServiceIntfImpl();
        assertTrue((m.findUser("test", "test"))==null);
    }

    @Test(expected = UserNotFoundException.class)
    public void loginServiceTest() {
        LoginServiceIntfImpl serviceMock = Mockito.spy(new LoginServiceIntfImpl());
        doReturn(null).when(serviceMock).findUser("not_existed_user", "not_existed_user");
        serviceMock.login("not_existed_user", "not_existed_user", "ru");
    }

    @Test
    public void loginServiceTestPositive(){
        LoginServiceIntfImpl serviceMock = Mockito.spy(new LoginServiceIntfImpl());
        doReturn("Keanu").when(serviceMock).findUser("user", "user");
        doReturn(LoginServiceIntfImpl.DAY_MESSAGE).when(serviceMock).getMessageForCurrentTime();
        assertTrue("Добрий день, Keanu".equals(serviceMock.login("user", "user","uk")));
    }


}
