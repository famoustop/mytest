package cn.itcast.solr;

import cn.itcast.solr.pojo.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;

import java.util.List;

/**SolrJ入门实例*/
public class SolrTest {
    //定义solrServer,用来操作solr

    private SolrServer solrServer =
            new HttpSolrServer("http://localhost:8080/solr/collection1");
    //添加或修改索引
    @Test
    public void saveOrUpdate() throws Exception {
        Product product = new Product();
        product.setPid("8000");
        product.setName("不用华为是二逼");
        product.setCatalogName("手机");
        product.setPrice(14000);
        product.setDescription("这手机贼傻逼");
        product.setPicture("1.jpg");

        solrServer.addBean(product);
        //提交事务
        solrServer.commit();

    }

    //根据id删除索引
    @Test
    public void deleteById() throws Exception {

        solrServer.deleteById("8000");
        //提交事务
        solrServer.commit();
    }

    //根据条件删除
    @Test
    public void deleteByQuery() throws Exception {

        solrServer.deleteByQuery("*:*");
        //提交事务
        solrServer.commit();
    }

    //分页查询(带条件查询)
    @Test
    public void query()throws Exception{
        //创建条件对象
        SolrQuery q = new SolrQuery("*:*");
        //设置分页起始记录数
        q.setStart(5);
        //设置页大小
        q.setRows(5);
        //分页查询,得到查询响应对象
        QueryResponse response = solrServer.query(q);
        System.out.println("命中的记录数: "+response.getResults().getNumFound());
        //获取搜索的文档
        List<Product> products = response.getBeans(Product.class);

        for (Product product : products) {
            System.out.println(product.getPid() +"\t" +product.getName()+"\t"+product.getPrice());
        }

    }
}
