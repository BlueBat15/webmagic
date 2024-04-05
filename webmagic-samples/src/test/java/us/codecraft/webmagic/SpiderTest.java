package us.codecraft.webmagic;

import org.junit.Ignore;
import org.junit.Test;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.SimplePageProcessor;
import us.codecraft.webmagic.samples.HuxiuProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-4-20
 * Time: 下午7:46
 */
public class SpiderTest {


    @Ignore
    @Test
    public void testSpider() throws InterruptedException {
        Spider me = Spider.create(new HuxiuProcessor()).addPipeline(new FilePipeline());
        me.run();
    }

    @Ignore
    @Test
    public void testGlobalSpider(){
        SimplePageProcessor pageProcessor2 = new SimplePageProcessor( "http://www.diaoyuweng.com/thread-*-1-1.html");
        System.out.println(pageProcessor2.getSite().getCharset());
        pageProcessor2.getSite().setSleepTime(500);
        Spider.create(pageProcessor2).addUrl("http://www.diaoyuweng.com/home.php?mod=space&uid=88304&do=thread&view=me&type=thread&from=space").addPipeline(new FilePipeline()).scheduler(new FileCacheQueueScheduler("/data/temp/webmagic/cache/")).
                run();
    }

    @Ignore
    @Test
    public void test(){
        System.out.println(System.getProperty("java.io.tmpdir"));
    }


}
