package us.codecraft.webmagic;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SiteTest {

    private Site site;

    @Before
    public void init(){
        site = Site.me();
    }

    @Test
    public void charsetTest() {
        site.setDefaultCharset(StandardCharsets.UTF_8.name());
        assertEquals(StandardCharsets.UTF_8.name(), site.getDefaultCharset());
    }

    @Test
    public void addHeaderTest(){
        site.addHeader("test","header");
        assertEquals("header",site.getHeaders().get("test"));
    }

    @Test
    public void addCookieTest(){
    site.addCookie("test","cookie");
    assertEquals("cookie",site.getDefaultCookies().get("test"));

    }

    @Test
    public void addCookie3ParamsTest(){
        site.addCookie("domain","test","cookie");
        Map<String,String> cookie = new HashMap<>();
        cookie.put("test","cookie");
        assertEquals(cookie,site.getCookies().get("domain"));
    }

    @Test
    public void toTaskTest(){
        site.setDomain("domain");
        Task task = site.toTask();
        assertEquals(site.getDomain(), task.getUUID());
        assertEquals(site, task.getSite());
    }
}
