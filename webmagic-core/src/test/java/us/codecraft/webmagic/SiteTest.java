package us.codecraft.webmagic;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

public class SiteTest {

    @Test
    public void test() {
        Site site = Site.me().setDefaultCharset(StandardCharsets.UTF_8.name());
        assertEquals(StandardCharsets.UTF_8.name(), site.getDefaultCharset());
    }

    @Test
    public void addHeaderTest(){
        Site site = Site.me();
        site.addHeader("test","header");
        assertEquals("header",site.getHeaders().get("test"));
    }
}
