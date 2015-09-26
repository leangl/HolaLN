package mobi.tattu.hola.model;

import java.io.Serializable;

/**
 * Created by cristian on 25/09/15.
 */
public class News implements Serializable,Comparable<Category> {
    public String title;
    public String subTitle;
    public String content;
    public String imageUri;

    public Category category;
    public String urlNews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        return category == news.category;

    }

    @Override
    public int hashCode() {
        return category != null ? category.hashCode() : 0;
    }

    @Override
    public int compareTo(Category category) {
        return category.compareTo(category);
    }

    @Override
    public String toString() {
        return  title +" " + subTitle + " " + content;
    }

    public String getResumeNews(){
        return  title;
    }
}
