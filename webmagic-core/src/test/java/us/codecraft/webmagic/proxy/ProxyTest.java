package us.codecraft.webmagic.proxy;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author yxssfxwzy@sina.com May 30, 2014
 * 
 */
public class ProxyTest {

    private static List<String[]> httpProxyList = new ArrayList<String[]>();
    private Proxy proxy;


    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8080;
    public static final String SCHEME = "http";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    @BeforeClass
    public static void before() {
        String[] source = { "::0.0.0.1:0", "::0.0.0.2:0", "::0.0.0.3:0", "::0.0.0.4:0" };
        for (String line : source) {
            httpProxyList.add(new String[] {line.split(":")[0], line.split(":")[1], line.split(":")[2], line.split(":")[3] });
        }
    }

    @Test
    public void testCreateWithMinimumURI(){
        proxy = Proxy.create(URI.create("//127.0.0.1:8080"));
        assertNull(proxy.getScheme());
        assertNull(proxy.getUsername());
        assertNull(proxy.getPassword());
        assertEquals(HOST, proxy.getHost());
        assertEquals(PORT, proxy.getPort());
    }
    @Test
    public void testCreateWithSchemeOnly(){
        proxy = Proxy.create(URI.create("http://127.0.0.1:8080"));
        assertEquals(SCHEME, proxy.getScheme());
        assertNull(proxy.getUsername());
        assertNull(proxy.getPassword());
        assertEquals(HOST, proxy.getHost());
        assertEquals(PORT, proxy.getPort());
    }

    @Test
    public void testCreateWithPasswordAndUsername() {
        proxy = Proxy.create(URI.create("//username:password@127.0.0.1:8080"));
        assertNull(proxy.getScheme());
        assertEquals(USERNAME, proxy.getUsername());
        assertEquals(PASSWORD, proxy.getPassword());
        assertEquals(HOST, proxy.getHost());
        assertEquals(PORT, proxy.getPort());
    }

    @Test
    public void testCreateWithUsernameOnly() {
        proxy = Proxy.create(URI.create("//username@127.0.0.1:8080"));
        assertNull(proxy.getScheme());
        assertEquals(USERNAME, proxy.getUsername());
        assertNull(proxy.getPassword());
        assertEquals(HOST, proxy.getHost());
        assertEquals(PORT, proxy.getPort());
    }

    @Test
    public void testCreateWithPasswordOnly() {

        proxy = Proxy.create(URI.create("//:password@127.0.0.1:8080"));
        assertNull(proxy.getScheme());
        assertNull(proxy.getUsername());
        assertEquals(PASSWORD, proxy.getPassword());
        assertEquals(HOST, proxy.getHost());
        assertEquals(PORT, proxy.getPort());
    }

    @Test
    public void testCreateWithAllAttribute(){
        proxy = Proxy.create(URI.create("http://username:password@127.0.0.1:8080"));
        assertEquals(SCHEME,proxy.getScheme());
        assertEquals(USERNAME, proxy.getUsername());
        assertEquals(PASSWORD, proxy.getPassword());
        assertEquals(HOST, proxy.getHost());
        assertEquals(PORT, proxy.getPort());
    }

    @Test
    public void testEqualsBasicCondition(){
        proxy = new Proxy(HOST,PORT);
        Proxy p = null;
        assertFalse(proxy.equals(p));
        p = proxy;
        assertTrue(proxy.equals(p));

        p = new Proxy(HOST,PORT);
        assertTrue(proxy.equals(p));

        p = new Proxy(HOST,9000);
        assertFalse(proxy.equals(p));
    }

    @Test
    public void testEqualsWithSameHostCondition(){
        // proxy.host null
        proxy = new Proxy(null,PORT);
        Proxy p = new Proxy(null,PORT);
        assertTrue(proxy.equals(p));

        p = new Proxy(HOST,PORT);
        assertFalse(proxy.equals(p));

        // proxy.host not null
        proxy = new Proxy(HOST,PORT);
        assertTrue(proxy.equals(p));

        p = new Proxy("127.0.0.2",PORT);
        assertFalse(proxy.equals(p));
    }

    @Test
    public void testEqualsWithSameSchemeCondition(){
        // proxy.scheme null
        proxy = new Proxy(HOST,PORT);
        Proxy p = new Proxy(HOST,PORT);
        assertTrue(proxy.equals(p));

        p.setScheme(SCHEME);
        assertFalse(proxy.equals(p));

        // proxy.scheme not null
        proxy.setScheme(SCHEME);
        assertTrue(proxy.equals(p));

        p.setScheme("https");
        assertFalse(proxy.equals(p));
    }

   @Test
    public void testEqualsWithSameUsernameCondition(){
        // proxy.username null
        proxy = new Proxy(HOST,PORT,null,PASSWORD);
        Proxy p = new Proxy(HOST,PORT,null,PASSWORD);
        assertTrue(proxy.equals(p));

        p = new Proxy(HOST,PORT,USERNAME,PASSWORD);
        assertFalse(proxy.equals(p));

        // proxy.username not null
        proxy = new Proxy(HOST,PORT,USERNAME,PASSWORD);
        assertTrue(proxy.equals(p));

        p= new Proxy(HOST,PORT,"pseudo",PASSWORD);
        assertFalse(proxy.equals(p));
    }

    @Test
    public void testEqualsWithSamePasswordCondition(){
        // proxy.password null
        proxy = new Proxy(HOST,PORT,USERNAME,null);
        Proxy p = new Proxy(HOST,PORT,USERNAME,null);
        assertTrue(proxy.equals(p));

        p = new Proxy(HOST,PORT,USERNAME,PASSWORD);
        assertFalse(proxy.equals(p));

        // proxy.password not null
        proxy = new Proxy(HOST,PORT,USERNAME,PASSWORD);
        assertTrue(proxy.equals(p));

        p= new Proxy(HOST,PORT,USERNAME,"motdepasse");
        assertFalse(proxy.equals(p));
    }


    @Test
    public void testToString() {
        assertEquals("//127.0.0.1:8080", new Proxy(HOST, PORT).toString());
        assertEquals("http://127.0.0.1:8080", new Proxy(HOST, PORT, SCHEME).toString());
        assertEquals("//username:password@127.0.0.1:8080", new Proxy(HOST, PORT, USERNAME, PASSWORD).toString());
        assertEquals("//username@127.0.0.1:8080", new Proxy(HOST, PORT, USERNAME, null).toString());
        assertEquals("//:password@127.0.0.1:8080", new Proxy(HOST, PORT, null, PASSWORD).toString());
    }

}
