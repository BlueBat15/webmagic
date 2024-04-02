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
        assertEquals("127.0.0.1", proxy.getHost());
        assertEquals(8080, proxy.getPort());
    }
    @Test
    public void testCreateWithSchemeOnly(){
        proxy = Proxy.create(URI.create("http://127.0.0.1:8080"));
        assertEquals("http", proxy.getScheme());
        assertNull(proxy.getUsername());
        assertNull(proxy.getPassword());
        assertEquals("127.0.0.1", proxy.getHost());
        assertEquals(8080, proxy.getPort());
    }

    @Test
    public void testCreateWithPasswordAndUsername() {
        proxy = Proxy.create(URI.create("//username:password@127.0.0.1:8080"));
        assertNull(proxy.getScheme());
        assertEquals("username", proxy.getUsername());
        assertEquals("password", proxy.getPassword());
        assertEquals("127.0.0.1", proxy.getHost());
        assertEquals(8080, proxy.getPort());
    }

    @Test
    public void testCreateWithUsernameOnly() {
        proxy = Proxy.create(URI.create("//username@127.0.0.1:8080"));
        assertNull(proxy.getScheme());
        assertEquals("username", proxy.getUsername());
        assertNull(proxy.getPassword());
        assertEquals("127.0.0.1", proxy.getHost());
        assertEquals(8080, proxy.getPort());
    }

    @Test
    public void testCreateWithPasswordOnly() {

        proxy = Proxy.create(URI.create("//:password@127.0.0.1:8080"));
        assertNull(proxy.getScheme());
        assertNull(proxy.getUsername());
        assertEquals("password", proxy.getPassword());
        assertEquals("127.0.0.1", proxy.getHost());
        assertEquals(8080, proxy.getPort());
    }

    @Test
    public void testCreateWithAllAttribute(){
        proxy = Proxy.create(URI.create("http://username:password@127.0.0.1:8080"));
        assertEquals("http",proxy.getScheme());
        assertEquals("username", proxy.getUsername());
        assertEquals("password", proxy.getPassword());
        assertEquals("127.0.0.1", proxy.getHost());
        assertEquals(8080, proxy.getPort());
    }

    @Test
    public void testEqualsBasicCondition(){
        proxy = new Proxy("127.0.0.1",8080);
        Proxy p = null;
        assertFalse(proxy.equals(p));
        p = proxy;
        assertTrue(proxy.equals(p));

        p = new Proxy("127.0.0.1",8080);
        assertTrue(proxy.equals(p));

        p = new Proxy("127.0.0.1",9000);
        assertFalse(proxy.equals(p));
    }

    @Test
    public void testEqualsWithSameHostCondition(){
        // proxy.host null
        proxy = new Proxy(null,8080);
        Proxy p = new Proxy(null,8080);
        assertTrue(proxy.equals(p));

        p = new Proxy("127.0.0.1",8080);
        assertFalse(proxy.equals(p));

        // proxy.host not null
        proxy = new Proxy("127.0.0.1",8080);
        assertTrue(proxy.equals(p));

        p = new Proxy("127.0.0.2",8080);
        assertFalse(proxy.equals(p));
    }

    @Test
    public void testEqualsWithSameSchemeCondition(){
        // proxy.scheme null
        proxy = new Proxy("127.0.0.1",8080);
        Proxy p = new Proxy("127.0.0.1",8080);
        assertTrue(proxy.equals(p));

        p.setScheme("http");
        assertFalse(proxy.equals(p));

        // proxy.scheme not null
        proxy.setScheme("http");
        assertTrue(proxy.equals(p));

        p.setScheme("https");
        assertFalse(proxy.equals(p));
    }

   @Test
    public void testEqualsWithSameUsernameCondition(){
        // proxy.username null
        proxy = new Proxy("127.0.0.1",8080,null,"password");
        Proxy p = new Proxy("127.0.0.1",8080,null,"password");
        assertTrue(proxy.equals(p));

        p = new Proxy("127.0.0.1",8080,"username","password");
        assertFalse(proxy.equals(p));

        // proxy.username not null
        proxy = new Proxy("127.0.0.1",8080,"username","password");
        assertTrue(proxy.equals(p));

        p= new Proxy("127.0.0.1",8080,"pseudo","password");
        assertFalse(proxy.equals(p));
    }

    @Test
    public void testEqualsWithSamePasswordCondition(){
        // proxy.password null
        proxy = new Proxy("127.0.0.1",8080,"username",null);
        Proxy p = new Proxy("127.0.0.1",8080,"username",null);
        assertTrue(proxy.equals(p));

        p = new Proxy("127.0.0.1",8080,"username","password");
        assertFalse(proxy.equals(p));

        // proxy.password not null
        proxy = new Proxy("127.0.0.1",8080,"username","password");
        assertTrue(proxy.equals(p));

        p= new Proxy("127.0.0.1",8080,"username","motdepasse");
        assertFalse(proxy.equals(p));
    }


    @Test
    public void testToString() {
        assertEquals("//127.0.0.1:8080", new Proxy("127.0.0.1", 8080).toString());
        assertEquals("http://127.0.0.1:8080", new Proxy("127.0.0.1", 8080, "http").toString());
        assertEquals("//username:password@127.0.0.1:8080", new Proxy("127.0.0.1", 8080, "username", "password").toString());
        assertEquals("//username@127.0.0.1:8080", new Proxy("127.0.0.1", 8080, "username", null).toString());
        assertEquals("//:password@127.0.0.1:8080", new Proxy("127.0.0.1", 8080, null, "password").toString());
    }

}
