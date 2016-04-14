import java.io.Serializable;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;

import com.datastax.driver.mapping.annotations.*;

@Table(keyspace="wiki_db", name="wiki_data_reduced")
public class Wiki_Doc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="wiki_id")
	private int wiki_Id;
	
	@Column(name="article_id")
	private Integer article_Id;

	@Column(name="article_link")
	private String article_link;
	
	@Column(name="article_title")
	private String article_title;
	
	@Column(name="article_text")
	private String article_text;
	
	public Wiki_Doc() {
		// TODO Auto-generated constructor stub
	}

	public int getWiki_Id() {
		return wiki_Id;
	}

	public void setWiki_Id(int wiki_Id) {
		this.wiki_Id = wiki_Id;
	}

	public Integer getArticle_Id() {
		return article_Id;
	}

	public void setArticle_Id(Integer article_Id) {
		this.article_Id = article_Id;
	}

	
	public String getArticle_link() {
		return article_link;
	}

	public void setArticle_link(String article_link) {
		this.article_link = article_link;
	}

	public String getArticle_title() {
		return article_title;
	}

	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}

	public String getArticle_text() {
		return article_text;
	}

	public void setArticle_text(String article_text) {
		this.article_text = article_text;
	}

	@Override
	public String toString(){
		String value = "";
		value += "wiki_id: " + this.getWiki_Id();
		value += "\n";
		value += "article_id: " + this.getArticle_Id();
		value += "\n";
		value += "title: " + this.getArticle_title();
		value += "\n";
		value += "url: " + this.getArticle_link();
		
		return value;
	}
}
