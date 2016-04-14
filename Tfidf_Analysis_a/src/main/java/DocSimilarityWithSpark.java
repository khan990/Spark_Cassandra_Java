import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapRowTo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.feature.IDFModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.BLAS;

import com.datastax.spark.connector.japi.CassandraJavaUtil;

public class DocSimilarityWithSpark {

	@SuppressWarnings({ "serial", "unchecked" })
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date startTime = new Date();
		org.apache.spark.SparkConf sparkConfig = new SparkConf()
				.setAppName("TFIDF Analysis").setMaster("local[4]")
				.set("spark.executor.memory", "3g")
				.set("spark.cassandra.connection.host", "localhost")
				.set("spark.driver.maxResultSize", "3g");
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);
		
		JavaSparkContext sparkContext = new JavaSparkContext(sparkConfig);
		JavaRDD<Wiki_Doc> docs = CassandraJavaUtil
				.javaFunctions(sparkContext)
				.cassandraTable("wiki_db", "wiki_data_reduced",
						mapRowTo(Wiki_Doc.class));
		
		
		JavaRDD<List<String>> terms = docs
				.map(new Function<Wiki_Doc, List<String>>() {
					public List<String> call(Wiki_Doc adoc) {

						return Arrays.asList(adoc.getArticle_text()
								.replaceAll("\\<[^>]*>", " ")
								.replace("\\n", " ").toLowerCase()
								.replaceAll("[0-9]", " ")
								.replaceAll("[^a-zA-Z]", " ")
								.replaceAll("\\s+", " ").split("\\s+"));
					}
				});
		
		System.out.println("");
		System.out.println("Num of JavaRDD for terms: " + terms.count());
		System.out.println("Num of terms for first article: " + terms.first().size());
		/*
		for(List<String> strings: terms.collect()){
			System.out.printf("\n-------------new(%d)------------\n", strings.size());
			for(String astring: strings){
				System.out.printf("%s ", astring);
			}
		}
		*/
		HashingTF tf_hashing = new HashingTF();
		
		JavaRDD<Vector> tf_Vector = tf_hashing.transform(terms);
		
		tf_Vector.cache();
		
		System.out.println("");
		System.out.println("tf vector size: " + tf_Vector.count());
		System.out.println("tf vector first element size: " + tf_Vector.first().size());
		
		
		/*
		for(Vector avector: tf_Vector.collect()){
			System.out.printf("\n-------------new(%d)------------\n", avector.size());
			System.out.println(avector);
		}
		*/
		
		IDFModel idf = new org.apache.spark.mllib.feature.IDF().fit(tf_Vector);
		
		JavaRDD<Vector> tfidf = idf.transform(tf_Vector);
//		tfidf.cache();
		
		/*
		for(Vector avector: tfidf.collect()){
			System.out.printf("\n-------------new(%d)------------\n", avector.size());
			System.out.println(avector);
		}
		*/
		
		/*
		// Distance between two vectors
		int i = 1, j = 1;
		for(Vector aTFIDF_Vector: tfidf.collect()){
			j = 1;
			for(Vector bTFIDF_Vector: tfidf.collect()){
				Double distance = Vectors.sqdist(aTFIDF_Vector, bTFIDF_Vector);
				System.out.println("Distance between " + i + " and " + j + " is: " + distance);
				j++;
			}
			i++;
		}
		*/
		
		//Cosine Similarity between two vectors
		int i = 1, j = 1;
		for(Vector aTFIDF_Vector: tfidf.collect()){
			j = 1;
			for(Vector bTFIDF_Vector: tfidf.collect()){
				Double cosineSimilarity = (BLAS.dot(aTFIDF_Vector , bTFIDF_Vector))/(Vectors.norm(aTFIDF_Vector, 2) * Vectors.norm(bTFIDF_Vector, 2));
				System.out.println("Cosine Similarity between " + i + " and " + j + " is: " + cosineSimilarity);
				j++;
			}
			i++;
		}
		
		Date endTime = new  Date();
		
		System.out.println();
		System.out.println("TF-IDF vector size: " + tfidf.count());
		System.out.println("TF-IDF first vector size: " + tf_Vector.first().size());
		
		System.out.println("Start Time: " + startTime.toString());
		System.out.println("End Time: " + endTime.toString());
		System.out.println("Time Difference: "
				+ (endTime.getTime() - startTime.getTime()) + " milli seconds");
//		counts.saveAsTextFile("counts.txt");
//		tfidf.saveAsTextFile("tfidf.txt");
	}

}
